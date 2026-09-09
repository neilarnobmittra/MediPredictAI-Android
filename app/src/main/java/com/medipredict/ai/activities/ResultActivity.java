package com.medipredict.ai.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medipredict.ai.R;
import com.medipredict.ai.adapters.ResultAdapter;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        ArrayList<String> diseases = getIntent().getStringArrayListExtra("diseases");
        ArrayList<String> confidences = getIntent().getStringArrayListExtra("confidences");
        ArrayList<String> risks = getIntent().getStringArrayListExtra("risks");
        ArrayList<String> specialists = getIntent().getStringArrayListExtra("specialists");
        ArrayList<String> precautions = getIntent().getStringArrayListExtra("precautions");

        if (diseases == null) diseases = new ArrayList<>();
        if (confidences == null) confidences = new ArrayList<>();
        if (risks == null) risks = new ArrayList<>();
        if (specialists == null) specialists = new ArrayList<>();
        if (precautions == null) precautions = new ArrayList<>();

        RecyclerView rv = findViewById(R.id.rvResults);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ResultAdapter(diseases, confidences, risks, specialists, precautions));
    }
}
