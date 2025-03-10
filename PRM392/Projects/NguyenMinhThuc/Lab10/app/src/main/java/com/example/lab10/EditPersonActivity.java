package com.example.lab10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

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
    protected void onCreate(final Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_person_edit);
        // Ánh xạ Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();
        mDb = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"app-database").build();
        intent =getIntent();
        if(intent != null && intent.hasExtra(CRUD.UPDATE_Person_Id)){
            btnSave.setText("Update");
            mPersonId = intent.getIntExtra(CRUD.UPDATE_Person_Id,-1);
            AppExecutor.getInstance().DiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDAO().LoadPersonById(mPersonId);
                    populateUI(person);
                }
            });
        }

    }
    private void populateUI(Person person){
        if(person == null){
            return;
        }
        etFirstName.setText(person.getFistName());
        etLastName.setText(person.getLastName());
    }
    private void initViews(){
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        btnSave =findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonCliked();
            }
        });
    }
    public  void onSaveButtonCliked(){
        final Person person = new Person(
                etFirstName.getText().toString(),
                etLastName.getText().toString());
        AppExecutor.getInstance().DiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(CRUD.UPDATE_Person_Id)) {
                    mDb.personDAO().insert(person);
                } else {
                    person.setUid(mPersonId);
                    mDb.personDAO().update(person);
                }
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                //onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
