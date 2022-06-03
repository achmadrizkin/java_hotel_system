package com.example.java_hotel_system.view.bottomNav.room_detail;

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
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;

import java.util.Locale;

public class RoomDetailActivity extends AppCompatActivity {
    ImageView ivPhotoRoom, ivBack;
    TextView tvName, tvRating, tvCity, tvDeskripsi, tvBed, tvRoom;
    Button btnMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String image_url = intent.getExtras().getString("image_url");
        String rating = intent.getExtras().getString("rating");
        String kota = intent.getExtras().getString("kota");
        String deskripsi = intent.getExtras().getString("deskripsi");
        String jmlh_ruangan = intent.getExtras().getString("jmlh_ruangan");
        String jmlh_kasur = intent.getExtras().getString("jmlh_kasur");
        String location = intent.getExtras().getString("location");

        ivPhotoRoom = findViewById(R.id.ivPhotoRoom);
        tvName = findViewById(R.id.textView17);
        tvRating = findViewById(R.id.textView18);
        tvCity = findViewById(R.id.textView16);
        tvDeskripsi = findViewById(R.id.textView23);
        tvBed = findViewById(R.id.tvBed);
        tvRoom = findViewById(R.id.tvRoom);
        btnMaps = findViewById(R.id.btnMaps);
        ivBack = findViewById(R.id.ivBack);

        // SET
        tvName.setText(name);
        tvBed.setText(jmlh_kasur);
        tvRoom.setText(jmlh_ruangan);
        tvRating.setText("☆ " + rating);
        tvCity.setText(kota);
        tvDeskripsi.setText(deskripsi);
        Glide.with(ivPhotoRoom).load(image_url).into(ivPhotoRoom);

        //
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OPEN GOOGLE MAPS SPESIFIC LOCATION
                // CONTOH: -6.193125, 106.821810
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            }
        });
    }
}