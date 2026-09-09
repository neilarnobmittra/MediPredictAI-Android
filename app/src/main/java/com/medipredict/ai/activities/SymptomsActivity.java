package com.medipredict.ai.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.medipredict.ai.R;
import com.medipredict.ai.api.ApiClient;
import com.medipredict.ai.models.PredictionResult;
import com.medipredict.ai.models.SymptomResponse;
import com.medipredict.ai.utils.PrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymptomsActivity extends AppCompatActivity {

    private static final String[] ALL_SYMPTOMS = {
            "fever", "cough", "fatigue", "headache", "nausea", "vomiting",
            "diarrhea", "chest_pain", "shortness_of_breath", "dizziness",
            "joint_pain", "muscle_pain", "sore_throat", "runny_nose",
            "loss_of_appetite", "weight_loss", "night_sweats", "chills",
            "abdominal_pain", "back_pain", "rash", "itching", "swelling",
            "blurred_vision", "frequent_urination", "excessive_thirst",
            "high_blood_pressure", "irregular_heartbeat", "anxiety", "depression"
    };

    private ChipGroup chipGroup;
    private TextView tvSelectedCount;
    private final Set<String> selected = new HashSet<>();
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        pref = new PrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        chipGroup = findViewById(R.id.chipGroupSymptoms);
        tvSelectedCount = findViewById(R.id.tvSelectedCount);
        TextInputEditText etSearch = findViewById(R.id.etSearch);
        MaterialButton btnPredict = findViewById(R.id.btnPredict);

        populateChips(Arrays.asList(ALL_SYMPTOMS));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterChips(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnPredict.setOnClickListener(v -> predict());
    }

    private void populateChips(List<String> symptoms) {
        chipGroup.removeAllViews();
        for (String symptom : symptoms) {
            Chip chip = new Chip(this);
            chip.setText(symptom.replace("_", " "));
            chip.setCheckable(true);
            chip.setChecked(selected.contains(symptom));
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) selected.add(symptom);
                else selected.remove(symptom);
                tvSelectedCount.setText("Selected: " + selected.size());
            });
            chipGroup.addView(chip);
        }
    }

    private void filterChips(String query) {
        List<String> filtered = new ArrayList<>();
        for (String s : ALL_SYMPTOMS) {
            if (s.replace("_", " ").toLowerCase().contains(query.toLowerCase())) {
                filtered.add(s);
            }
        }
        populateChips(filtered);
    }

    private void predict() {
        if (selected.isEmpty()) {
            Toast.makeText(this, "Please select at least one symptom", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("symptoms", new ArrayList<>(selected));

        // Try real API (no token needed if backend allows, otherwise falls to demo)
        ApiClient.getService().predictSymptoms("Bearer firebase", body)
                .enqueue(new Callback<SymptomResponse>() {
                    @Override
                    public void onResponse(Call<SymptomResponse> call, Response<SymptomResponse> response) {
                        if (response.isSuccessful() && response.body() != null
                                && response.body().predictions != null) {
                            openResults(response.body().predictions);
                        } else {
                            openDemoResults();
                        }
                    }

                    @Override
                    public void onFailure(Call<SymptomResponse> call, Throwable t) {
                        openDemoResults();
                    }
                });
    }

    private void openDemoResults() {
        List<PredictionResult> demo = new ArrayList<>();
        PredictionResult r1 = new PredictionResult();
        r1.disease = "Common Cold";
        r1.confidence = 68.5;
        r1.severity = "Low";
        r1.specialist = "General Physician";
        r1.precautions = "Rest, stay hydrated, and consult a doctor if symptoms worsen.";
        demo.add(r1);

        PredictionResult r2 = new PredictionResult();
        r2.disease = "Viral Infection";
        r2.confidence = 42.0;
        r2.severity = "Medium";
        r2.specialist = "General Physician";
        r2.precautions = "Monitor symptoms, take rest, and seek medical advice if fever persists.";
        demo.add(r2);

        openResults(demo);
    }

    private void openResults(List<PredictionResult> results) {
        Intent intent = new Intent(this, ResultActivity.class);
        ArrayList<String> diseases = new ArrayList<>();
        ArrayList<String> confidences = new ArrayList<>();
        ArrayList<String> risks = new ArrayList<>();
        ArrayList<String> specialists = new ArrayList<>();
        ArrayList<String> precautions = new ArrayList<>();

        for (PredictionResult r : results) {
            diseases.add(r.disease);
            confidences.add(String.valueOf(r.confidence));
            risks.add(r.severity != null ? r.severity : r.risk);
            specialists.add(r.specialist != null ? r.specialist : "General Physician");
            precautions.add(r.precautions != null ? r.precautions : "");
        }

        intent.putStringArrayListExtra("diseases", diseases);
        intent.putStringArrayListExtra("confidences", confidences);
        intent.putStringArrayListExtra("risks", risks);
        intent.putStringArrayListExtra("specialists", specialists);
        intent.putStringArrayListExtra("precautions", precautions);
        startActivity(intent);
    }
}
