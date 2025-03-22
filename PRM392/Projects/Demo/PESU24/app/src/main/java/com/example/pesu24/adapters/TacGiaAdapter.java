package com.example.pesu24.adapters;

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

import com.example.pesu24.R;
import com.example.pesu24.activities.EditTacGiaActivity;
import com.example.pesu24.activities.TacGiaMapActivity;
import com.example.pesu24.entities.TacGia;

import java.util.List;

public class TacGiaAdapter extends ArrayAdapter<TacGia> {
    private Context context;
    private List<TacGia> tacGiaList;

    public TacGiaAdapter(@NonNull Context context, List<TacGia> tacGiaList) {
        super(context, 0, tacGiaList);
        this.context = context;
        this.tacGiaList = tacGiaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_tacgia, parent, false);
        }

        TacGia tacGia = tacGiaList.get(position);

        TextView tvTenTacGia = listItem.findViewById(R.id.tv_ten_tac_gia);
        TextView tvEmail = listItem.findViewById(R.id.tv_email);
        TextView tvDiaChi = listItem.findViewById(R.id.tv_dia_chi);
        TextView tvDienThoai = listItem.findViewById(R.id.tv_dien_thoai);
        ImageButton btnViewMap = listItem.findViewById(R.id.btn_view_map);

        tvTenTacGia.setText(tacGia.getTenTacGia());
        tvEmail.setText("Email: " + tacGia.getEmail());
        tvDiaChi.setText("Địa chỉ: " + tacGia.getDiaChi());
        tvDienThoai.setText("SĐT: " + tacGia.getDienThoai());

        // Set click listener to open edit activity
        listItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTacGiaActivity.class);
            intent.putExtra("tacgia_id", tacGia.getId());
            context.startActivity(intent);
        });

        // Set click listener for the map button
        btnViewMap.setOnClickListener(v -> {
            Intent intent = new Intent(context, TacGiaMapActivity.class);
            intent.putExtra("tacgia_id", tacGia.getId());
            context.startActivity(intent);
        });

        return listItem;
    }
}
