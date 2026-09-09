package com.medipredict.ai.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medipredict.ai.R;
import com.medipredict.ai.adapters.HistoryAdapter;
import com.medipredict.ai.models.PredictionResult;
import com.medipredict.ai.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pref = new PrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        // Show sample history for now (can later load from Firestore)
        showDemo();
    }

    private void showDemo() {
        List<PredictionResult> demo = new ArrayList<>();

        PredictionResult r = new PredictionResult();
        r.disease = "Diabetes";
        r.confidence = 72.5;
        r.risk = "High";
        r.date = "2024-08-01 14:30";
        demo.add(r);

        PredictionResult r2 = new PredictionResult();
        r2.disease = "Heart Disease";
        r2.confidence = 41.0;
        r2.risk = "Medium";
        r2.date = "2024-07-28 10:15";
        demo.add(r2);

        PredictionResult r3 = new PredictionResult();
        r3.disease = "Common Cold";
        r3.confidence = 65.0;
        r3.risk = "Low";
        r3.date = "2024-07-20 09:00";
        demo.add(r3);

        rvHistory.setAdapter(new HistoryAdapter(demo));
    }
}
