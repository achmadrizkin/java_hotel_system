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
import com.example.java_hotel_system.model.booking.PostBookingRequest;
import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.edit.EditRoomActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.payment.PaymentSuccessActivity;
import com.example.java_hotel_system.view_model.RoomDetailViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RoomDetailActivity extends AppCompatActivity {
    ImageView ivPhotoRoom, ivBack, ivEdit;
    TextView tvName, tvRating, tvCity, tvDeskripsi, tvBed, tvRoom, tvNotLogin;
    Button btnMaps, btnBooking;
    EditText etCheckIn, etCheckOut;

    //
    private RoomDetailViewModel viewModel;
    private FirebaseAuth mAuth;

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
        String kd_kamar = intent.getExtras().getString("kd_kamar");

        ivPhotoRoom = findViewById(R.id.ivPhotoRoom);
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

        viewModel = new ViewModelProvider(this).get(RoomDetailViewModel.class);
        mAuth = FirebaseAuth.getInstance();

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

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditRoomActivity.class));
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
                    String uniqueID = UUID.randomUUID().toString();
                    PostBookingRequest b = new PostBookingRequest(uniqueID, mAuth.getUid(), kd_kamar, checkOut, checkIn);

                    postBooking(b);
                }
            }
        });
    }

    private void postBooking(PostBookingRequest postBookingRequest) {
        viewModel = new ViewModelProvider(this).get(RoomDetailViewModel.class);
        viewModel.postBookingObservable().observe(RoomDetailActivity.this, new Observer<PostBookingRequest>() {
            @Override
            public void onChanged(PostBookingRequest recyclerData) {
                if (recyclerData == null) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Failed to booking room",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Success to booking room",
                            Toast.LENGTH_LONG
                    ).show();

                    startActivity(new Intent(getApplicationContext(), PaymentSuccessActivity.class));
                    finish();
                }
            }
        });
        viewModel.postBookingOfData(postBookingRequest);
    }
}