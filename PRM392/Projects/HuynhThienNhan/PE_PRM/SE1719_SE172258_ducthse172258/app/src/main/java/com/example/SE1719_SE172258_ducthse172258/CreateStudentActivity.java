package com.example.SE1719_SE172258_ducthse172258;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.SE1719_SE172258_ducthse172258.apdater.StudentAdapter;
import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentService;
import com.example.SE1719_SE172258_ducthse172258.models.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateStudentActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextView textView;
    private EditText edtName, edtEmail, edtAddress, edtGender, edtDate, edtMajor;
    private AppCompatButton btnSave;
    StudentService studentService;
    StudentAdapter studentAdapter;
    List<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (areAllFieldsFilled()) {
                    create_new_student();
                }
            }
        });
    }

    private void create_new_student() {
        studentService = StudentRepository.getStudentService();
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String address = edtAddress.getText().toString();
        String gender = edtGender.getText().toString();
        String date = edtDate.getText().toString();
        long major = Long.parseLong(edtMajor.getText().toString());

        Student student = new Student(name, date, gender, email, address, major);
        Call<Student> call = studentService.createStudent(student);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.body() != null) {
                    Toast.makeText(CreateStudentActivity.this, "Save Student Successfully", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                    studentList.add(response.body());
                    notifyDataSetChangedToAdapter();
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Toast.makeText(CreateStudentActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable throwable) {
                Toast.makeText(CreateStudentActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notifyDataSetChangedToAdapter() {
        if (studentAdapter != null) {
            studentAdapter.notifyDataSetChanged(); // Notify adapter of data change
        }
    }


    private void initView() {
        backBtn = findViewById(R.id.back_btn);
        textView = findViewById(R.id.textView);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        edtGender = findViewById(R.id.edt_gender);
        edtDate = findViewById(R.id.edt_date);
        edtMajor = findViewById(R.id.edt_major);
        btnSave = findViewById(R.id.btn_save);
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show the date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CreateStudentActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        edtDate.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void clearInputFields() {
        edtName.setText("");
        edtEmail.setText("");
        edtAddress.setText("");
        edtGender.setText("");
        edtDate.setText("");
        edtMajor.setText("");

    }

    private boolean areAllFieldsFilled() {
        if (edtName.getText().toString().isEmpty() ||
                edtEmail.getText().toString().isEmpty() ||
                edtAddress.getText().toString().isEmpty() ||
                edtGender.getText().toString().isEmpty() ||
                edtDate.getText().toString().isEmpty() ||
                edtMajor.getText().toString().isEmpty()) {
            Toast.makeText(CreateStudentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}