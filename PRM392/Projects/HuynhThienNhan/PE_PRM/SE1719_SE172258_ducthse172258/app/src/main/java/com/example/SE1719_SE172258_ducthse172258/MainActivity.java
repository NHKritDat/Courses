package com.example.SE1719_SE172258_ducthse172258;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.SE1719_SE172258_ducthse172258.apdater.StudentAdapter;
import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentService;
import com.example.SE1719_SE172258_ducthse172258.models.Student;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btn_navigate_create_student, btn_navigate_create_major, btn_map;
    private ListView listView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    StudentService studentService;
    private ImageView logout_btn;

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private static final int CREATE_STUDENT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListTraineeView();
        auth = FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignIn.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.wed_id))
                        .requestEmail()
                        .build());
        btn_navigate_create_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate_create_student();
            }
        });
        btn_navigate_create_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate_create_major();
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMap();
            }
        });
    }

    private void gotoMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private void logout() {

        // logout
        // Sign out from Firebase
        auth.signOut();

        // Sign out from Google
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // After sign out, navigate to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


    }


    private void initListTraineeView() {
        studentService = StudentRepository.getStudentService();
        studentList = new ArrayList<>();
        Call<Student[]> call = studentService.getAllStudent();
        call.enqueue(new Callback<Student[]>() {
            @Override
            public void onResponse(Call<Student[]> call, Response<Student[]> response) {
                if (response.body() != null) {
                    studentList.clear();
                    studentList.addAll(Arrays.asList(response.body()));
                    studentAdapter = new StudentAdapter(MainActivity.this, studentList);
                    listView.setAdapter(studentAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "fetch api failed", Toast.LENGTH_SHORT).show();
                    Log.d("Error", response.message());


                }
            }

            @Override
            public void onFailure(Call<Student[]> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Failed to load students", Toast.LENGTH_SHORT).show();
                Log.d("Error", throwable.toString());
            }
        });

    }

    private void navigate_create_student() {
        Intent intent = new Intent(this, CreateStudentActivity.class);
        startActivityForResult(intent, CREATE_STUDENT_REQUEST_CODE);
    }

    private void navigate_create_major() {
        Intent intent = new Intent(this, MajorManagerActivity.class);
        startActivity(intent);

    }

    private void initView() {
        btn_navigate_create_student = findViewById(R.id.btn_navigate_create_student);
        btn_navigate_create_major = findViewById(R.id.btn_navigate_create_major);
        listView = findViewById(R.id.student_list_view);
        logout_btn = findViewById(R.id.logout_btn);
        btn_map = findViewById(R.id.map_go);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_STUDENT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh the adapter
            initListTraineeView();
        }
    }
}