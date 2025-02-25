package com.example.lab_9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private final MainActivity context;
    private final int layout;
    private final List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layout, null);
            holder.txtTen = convertView.findViewById(R.id.textviewTen);
            holder.imgDelete = convertView.findViewById(R.id.imageviewDelete);
            holder.imgEdit = convertView.findViewById(R.id.imageviewEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CongViec congViec = congViecList.get(position);
        holder.txtTen.setText(congViec.getTenCV());

        holder.imgEdit.setOnClickListener(v -> context.DialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV()));
        holder.imgDelete.setOnClickListener(v -> context.DialogXoaCongViec(congViec.getTenCV(), congViec.getIdCV()));

        return convertView;
    }

    private class ViewHolder {
        TextView txtTen;
        ImageView imgDelete, imgEdit;
    }
}
