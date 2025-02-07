package com.example.listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvFruit;
    ArrayList<Fruits> fruitsArrayList;
    FruitAdapter adapter;
    Button btnAdd, btnEdit, btnDelete;
    int selectedIndex = -1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();

        adapter = new FruitAdapter(this, R.layout.line_fruit, fruitsArrayList);
        lvFruit.setAdapter(adapter);

        lvFruit.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedIndex = i;
            Toast.makeText(MainActivity.this, "Chọn: " + fruitsArrayList.get(i).getName(), Toast.LENGTH_SHORT).show();
        });

        btnAdd.setOnClickListener(v -> showDialogAdd());
        btnEdit.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                showDialogEdit(selectedIndex);
            } else {
                Toast.makeText(this, "Vui lòng chọn một mục để sửa!", Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                showDialogDelete(selectedIndex);
            } else {
                Toast.makeText(this, "Vui lòng chọn một mục để xóa!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Mapping() {
        lvFruit = findViewById(R.id.listviewFruit);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        fruitsArrayList = new ArrayList<>();
        fruitsArrayList.add(new Fruits("Chuối già", "Chuối già Đà Lạt", R.drawable.banana));
        fruitsArrayList.add(new Fruits("Thanh Long", "Thanh Long Bình Thuận", R.drawable.pitaya));
        fruitsArrayList.add(new Fruits("Dâu Tây", "Dâu tây Đà Lạt", R.drawable.strawberry));
        fruitsArrayList.add(new Fruits("Dưa Hấu", "Dưa hấu Long An", R.drawable.watermelon));
        fruitsArrayList.add(new Fruits("Cam Vàng", "Cam vàng mọng nước", R.drawable.orange));
    }

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm mới trái cây");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtDesc = view.findViewById(R.id.edtDesc);
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        imgPreviewDialog = view.findViewById(R.id.imgPreview);

        btnChooseImage.setOnClickListener(v -> openGallery());

        builder.setView(view);
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edtName.getText().toString();
            String desc = edtDesc.getText().toString();

            // Nếu có hình ảnh, lưu đường dẫn
            String imagePath = (imageUri != null) ? imageUri.toString() : null;
            fruitsArrayList.add(new Fruits(name, desc, imagePath));

            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDialogEdit(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa trái cây");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtDesc = view.findViewById(R.id.edtDesc);

        edtName.setText(fruitsArrayList.get(index).getName());
        edtDesc.setText(fruitsArrayList.get(index).getDescription());

        builder.setView(view);
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            fruitsArrayList.get(index).setName(edtName.getText().toString());
            fruitsArrayList.get(index).setDescription(edtDesc.getText().toString());
            adapter.notifyDataSetChanged();
            selectedIndex = -1;
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDialogDelete(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");

        builder.setPositiveButton("Xóa", (dialog, which) -> {
            deleteFruit(index);
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteFruit(int position) {
        if (position >= 0 && position < fruitsArrayList.size()) {
            fruitsArrayList.remove(position);
            adapter.notifyDataSetChanged();
            selectedIndex = -1;
            Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    private ImageView imgPreviewDialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            if (imgPreviewDialog != null) {
                imgPreviewDialog.setImageURI(imageUri);
            }
        }
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

}
