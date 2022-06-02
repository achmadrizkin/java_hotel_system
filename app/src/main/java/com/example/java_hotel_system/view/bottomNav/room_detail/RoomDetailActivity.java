package com.example.java_hotel_system.view.bottomNav.room_detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.java_hotel_system.R;

public class RoomDetailActivity extends AppCompatActivity {
    TextView tvNameDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");

        tvNameDetail = findViewById(R.id.tvNameDetail);

        //
        tvNameDetail.setText(name);
    }
}