package com.example.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private RecyclerView mRecyclerView;
    private PersonAdapter mAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonActivity.this, EditPersonActivity.class));
            }
        });
        mRecyclerView = findViewById(R.id.rvPerson);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        // Khởi tạo Adapter
        mAdapter = new PersonAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mDb = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"app-database").build();

        // Xử lý vuốt để xóa
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutor.getInstance().DiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Person> persons = mAdapter.getTasks();
                        mDb.personDAO().delete(persons.get(position));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutor.getInstance().DiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Person> persons = mDb.personDAO().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(persons);
                    }
                });
            }
        });
    }
}
