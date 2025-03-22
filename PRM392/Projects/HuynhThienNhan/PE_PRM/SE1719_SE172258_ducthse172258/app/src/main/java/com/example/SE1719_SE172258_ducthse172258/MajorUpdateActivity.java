package com.example.SE1719_SE172258_ducthse172258;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Major.MajorService;
import com.example.SE1719_SE172258_ducthse172258.models.Major;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MajorUpdateActivity extends AppCompatActivity {
    ImageView back_mj_btn;
    EditText edt_name_major;
    AppCompatButton btn_save;
    Long Major_id;
    MajorService majorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_major_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intiView();
        getDataFromIntent();
        back_mj_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMajor();
            }
        });
    }

    private void updateMajor() {
        majorService = MajorRepository.getMajorService();
        String name = edt_name_major.getText().toString();


        Major updatedMajor = new Major(name);
        Call<Major> call = majorService.updateMajor(Major_id, updatedMajor);
        call.enqueue(new Callback<Major>() {
            @Override
            public void onResponse(Call<Major> call, Response<Major> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MajorUpdateActivity.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MajorUpdateActivity.this, MajorManagerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MajorUpdateActivity.this, "Api error", Toast.LENGTH_SHORT).show();
                    Log.e("Api errorr::", response.message());
                }
            }

            @Override
            public void onFailure(Call<Major> call, Throwable throwable) {
                Toast.makeText(MajorUpdateActivity.this, "Api error  ::", Toast.LENGTH_SHORT).show();
                Log.e("Api errorr1::", throwable.toString());

            }
        });
    }

    private void intiView() {
        back_mj_btn = findViewById(R.id.back_mj_btn);
        edt_name_major = findViewById(R.id.edt_name_major_mian);
        btn_save = findViewById(R.id.btn_save_major_u);


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String idString = intent.getStringExtra("ID");
            if (idString != null && !idString.isEmpty()) {
                Major_id = Long.parseLong(idString);
            }
            edt_name_major.setText(intent.getStringExtra("Name"));

        }
    }
}