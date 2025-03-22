package com.example.bookmanagementapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookmanagementapp.R;
import com.example.bookmanagementapp.model.Author;

import java.util.List;
public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {
    private List<Author> authorList;

    public AuthorAdapter(List<Author> authorList) {
        this.authorList = authorList;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author author = authorList.get(position);
        holder.nameTextView.setText(author.getName());
        holder.emailTextView.setText("Email: " + author.getEmail());
        holder.phoneTextView.setText("Phone: " + author.getPhone());
        holder.addressTextView.setText("Address: " + author.getAddress());
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, phoneTextView, addressTextView;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
        }
    }
}

