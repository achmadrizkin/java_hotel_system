package com.example.java_hotel_system.view.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.view.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private int waktu_loading = 1000; // 1 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent p = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(p);
                finish();

            }
        }, waktu_loading);
    }
}