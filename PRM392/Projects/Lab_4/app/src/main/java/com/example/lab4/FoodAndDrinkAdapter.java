package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAndDrinkAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final ArrayList<FoodAndDrink> foodAndDrinks;

    public FoodAndDrinkAdapter(Context context, int layout, ArrayList<FoodAndDrink> foodAndDrinks) {
        this.context = context;
        this.layout = layout;
        this.foodAndDrinks = foodAndDrinks;
    }

    @Override
    public int getCount() {
        return foodAndDrinks.size();
    }

    @Override
    public FoodAndDrink getItem(int position) {
        return foodAndDrinks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView txtName = convertView.findViewById(R.id.tvName);
        TextView txtDescription = convertView.findViewById(R.id.tvDescription);
        TextView txtPrice = convertView.findViewById(R.id.tvPrice);
        ImageView imageView = convertView.findViewById(R.id.imgv);

        FoodAndDrink foodAndDrink = getItem(position);
        txtName.setText(foodAndDrink.getName());
        txtDescription.setText(foodAndDrink.getDescription());
        txtPrice.setText(String.valueOf(foodAndDrink.getPrice()));
        imageView.setImageResource(foodAndDrink.getImage());

        return convertView;
    }
}
