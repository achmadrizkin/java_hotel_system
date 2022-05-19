package com.example.java_hotel_system.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;

public class LoginActivity extends AppCompatActivity {
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvSkip = findViewById(R.id.tvSkip);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                startActivity(p);
            }
        });
    }
}