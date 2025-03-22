package com.example.pesu24.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pesu24.R;
import com.example.pesu24.activities.EditSachActivity;
import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.entities.Sach;
import com.example.pesu24.entities.TacGia;

import java.util.List;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private List<Sach> sachList;
    private DatabaseManager dbManager;

    public SachAdapter(@NonNull Context context, List<Sach> sachList, DatabaseManager dbManager) {
        super(context, 0, sachList);
        this.context = context;
        this.sachList = sachList;
        this.dbManager = dbManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
        }

        Sach sach = sachList.get(position);

        // Get author information
        TacGia tacGia = dbManager.getTacGia(sach.getIdTacGia());

        TextView tvTenSach = listItem.findViewById(R.id.tv_ten_sach);
        TextView tvTheLoai = listItem.findViewById(R.id.tv_the_loai);
        TextView tvNgayXB = listItem.findViewById(R.id.tv_ngay_xb);
        TextView tvTacGia = listItem.findViewById(R.id.tv_tac_gia);

        tvTenSach.setText(sach.getTenSach());
        tvTheLoai.setText("Thể loại: " + sach.getTheLoai());
        tvNgayXB.setText("Ngày xuất bản: " + sach.getNgayXB());
        tvTacGia.setText("Tác giả: " + (tacGia != null ? tacGia.getTenTacGia() : "Không xác định"));

        // Set click listener for editing
        listItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditSachActivity.class);
            intent.putExtra("sach_id", sach.getId());
            context.startActivity(intent);
        });

        return listItem;
    }


}
