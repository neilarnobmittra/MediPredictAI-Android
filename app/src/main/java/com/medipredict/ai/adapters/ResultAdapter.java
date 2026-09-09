package com.medipredict.ai.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medipredict.ai.R;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private final List<String> diseases;
    private final List<String> confidences;
    private final List<String> risks;
    private final List<String> specialists;
    private final List<String> precautions;

    public ResultAdapter(List<String> diseases, List<String> confidences,
                         List<String> risks, List<String> specialists,
                         List<String> precautions) {
        this.diseases = diseases;
        this.confidences = confidences;
        this.risks = risks;
        this.specialists = specialists;
        this.precautions = precautions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        h.tvDisease.setText(diseases.get(position));
        double conf = Double.parseDouble(confidences.get(position));
        h.tvConfidence.setText(String.format("Confidence: %.1f%%", conf));
        h.progress.setProgress((int) conf);

        String risk = risks.get(position);
        h.tvRisk.setText(risk);
        if ("High".equalsIgnoreCase(risk)) {
            h.tvRisk.setBackgroundColor(Color.parseColor("#EF4444"));
        } else if ("Medium".equalsIgnoreCase(risk)) {
            h.tvRisk.setBackgroundColor(Color.parseColor("#F59E0B"));
        } else {
            h.tvRisk.setBackgroundColor(Color.parseColor("#10B981"));
        }

        h.tvSpecialist.setText("Specialist: " + specialists.get(position));
        h.tvPrecautions.setText(precautions.get(position));
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisease, tvConfidence, tvRisk, tvSpecialist, tvPrecautions;
        ProgressBar progress;

        ViewHolder(View v) {
            super(v);
            tvDisease = v.findViewById(R.id.tvDisease);
            tvConfidence = v.findViewById(R.id.tvConfidence);
            tvRisk = v.findViewById(R.id.tvRisk);
            tvSpecialist = v.findViewById(R.id.tvSpecialist);
            tvPrecautions = v.findViewById(R.id.tvPrecautions);
            progress = v.findViewById(R.id.progressConfidence);
        }
    }
}
