package com.example.lab_6;

import static android.widget.Toast.LENGTH_SHORT;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout manHinh;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mShare)
            Toast.makeText(this, "Bạn đã chọn item Share", LENGTH_SHORT).show();
        if (item.getItemId() == R.id.mSearch)
            Toast.makeText(this, "Bạn đã chọn item Search", LENGTH_SHORT).show();
        if (item.getItemId() == R.id.mEmail)
            Toast.makeText(this, "Bạn đã chọn item Email", LENGTH_SHORT).show();
        if (item.getItemId() == R.id.mPhone)
            Toast.makeText(this, "Bạn đã chọn item Phone", LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mVang)
            manHinh.setBackgroundColor(Color.YELLOW);
        if (item.getItemId() == R.id.mDo)
            manHinh.setBackgroundColor(Color.RED);
        if (item.getItemId() == R.id.mXanh)
            manHinh.setBackgroundColor(Color.BLUE);
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        Button btnMenu = findViewById(R.id.button);

        btnMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, btnMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.mThem)
                    Toast.makeText(this, "Bạn đã chọn item Them", LENGTH_SHORT).show();
                if (item.getItemId() == R.id.mSua)
                    Toast.makeText(this, "Bạn đã chọn item Sua", LENGTH_SHORT).show();
                if (item.getItemId() == R.id.mXoa)
                    Toast.makeText(this, "Bạn đã chọn item Xoa", LENGTH_SHORT).show();
                return false;
            });
            popupMenu.show();
        });

        manHinh = findViewById(R.id.main);
        Button btnMau = findViewById(R.id.button2);
        registerForContextMenu(btnMau);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}