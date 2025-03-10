package com.example.lab_10;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    private List<Person> list;

    public PersonAdapter(Context context) {
        this.context = context;
    }

    private final Context context;

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = list.get(position);
        holder.firstName.setText(person.getFirstName());
        holder.lastName.setText(person.getLastName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setTasks(List<Person> personList) {
        list = personList;
        notifyDataSetChanged();
    }

    public List<Person> getTasks() {
        return list;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView lastName;
        ImageView editImage;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.tvFirstName);
            lastName = itemView.findViewById(R.id.tvLastName);
            editImage = itemView.findViewById(R.id.ivEdit);
            editImage.setOnClickListener(v -> {
                int elementId = list.get(getAdapterPosition()).getUid();
                Intent i = new Intent(context, EditPersonActivity.class);
                i.putExtra(Constants.UPDATE_PERSON_ID, elementId);
                context.startActivity(i);
            });
        }
    }
}
