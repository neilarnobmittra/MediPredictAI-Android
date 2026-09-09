package com.medipredict.ai.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medipredict.ai.R;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private final List<String[]> list;

    public DoctorAdapter(List<String[]> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        String[] d = list.get(position);
        h.tvName.setText(d[0]);
        h.tvSpecialty.setText(d[1]);
        h.tvAddress.setText(d[2]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSpecialty, tvAddress;

        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvDoctorName);
            tvSpecialty = v.findViewById(R.id.tvSpecialty);
            tvAddress = v.findViewById(R.id.tvAddress);
        }
    }
}
