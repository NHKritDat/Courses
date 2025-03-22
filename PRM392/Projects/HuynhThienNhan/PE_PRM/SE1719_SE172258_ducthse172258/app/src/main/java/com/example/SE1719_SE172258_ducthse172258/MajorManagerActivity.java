package com.example.SE1719_SE172258_ducthse172258;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MajorManagerActivity extends AppCompatActivity {
    ImageView btn_back;
    MajorAdapter majorAdapter;
    ListView major_list_view;
    MajorService majorService;
    List<Major> majorList = new ArrayList<>();
    AppCompatButton btn_navigate_create_major;
    private static final int CREATE_MAJOR_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_major_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initMajorListView();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_navigate_create_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MajorManagerActivity.this, CreateMajorActivity.class);
                startActivityForResult(intent, CREATE_MAJOR_REQUEST_CODE);
            }
        });
    }

    private void initMajorListView() {
        majorService = MajorRepository.getMajorService();
        Call<Major[]> call = majorService.getAllMajor();
        call.enqueue(new Callback<Major[]>() {
            @Override
            public void onResponse(Call<Major[]> call, Response<Major[]> response) {
                if (response.body() != null) {
                    majorList.clear();
                    majorList.addAll(Arrays.asList(response.body()));
                    majorAdapter = new MajorAdapter(MajorManagerActivity.this, majorList);
                    major_list_view.setAdapter(majorAdapter);
                } else {
                    Toast.makeText(MajorManagerActivity.this, "fetch api failed", Toast.LENGTH_SHORT).show();
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<Major[]> call, Throwable throwable) {
                Toast.makeText(MajorManagerActivity.this, "fetch api failed", Toast.LENGTH_SHORT).show();
                Log.d("Error", throwable.toString());
            }
        });

    }

    private void initView() {
        btn_back = findViewById(R.id.back_btn_home);
        major_list_view = findViewById(R.id.major_list);
        btn_navigate_create_major = findViewById(R.id.btn_navigate_create_major);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_MAJOR_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh the adapter
            initMajorListView();
        }
    }
}