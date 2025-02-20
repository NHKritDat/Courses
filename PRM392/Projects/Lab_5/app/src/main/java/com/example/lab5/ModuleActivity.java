package com.example.lab5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModuleActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private ItemAdapter adapter;
    private int selectedItemPosition = -1;
    private Uri selectedImageUri;
    private ImageView imageView;
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    imageView.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_module);

        items = new ArrayList<>();
        items.add(new Item(R.drawable.img, null, "ListView trong Android là một phần để nhóm nhiều mục (item) lại với nhau và và và và và", "Android"));
        items.add(new Item(R.drawable.img_1, null, "Xử lý sự kiện trong IOS. Sau khi các bạn biêt cách thiết kế giao diện dành cho các ứng dụng mobile thuộc hệ điều hành", "IOS"));

        adapter = new ItemAdapter(items, position -> {
            selectedItemPosition = position;
            Toast.makeText(this, "Selected: " + items.get(position).getName(), Toast.LENGTH_SHORT).show();
        });
        RecyclerView view = findViewById(R.id.rvItem);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnAdd).setOnClickListener(v -> showDialogAdd());
        findViewById(R.id.btnEdit).setOnClickListener(v -> {
            if (selectedItemPosition != -1)
                showDialogEdit(selectedItemPosition);
            else
                Toast.makeText(this, "Vui lòng chọn một mục để sửa!", Toast.LENGTH_SHORT).show();

        });
        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if (selectedItemPosition != -1)
                showDialogDelete(selectedItemPosition);
            else
                Toast.makeText(this, "Vui lòng chọn một mục để xóa!", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDialogDelete(int index) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");

        builder.setPositiveButton("Xóa", (dialog, which) -> {
            delete(index);
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void delete(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            adapter.notifyDataSetChanged();
            selectedItemPosition = -1;
            Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogEdit(int index) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Sửa module");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtDesc = view.findViewById(R.id.edtDesc);
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        imageView = view.findViewById(R.id.imgPreview);

        btnChooseImage.setOnClickListener(v -> openGallery());

        edtName.setText(items.get(index).getName());
        edtDesc.setText(items.get(index).getDescription());

        builder.setView(view);
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            items.get(index).setName(edtName.getText().toString());
            items.get(index).setDescription(edtDesc.getText().toString());
            boolean isImageNull = selectedImageUri != null;
            int image = isImageNull ? 0 : R.drawable.ic_launcher_background;
            items.get(index).setImageUri(isImageNull ? selectedImageUri.toString() : null);
            items.get(index).setImage(image);

            adapter.notifyDataSetChanged();
            selectedItemPosition = -1;
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm mới module");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtDesc = view.findViewById(R.id.edtDesc);
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        imageView = view.findViewById(R.id.imgPreview);

        btnChooseImage.setOnClickListener(v -> openGallery());

        builder.setView(view);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edtName.getText().toString();
            String desc = edtDesc.getText().toString();
            boolean isImageNull = selectedImageUri != null;
            int image = isImageNull ? 0 : R.drawable.ic_launcher_background;

            items.add(new Item(image, isImageNull ? selectedImageUri.toString() : null, desc, name));

            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }
}