package com.example.listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FruitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Fruits> fruitList;

    public FruitAdapter(Context context, int layout, List<Fruits> fruitList) {
        this.context = context;
        this.layout = layout;
        this.fruitList = fruitList;
    }

    @Override
    public int getCount() {
        return fruitList.size();
    }

    @Override
    public Object getItem(int i) {
        return fruitList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtDes;
        ImageView imageView;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            // Ánh xạ view
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.textvienten);
            holder.txtDes = convertView.findViewById(R.id.textviewmota);
            holder.imageView = convertView.findViewById(R.id.imagevienhinh);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Gán dữ liệu vào view
        Fruits fruit = fruitList.get(i);
        holder.txtName.setText(fruit.getName());
        holder.txtDes.setText(fruit.getDescription());

        // Kiểm tra nếu có ảnh từ bộ nhớ thì load ảnh từ file
        if (fruit.getImagePath() != null) {
            File imgFile = new File(fruit.getImagePath());
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imageView.setImageBitmap(bitmap);
            } else {
                holder.imageView.setImageResource(R.drawable.ic_launcher_background);
            }
        } else {
            holder.imageView.setImageResource(fruit.getImageResource());
        }

        return convertView;
    }
}
