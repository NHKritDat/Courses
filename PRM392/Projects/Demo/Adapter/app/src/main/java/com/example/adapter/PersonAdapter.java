package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {
    private final ArrayList<Person> list;
    private final LayoutInflater inflater;

    public PersonAdapter(Context context, ArrayList<Person> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Person getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item, parent, false);

        TextView name = convertView.findViewById(R.id.name);
        TextView age = convertView.findViewById(R.id.age);

        Person person = getItem(position);
        name.setText(person.getName());
        age.setText(String.valueOf(person.getAge()));

        return convertView;
    }
}
