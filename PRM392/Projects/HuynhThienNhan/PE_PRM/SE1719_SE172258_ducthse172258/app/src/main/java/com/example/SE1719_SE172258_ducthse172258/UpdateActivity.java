package com.example.SE1719_SE172258_ducthse172258;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentRepository;
import com.example.SE1719_SE172258_ducthse172258.api.Student.StudentService;
import com.example.SE1719_SE172258_ducthse172258.models.Student;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private ImageView back_btn;
    private TextView textView;
    private EditText edt_name, edt_email, edt_address, edt_gender, edt_date, edt_major;
    private androidx.appcompat.widget.AppCompatButton btn_save;
    private StudentService studentService;
    private Long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        getDataFromIntent();
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (areAllFieldsFilled()) {
                    updateStudent();
                }
            }
        });

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String idString = intent.getStringExtra("ID");
            if (idString != null && !idString.isEmpty()) {
                studentId = Long.parseLong(idString);
            }
            edt_name.setText(intent.getStringExtra("Name"));
            edt_email.setText(intent.getStringExtra("Email"));
            edt_address.setText(intent.getStringExtra("Address"));
            edt_gender.setText(intent.getStringExtra("Gender"));
            edt_date.setText(intent.getStringExtra("Date"));
            edt_major.setText(intent.getStringExtra("Major"));

        }
    }

    private void updateStudent() {
        studentService = StudentRepository.getStudentService();
        String name = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String address = edt_address.getText().toString();
        String gender = edt_gender.getText().toString();
        String date = edt_date.getText().toString();
        long major =Long.parseLong( edt_major.getText().toString());

        Student updatedStudent = new Student(name, date, gender, email, address, major);
        Call<Student> call = studentService.updateStudent(studentId, updatedStudent);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, "Update Succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, "Api error", Toast.LENGTH_SHORT).show();
                    Log.e("Api errorr::", response.message());
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable throwable) {
                Toast.makeText(UpdateActivity.this, "Api error  ::", Toast.LENGTH_SHORT).show();
                Log.e("Api errorr1::", throwable.toString());

            }
        });
    }

    private void initView() {
        back_btn = findViewById(R.id.back_btn);
        textView = findViewById(R.id.textView);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_address = findViewById(R.id.edt_address);
        edt_gender = findViewById(R.id.edt_gender);
        edt_date = findViewById(R.id.edt_date);
        edt_major = findViewById(R.id.edt_major);
        btn_save = findViewById(R.id.btn_save);
    }

    private boolean areAllFieldsFilled() {
        if (edt_name.getText().toString().isEmpty() ||
                edt_email.getText().toString().isEmpty() ||
                edt_address.getText().toString().isEmpty() ||
                edt_gender.getText().toString().isEmpty() ||
                edt_date.getText().toString().isEmpty() ||
                edt_major.getText().toString().isEmpty()) {
            Toast.makeText(UpdateActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show the date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                UpdateActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        edt_date.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

}