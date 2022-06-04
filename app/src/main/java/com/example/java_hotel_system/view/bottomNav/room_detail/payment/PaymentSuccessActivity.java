package com.example.java_hotel_system.view.bottomNav.room_detail.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;

public class PaymentSuccessActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
                finish();
            }
        });
    }
}