package com.example.java_hotel_system.view.bottomNav.booking.show_qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.java_hotel_system.R;

import net.glxn.qrgen.android.QRCode;

public class ShowQrActivity extends AppCompatActivity {

    private ImageView ivQrCode, ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);

        Intent intent = getIntent();
        String kd_booking = intent.getExtras().getString("kd_booking");

        ivQrCode = findViewById(R.id.ivQrCode);
        ivBack = findViewById(R.id.ivBack);

        // MAKE QR
        Bitmap myBitmap = QRCode.from(kd_booking).bitmap();
        ivQrCode.setImageBitmap(myBitmap);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}