package com.example.lab_1_2_3.Lab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab_1_2_3.R;

import java.util.ArrayList;
import java.util.List;

public class Lab3ListViewActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<String> listItems;
    private int selectedItemPosition = -1;
    private EditText editTextItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab3_list_view);

        findViewById(R.id.btnBackLab3ListView).setOnClickListener(v -> finish());

        ListView listView = findViewById(R.id.listView);
        editTextItem = findViewById(R.id.editTextItem);

        listItems = new ArrayList<>();
        listItems.add("Android");
        listItems.add("PHP");
        listItems.add("iOS");
        listItems.add("Unity");
        listItems.add("ASP.NET");

        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.textItem, listItems);
        listView.setAdapter(adapter);
        findViewById(R.id.addButton).setOnClickListener(view -> {
            String newItem = editTextItem.getText().toString().trim();
            if (!newItem.isEmpty()) {
                listItems.add(newItem);
                adapter.notifyDataSetChanged();
                editTextItem.setText(""); // Clear input field
            } else {
                Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.editButton).setOnClickListener(view -> {
            if (selectedItemPosition >= 0) {
                String updatedItem = editTextItem.getText().toString().trim();
                if (!updatedItem.isEmpty()) {
                    listItems.set(selectedItemPosition, updatedItem);
                    adapter.notifyDataSetChanged();
                    editTextItem.setText("");
                    selectedItemPosition = -1;
                } else {
                    Toast.makeText(this, "Please enter a valid item", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select an item to edit", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.deleteButton).setOnClickListener(view -> {
            if (selectedItemPosition >= 0) {
                listItems.remove(selectedItemPosition);
                adapter.notifyDataSetChanged();
                editTextItem.setText("");
                selectedItemPosition = -1;
            } else {
                Toast.makeText(this, "Please select an item to delete", Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.refreshButton).setOnClickListener(view -> {

            listItems.clear();
            listItems.add("Android");
            listItems.add("PHP");
            listItems.add("iOS");
            listItems.add("Unity");
            listItems.add("ASP.NET");
            adapter.notifyDataSetChanged();
            editTextItem.setText("");
        });


        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemPosition = position;
            editTextItem.setText(listItems.get(position));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}