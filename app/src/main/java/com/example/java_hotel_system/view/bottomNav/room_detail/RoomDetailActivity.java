package com.example.java_hotel_system.view.bottomNav.room_detail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.dao.DaoKamar;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.bottomNav.profile.add_room.AddRoomActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.edit.EditRoomActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.payment.PaymentSuccessActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RoomDetailActivity extends AppCompatActivity {
    ImageView ivPhotoRoom, ivBack, ivEdit, ivDelete;
    TextView tvName, tvRating, tvCity, tvDeskripsi, tvBed, tvRoom, tvNotLogin, tvHarga, tvURL;
    Button btnMaps, btnBooking;
    EditText etCheckIn, etCheckOut;

    //
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Intent intent = getIntent();
        String key = intent.getExtras().getString("key");
        String name = intent.getExtras().getString("name");
        String image_url = intent.getExtras().getString("image_url");
        String rating = intent.getExtras().getString("rating");
        String kota = intent.getExtras().getString("kota");
        String deskripsi = intent.getExtras().getString("deskripsi");
        String jmlh_ruangan = intent.getExtras().getString("jmlh_ruangan");
        String jmlh_kasur = intent.getExtras().getString("jmlh_kasur");
        String location = intent.getExtras().getString("location");
        String kd_kamar = intent.getExtras().getString("kd_kamar");
        String harga = intent.getExtras().getString("harga");

        ivPhotoRoom = findViewById(R.id.ivPhotoRoom);
        tvURL = findViewById(R.id.tvURL);
        tvName = findViewById(R.id.textView17);
        tvRating = findViewById(R.id.textView18);
        tvCity = findViewById(R.id.textView16);
        tvDeskripsi = findViewById(R.id.textView23);
        tvBed = findViewById(R.id.tvBed);
        tvRoom = findViewById(R.id.tvRoom);
        btnMaps = findViewById(R.id.btnMaps);
        ivBack = findViewById(R.id.ivBack);
        ivEdit = findViewById(R.id.ivEdit);
        btnBooking = findViewById(R.id.btnBooking);
        etCheckIn = findViewById(R.id.editTextDate2);
        etCheckOut = findViewById(R.id.editTextDate3);
        tvNotLogin = findViewById(R.id.tvNotLogin);
        tvHarga = findViewById(R.id.tvHarga);
        ivDelete = findViewById(R.id.ivDelete);

        mAuth = FirebaseAuth.getInstance();

        // SET
        tvName.setText(name);
        tvBed.setText(jmlh_kasur);
        tvRoom.setText(jmlh_ruangan);
        tvRating.setText("☆ " + rating);
        tvCity.setText(kota);
        tvHarga.setText(harga);

        tvDeskripsi.setText(deskripsi);
        Glide.with(this).load(image_url).placeholder(R.drawable.erorr_picture).dontAnimate().into(ivPhotoRoom);
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

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditRoomActivity.class);
                i.putExtra("key", key);
                i.putExtra("name", name);
                i.putExtra("kd_kamar", kd_kamar);
                i.putExtra("image_url", image_url);
                i.putExtra("rating", rating);
                i.putExtra("kota", kota);
                i.putExtra("deskripsi", deskripsi);
                i.putExtra("jmlh_ruangan", jmlh_ruangan);
                i.putExtra("jmlh_kasur", jmlh_kasur);
                i.putExtra("location", location);
                i.putExtra("harga", harga);
                startActivity(i);
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DELETE
                removeKamar(key);
            }
        });

        if (mAuth.getCurrentUser() != null) {
            btnBooking.setVisibility(View.VISIBLE);
        } else {
            btnBooking.setVisibility(View.GONE);
            tvNotLogin.setVisibility(View.VISIBLE);
        }

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String checkIn = etCheckIn.getText().toString();
                String checkOut = etCheckOut.getText().toString();

                if (checkIn.isEmpty() || checkOut.isEmpty()) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Fill empty Check In and Check Out",
                            Toast.LENGTH_LONG
                    ).show();
                } else {

                }
            }
        });
    }

    private void removeKamar(String key) {
        DaoKamar dao = new DaoKamar();
        dao.remove(key).addOnSuccessListener(suc -> {
            Toast.makeText(RoomDetailActivity.this, "Delete Data Success", Toast.LENGTH_LONG).show();
            finish();
        }).addOnFailureListener(er -> {
            Toast.makeText(RoomDetailActivity.this, "Delete Data Fail", Toast.LENGTH_LONG).show();
        });
    }

}