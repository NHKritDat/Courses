package com.example.lab_9;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Database database;
    private ListView lvCongViec;
    private ArrayList<CongViec> arrayCongViec;
    private CongViecAdapter adapter;

    public void DialogSuaCongViec(String ten, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_cong_viec);

        EditText edtTenCV = dialog.findViewById(R.id.editTextTenCV);
        Button btnXacNhan = dialog.findViewById(R.id.buttonSua);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        edtTenCV.setText(ten);
        btnXacNhan.setOnClickListener(v -> {
            String tenMoi = edtTenCV.getText().toString().trim();
            database.QueryData("UPDATE CongViec SET TenCV = '" + tenMoi + "' WHERE id = '" + id + "'");
            Toast.makeText(MainActivity.this, "Da cap nhat", LENGTH_SHORT).show();
            dialog.dismiss();
            GetDataCongViec();
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void DialogXoaCongViec(String ten, int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Ban co muon xoa cong viec " + ten + " khong?");
        dialogXoa.setPositiveButton("Yes", (dialog, which) -> {
            database.QueryData("DELETE FROM CongViec WHERE Id = '" + id + "'");
            Toast.makeText(MainActivity.this, "Da xoa " + ten, LENGTH_SHORT).show();
            GetDataCongViec();
        });
        dialogXoa.setNegativeButton("No", (dialog, which) -> {
        });
        dialogXoa.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) DialogThem();
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtTen = dialog.findViewById(R.id.editTextTenCV);
        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        btnThem.setOnClickListener(v -> {
            String tenCV = edtTen.getText().toString();
            if (tenCV.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc!", LENGTH_SHORT).show();
            } else {
                database.QueryData("Insert into CongViec values(null, '" + tenCV + "')");
                Toast.makeText(MainActivity.this, "Đã thêm", LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });

        btnHuy.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        lvCongViec = findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        database = new Database(this, "GhiChu.sqlite", null, 1);

        database.QueryData("Create table if not exists CongViec(id Integer Primary Key Autoincrement, TenCV nvarchar(200))");

//        database.QueryData("Insert into Congviec values(null, 'Project Android')");
//        database.QueryData("Insert into Congviec values(null, 'Design App')");

        GetDataCongViec();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void GetDataCongViec() {
        Cursor dataCongViec = database.GetData("Select * from CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()) {
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id, ten));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }
}