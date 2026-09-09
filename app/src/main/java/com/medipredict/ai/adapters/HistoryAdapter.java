package com.medipredict.ai.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medipredict.ai.R;
import com.medipredict.ai.models.PredictionResult;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<PredictionResult> list;

    public HistoryAdapter(List<PredictionResult> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        PredictionResult r = list.get(position);
        h.tvDisease.setText(r.disease);
        h.tvDetails.setText(String.format("Confidence: %.1f%%  |  Risk: %s", r.confidence, r.risk));
        h.tvDate.setText(r.date != null ? r.date : "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisease, tvDetails, tvDate;

        ViewHolder(View v) {
            super(v);
            tvDisease = v.findViewById(R.id.tvDisease);
            tvDetails = v.findViewById(R.id.tvDetails);
            tvDate = v.findViewById(R.id.tvDate);
        }
    }
}
