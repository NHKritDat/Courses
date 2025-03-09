package com.example.lab_11.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_11.R;
import com.example.lab_11.model.Trainee;

import java.util.ArrayList;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.ViewHolder> {
    private final ArrayList<Trainee> trainees;
    private final OnItemClickListener listener;

    public TraineeAdapter(ArrayList<Trainee> trainees, OnItemClickListener listener) {
        this.trainees = trainees;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trainee trainee = trainees.get(position);
        holder.tvName.setText(String.format("Name: %s", trainee.getName()));
        holder.tvEmail.setText(String.format("Email: %s", trainee.getEmail()));
        holder.tvPhone.setText(String.format("Phone: %s", trainee.getPhone()));
        holder.tvGender.setText(String.format("Gender: %s", trainee.getGender()));
    }

    @Override
    public int getItemCount() {
        return trainees.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvEmail;
        TextView tvPhone;
        TextView tvGender;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvGender = itemView.findViewById(R.id.tvGender);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
