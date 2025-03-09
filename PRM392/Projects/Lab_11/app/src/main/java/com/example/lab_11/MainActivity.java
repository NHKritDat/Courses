package com.example.lab_11;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_11.adapter.TraineeAdapter;
import com.example.lab_11.model.Trainee;
import com.example.lab_11.model.api.TraineeRepository;
import com.example.lab_11.model.api.TraineeService;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TraineeService traineeService;
    int selectedItemPosition = -1;
    ArrayList<Trainee> list;
    EditText etName, etEmail, etPhone, etGender;
    TraineeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        traineeService = TraineeRepository.getTraineeService();
        RecyclerView recyclerView = findViewById(R.id.rvTrainee);

        getAll(recyclerView);

        findViewById(R.id.btnAdd).setOnClickListener(v -> showDialogAdd(recyclerView));
        findViewById(R.id.btnEdit).setOnClickListener(v -> {
            if (selectedItemPosition != -1)
                showDialogEdit(recyclerView, selectedItemPosition);
            else
                Toast.makeText(this, "Vui lòng chọn một mục để sửa!", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if (selectedItemPosition != -1)
                showDialogDelete(recyclerView, selectedItemPosition);
            else
                Toast.makeText(this, "Vui lòng chọn một mục để xóa!", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getAll(RecyclerView recyclerView) {
        Call<Trainee[]> call = traineeService.getAll();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Trainee[]> call, @NonNull Response<Trainee[]> response) {
                Trainee[] trainees = response.body();
                if (trainees == null) {
                    return;
                }

                list = new ArrayList<>();
                list.addAll(Arrays.asList(trainees));

                adapter = new TraineeAdapter(list, position -> {
                    selectedItemPosition = position;
                    Toast.makeText(MainActivity.this, "Selected: " + list.get(position).getName(),
                            Toast.LENGTH_SHORT).show();
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(@NonNull Call<Trainee[]> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Get Failed", LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogAdd(RecyclerView recyclerView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new Trainee");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        builder.setView(view);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etGender = view.findViewById(R.id.etGender);

        builder.setPositiveButton("Add", (dialog, which) -> {
            Trainee trainee = new Trainee(etName.getText().toString(), etEmail.getText().toString(),
                    etPhone.getText().toString(), etGender.getText().toString());
            Call<Trainee> call = traineeService.create(trainee);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Trainee> call, @NonNull Response<Trainee> response) {
                    if (response.body() != null) {
                        getAll(recyclerView);
                        Toast.makeText(MainActivity.this, "Save successfully!", LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Trainee> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Save Failed", LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void showDialogEdit(RecyclerView recyclerView, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit a Trainee");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null);
        builder.setView(view);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etGender = view.findViewById(R.id.etGender);

        etName.setText(list.get(index).getName());
        etEmail.setText(list.get(index).getEmail());
        etPhone.setText(list.get(index).getPhone());
        etGender.setText(list.get(index).getGender());

        builder.setPositiveButton("Submit", (dialog, which) -> {
            Trainee trainee = new Trainee(etName.getText().toString(), etEmail.getText().toString(),
                    etPhone.getText().toString(), etGender.getText().toString());
            Call<Trainee> call = traineeService.updateTrainee(list.get(index).getId(), trainee);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Trainee> call, @NonNull Response<Trainee> response) {
                    if (response.body() != null) {
                        getAll(recyclerView);
                        Toast.makeText(MainActivity.this, "Edit successfully!", LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Trainee> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Edit Failed", LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDialogDelete(RecyclerView recyclerView, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete a Trainee");
        builder.setMessage("Do you want to delete this item?");

        builder.setPositiveButton("Submit", (dialog, which) -> {
            if (index >= 0 && index < list.size()) {
                Call<Trainee> call = traineeService.delete(list.get(index).getId());
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Trainee> call, @NonNull Response<Trainee> response) {
                        if (response.body() != null) {
                            getAll(recyclerView);
                            Toast.makeText(MainActivity.this, "Remove successfully!", LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Trainee> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Remove Failed", LENGTH_SHORT).show();
                    }
                });
                selectedItemPosition = -1;
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}