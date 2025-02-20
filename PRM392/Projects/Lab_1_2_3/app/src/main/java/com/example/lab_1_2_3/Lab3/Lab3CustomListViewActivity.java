package com.example.lab_1_2_3.Lab3;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab_1_2_3.R;

import java.util.ArrayList;

public class Lab3CustomListViewActivity extends AppCompatActivity {
    ListView lvFruit;
    ArrayList<Fruit> fruitArrayList;
    FruitAdapter adapter;
    Button btnAdd, btnEdit, btnDelete;
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
        setContentView(R.layout.activity_lab3_custom_list_view);

        findViewById(R.id.btnBackLab3clv).setOnClickListener(v -> finish());

        Mapping();
        adapter = new FruitAdapter(this, R.layout.line_fruit, fruitArrayList);
        lvFruit.setAdapter(adapter);

        lvFruit.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemPosition = position;
            Toast.makeText(this, "Chọn: " + fruitArrayList.get(position).getName(), LENGTH_SHORT).show();
        });

        btnAdd.setOnClickListener(v -> showDialogAdd());
        btnEdit.setOnClickListener(v -> {
            if (selectedItemPosition != -1) {
                showDialogEdit(selectedItemPosition);
            } else {
                Toast.makeText(this, "Vui lòng chọn một mục để sửa!", Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(v -> {
            if (selectedItemPosition != -1) {
                showDialogDelete(selectedItemPosition);
            } else {
                Toast.makeText(this, "Vui lòng chọn một mục để xóa!", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm mới trái cây");

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

            fruitArrayList.add(new Fruit(name, desc, image, isImageNull ? selectedImageUri.toString() : null));

            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDialogEdit(int index) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Sửa trái cây");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtDesc = view.findViewById(R.id.edtDesc);
        Button btnChooseImage = view.findViewById(R.id.btnChooseImage);
        imageView = view.findViewById(R.id.imgPreview);

        btnChooseImage.setOnClickListener(v -> openGallery());

        edtName.setText(fruitArrayList.get(index).getName());
        edtDesc.setText(fruitArrayList.get(index).getDescription());

        builder.setView(view);
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            fruitArrayList.get(index).setName(edtName.getText().toString());
            fruitArrayList.get(index).setDescription(edtDesc.getText().toString());
            boolean isImageNull = selectedImageUri != null;
            int image = isImageNull ? 0 : R.drawable.ic_launcher_background;
            fruitArrayList.get(index).setImageUri(isImageNull ? selectedImageUri.toString() : null);
            fruitArrayList.get(index).setImage(image);

            adapter.notifyDataSetChanged();
            selectedItemPosition = -1;
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDialogDelete(int index) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa không?");

        builder.setPositiveButton("Xóa", (dialog, which) -> {
            deleteFruit(index);
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteFruit(int position) {
        if (position >= 0 && position < fruitArrayList.size()) {
            fruitArrayList.remove(position);
            adapter.notifyDataSetChanged();
            selectedItemPosition = -1;
            Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void Mapping() {
        lvFruit = findViewById(R.id.listviewFruit);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        fruitArrayList = new ArrayList<>();
        fruitArrayList.add(new Fruit("Chuối già", "Chuối già Đà Lạt", R.drawable.banana, null));
        fruitArrayList.add(new Fruit("Thanh Long", "Thanh Long Bình Thuận", R.drawable.pitaya, null));
        fruitArrayList.add(new Fruit("Dâu Tây", "Dâu tây Đà Lạt", R.drawable.strawberry, null));
        fruitArrayList.add(new Fruit("Dưa Hấu", "Dưa hấu Long An", R.drawable.watermelon, null));
        fruitArrayList.add(new Fruit("Cam Vàng", "Cam vàng mọng nước", R.drawable.orange, null));
    }
}