package com.example.adapter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
//    ArrayList<String> arrayList;//ArrayAdapter
    ArrayList<Person> arrayList;//BaseAdapter
    //    ArrayAdapter<String> adapter;//ArrayAdapter
    PersonAdapter adapter;//BaseAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        //ArrayAdapter
//        arrayList.add("Dat");
//        arrayList.add("Thuc");
//        arrayList.add("Hieu");
//        arrayList.add("Nhan");
//        arrayList.add("Thuc");
//        arrayList.add("Kiet");
//        arrayList.add("Hieu");
        //BaseAdapter
        arrayList.add(new Person("Dat", 22));
        arrayList.add(new Person("Thuc", 22));
        arrayList.add(new Person("Hieu", 22));
        arrayList.add(new Person("Nhan", 22));
        arrayList.add(new Person("Thuc", 22));
        arrayList.add(new Person("Kiet", 22));
        arrayList.add(new Person("Hieu", 22));

//        adapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, arrayList);//ArrayAdapter
        adapter = new PersonAdapter(this, arrayList);//BaseAdapter
        listView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}