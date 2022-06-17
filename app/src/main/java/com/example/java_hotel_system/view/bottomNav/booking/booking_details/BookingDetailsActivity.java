package com.example.java_hotel_system.view.bottomNav.booking.booking_details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.util.MCrypt;
import com.example.java_hotel_system.view.bottomNav.booking.show_qr.ShowQrActivity;

public class BookingDetailsActivity extends AppCompatActivity {

    private ImageView ivBack, ivPhotoRoom;
    private TextView tvNamaKamar, tvRating;
    private Button btnMaps, btnShowQR;
    String encrypted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Intent intent = getIntent();
        String image_url = intent.getExtras().getString("image_url");
        String nama_kamar = intent.getExtras().getString("nama_kamar");
        String rating = intent.getExtras().getString("rating");
        String lokasi = intent.getExtras().getString("lokasi");
        String kd_booking = intent.getExtras().getString("kd_booking");

        ivBack = findViewById(R.id.ivBack);
        ivPhotoRoom = findViewById(R.id.ivPhotoRoom);
        tvNamaKamar = findViewById(R.id.tvNamaKamar);
        tvRating = findViewById(R.id.textView18);
        btnMaps = findViewById(R.id.btnMaps);
        btnShowQR = findViewById(R.id.btnShowQR);

        tvNamaKamar.setText(nama_kamar);
        tvNamaKamar.setText(nama_kamar);
        tvRating.setText(rating);

        // encrypted
        MCrypt mcrypt = new MCrypt();
        try {
            encrypted = MCrypt.bytesToHex( mcrypt.encrypt(kd_booking));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Glide.with(this).load(image_url).placeholder(R.drawable.erorr_picture).dontAnimate().into(ivPhotoRoom);

        //
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OPEN GOOGLE MAPS SPESIFIC LOCATION
                // CONTOH: -6.193125, 106.821810
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(lokasi));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnShowQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ShowQrActivity.class);

                i.putExtra("kd_booking", encrypted);
                startActivity(i);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}