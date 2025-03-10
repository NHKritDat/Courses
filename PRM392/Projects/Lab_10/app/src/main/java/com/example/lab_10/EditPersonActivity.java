package com.example.lab_10;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class EditPersonActivity extends AppCompatActivity {
    private EditText etFirstName;
    private EditText etLastName;
    private Button btnSave;
    private int mPersonId;
    private Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        setSupportActionBar(findViewById(R.id.toolbar2));

        initViews();
        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_PERSON_ID)) {
            btnSave.setText("Update");
            mPersonId = intent.getIntExtra(Constants.UPDATE_PERSON_ID, -1);
            AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDao().getById(mPersonId);
                    populateUI(person);
                }
            });
        }
    }

    private void populateUI(Person person) {
        if (person == null)
            return;
        etFirstName.setText(person.getFirstName());
        etLastName.setText(person.getLastName());
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            onSaveButtonClicked();
        });
    }

    public void onSaveButtonClicked() {
        final Person person = new Person(etFirstName.getText().toString(), etLastName.getText().toString());
        AppExecutors.getInstance().getDiskIO().execute(() -> {
            if (!intent.hasExtra(Constants.UPDATE_PERSON_ID)) {
                mDb.personDao().insert(person);
            } else {
                person.setUid(mPersonId);
                mDb.personDao().update(person);
            }
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}