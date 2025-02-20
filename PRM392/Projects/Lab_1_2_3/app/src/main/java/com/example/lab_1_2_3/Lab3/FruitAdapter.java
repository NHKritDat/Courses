package com.example.lab_1_2_3.Lab3;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab_1_2_3.R;

import java.util.List;

public class FruitAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final List<Fruit> fruitList;

    public FruitAdapter(Context context, int layout, List<Fruit> fruitList) {
        this.context = context;
        this.layout = layout;
        this.fruitList = fruitList;
    }

    @Override
    public int getCount() {
        return fruitList.size();
    }

    @Override
    public Fruit getItem(int i) {
        return fruitList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(layout, parent, false);
        }

        TextView txtTen = view.findViewById(R.id.textviewten);
        TextView txtDesc = view.findViewById(R.id.textviewmota);
        ImageView image = view.findViewById(R.id.imagevienhinh);

        Fruit fruit = getItem(i);

        txtTen.setText(fruit.getName());
        txtDesc.setText(fruit.getDescription());

        if (fruit.getImageUri() != null && !fruit.getImageUri().isEmpty()) {
            image.setImageURI(Uri.parse(fruit.getImageUri()));
        } else {
            image.setImageResource(fruit.getImage());
        }

        return view;
    }
}
