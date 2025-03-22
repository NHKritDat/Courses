package com.example.sp25_nguyenhoangdat_net1720.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sp25_nguyenhoangdat_net1720.R;
import com.example.sp25_nguyenhoangdat_net1720.activities.AuthorMapActivity;
import com.example.sp25_nguyenhoangdat_net1720.activities.EditAuthorActivity;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;

import java.util.List;

public class AuthorAdapter extends ArrayAdapter<Author> {
    private Context context;
    private List<Author> authorList;

    public AuthorAdapter(@NonNull Context context, List<Author> authorList) {
        super(context, 0, authorList);
        this.context = context;
        this.authorList = authorList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_author, parent, false);
        }

        Author author = authorList.get(position);

        TextView tvName = listItem.findViewById(R.id.tv_name);
        TextView tvEmail = listItem.findViewById(R.id.tv_email);
        TextView tvAddress = listItem.findViewById(R.id.tv_address);
        TextView tvPhone = listItem.findViewById(R.id.tv_phone);
        ImageButton btnViewMap = listItem.findViewById(R.id.btn_view_map);

        tvName.setText(author.getName());
        tvEmail.setText("Email: " + author.getEmail());
        tvAddress.setText("Address: " + author.getAddress());
        tvPhone.setText("Phone: " + author.getPhone());

        // Set click listener to open edit activity
        listItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditAuthorActivity.class);
            intent.putExtra("author_id", author.getAuthorId());
            context.startActivity(intent);
        });

        // Set click listener for the map button
        btnViewMap.setOnClickListener(v -> {
            Intent intent = new Intent(context, AuthorMapActivity.class);
            intent.putExtra("author_id", author.getAuthorId());
            context.startActivity(intent);
        });

        return listItem;
    }
}
