package com.example.SE1719_SE172258_ducthse172258;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.SE1719_SE172258_ducthse172258.apdater.MajorAdapter;
import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorService;
import com.example.SE1719_SE172258_ducthse172258.models.Major;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMajorActivity extends AppCompatActivity {
    AppCompatButton btn_save;
    private ImageView back_mj_btn;
    private EditText edt_name_major;
    MajorService majorService;
    List<Major> majorList  = new ArrayList<>();;
    MajorAdapter majorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_major);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        back_mj_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_major();
            }
        });

    }

    private void save_major() {
        majorService = MajorRepository.getMajorService();
        String name = edt_name_major.getText().toString();
        Major major = new Major(name);
        Call<Major> call = majorService.createMajor(major);
        call.enqueue(new Callback<Major>() {
            @Override
            public void onResponse(Call<Major> call, Response<Major> response) {
                if (response.body() != null) {
                    Toast.makeText(CreateMajorActivity.this, "Save Major Successfully", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                    majorList.add(response.body());
                    notifyDataSetChangedToAdapter();
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Toast.makeText(CreateMajorActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Major> call, Throwable throwable) {
                Toast.makeText(CreateMajorActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void clearInputFields() {
        edt_name_major.setText("");
    }

    private void initView() {
        btn_save = findViewById(R.id.btn_save_major);
        back_mj_btn = findViewById(R.id.back_mj_btn);
        edt_name_major = findViewById(R.id.edt_name_major);
    }

    private void notifyDataSetChangedToAdapter() {
        if (majorAdapter != null) {
            majorAdapter.notifyDataSetChanged(); // Notify adapter of data change
        }
    }
}