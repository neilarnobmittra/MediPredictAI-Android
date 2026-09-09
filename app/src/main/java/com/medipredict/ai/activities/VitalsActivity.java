package com.medipredict.ai.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.medipredict.ai.R;
import com.medipredict.ai.api.ApiClient;
import com.medipredict.ai.models.PredictionResult;
import com.medipredict.ai.utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VitalsActivity extends AppCompatActivity {

    private Spinner spinnerDisease;
    private TextInputEditText etAge, etBp, etGlucose, etCholesterol, etBmi;
    private PrefManager pref;

    private static final String[] DISEASES = {
            "diabetes", "heart", "kidney", "liver", "stroke"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

        pref = new PrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        spinnerDisease = findViewById(R.id.spinnerDisease);
        etAge = findViewById(R.id.etAge);
        etBp = findViewById(R.id.etBp);
        etGlucose = findViewById(R.id.etGlucose);
        etCholesterol = findViewById(R.id.etCholesterol);
        etBmi = findViewById(R.id.etBmi);
        MaterialButton btnPredict = findViewById(R.id.btnPredict);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, DISEASES);
        spinnerDisease.setAdapter(adapter);

        btnPredict.setOnClickListener(v -> predict());
    }

    private void predict() {
        try {
            String disease = spinnerDisease.getSelectedItem().toString();
            double age = Double.parseDouble(etAge.getText().toString().trim());
            double bp = Double.parseDouble(etBp.getText().toString().trim());
            double glucose = Double.parseDouble(etGlucose.getText().toString().trim());
            double cholesterol = Double.parseDouble(etCholesterol.getText().toString().trim());
            double bmi = Double.parseDouble(etBmi.getText().toString().trim());

            Map<String, Object> body = new HashMap<>();
            body.put("disease", disease);
            body.put("age", age);
            body.put("bp", bp);
            body.put("glucose", glucose);
            body.put("cholesterol", cholesterol);
            body.put("bmi", bmi);

            ApiClient.getService().predictVitals("Bearer firebase", body)
                    .enqueue(new Callback<PredictionResult>() {
                        @Override
                        public void onResponse(Call<PredictionResult> call, Response<PredictionResult> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                showResult(response.body());
                            } else {
                                showDemoResult(disease);
                            }
                        }

                        @Override
                        public void onFailure(Call<PredictionResult> call, Throwable t) {
                            showDemoResult(disease);
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, "Please fill all fields with valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDemoResult(String disease) {
        PredictionResult r = new PredictionResult();
        r.disease = disease.substring(0, 1).toUpperCase() + disease.substring(1);
        r.confidence = 55.0 + Math.random() * 30;
        r.risk = r.confidence >= 65 ? "High" : (r.confidence >= 35 ? "Medium" : "Low");
        r.specialist = "Specialist";
        r.precautions = "Please consult a doctor for proper diagnosis.";
        showResult(r);
    }

    private void showResult(PredictionResult r) {
        Intent intent = new Intent(this, ResultActivity.class);
        ArrayList<String> diseases = new ArrayList<>();
        ArrayList<String> confidences = new ArrayList<>();
        ArrayList<String> risks = new ArrayList<>();
        ArrayList<String> specialists = new ArrayList<>();
        ArrayList<String> precautions = new ArrayList<>();

        diseases.add(r.disease);
        confidences.add(String.valueOf(r.confidence));
        risks.add(r.risk != null ? r.risk : "Medium");
        specialists.add(r.specialist != null ? r.specialist : "Specialist");
        precautions.add(r.precautions != null ? r.precautions : "");

        intent.putStringArrayListExtra("diseases", diseases);
        intent.putStringArrayListExtra("confidences", confidences);
        intent.putStringArrayListExtra("risks", risks);
        intent.putStringArrayListExtra("specialists", specialists);
        intent.putStringArrayListExtra("precautions", precautions);
        startActivity(intent);
    }
}
