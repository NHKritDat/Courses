package com.example.lab_10;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView view;
    private AppDatabase database;
    private FloatingActionButton fabAdd;
    private PersonAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> startActivity(new Intent(this,
                EditPersonActivity.class)));

        view = findViewById(R.id.rvPerson);
        view.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PersonAdapter(this);
        view.setAdapter(adapter);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int posotion = viewHolder.getAdapterPosition();
                        List<Person> tasks = adapter.getTasks();
                        database.personDao().delete(tasks.get(posotion));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Person> people = database.personDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setTasks(people);
                    }
                });
            }
        });
    }
}