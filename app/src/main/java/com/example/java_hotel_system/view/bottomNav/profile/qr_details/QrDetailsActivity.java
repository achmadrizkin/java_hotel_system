package com.example.java_hotel_system.view.bottomNav.profile.qr_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.dao.DaoBooking;
import com.example.java_hotel_system.model.booking.Booking;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.util.MCrypt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QrDetailsActivity extends AppCompatActivity {
    String decrypted;
    private DaoBooking dao;
    private ProgressBar pbLoading;
    private ConstraintLayout clQrCodeSuccess;
    private TextView tvKodeBooking, tvUserId, tvCheckIn, tvCheckOut, tvTotalHarga;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_details);

        dao = new DaoBooking();

        pbLoading = findViewById(R.id.pbLoading);
        tvKodeBooking = findViewById(R.id.tvKodeBooking);
        tvUserId = findViewById(R.id.tvUserId);
        clQrCodeSuccess = findViewById(R.id.clQrCodeSuccess);
        btnBack = findViewById(R.id.btnBack);
        tvCheckIn = findViewById(R.id.tvCheckIn);
        tvCheckOut = findViewById(R.id.tvCheckOut);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);

        pbLoading.setVisibility(View.VISIBLE);
        clQrCodeSuccess.setVisibility(View.GONE);

        // decrpty first
        Intent intent = getIntent();
        String result = intent.getExtras().getString("result");

        // decrypted
        MCrypt mcrypt = new MCrypt();
        try {
            decrypted = new String( mcrypt.decrypt( result ) );
        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        getBookingByUser(decrypted);

        //
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getBookingByUser(String kd_booking) {
        dao.checkKdBooking(kd_booking).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Booking> bookings = new ArrayList<>();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Booking book = data.getValue(Booking.class);
                    bookings.add(book);
                }

                // check if data is not null
                if (bookings.get(0).getKd_booking().equals(decrypted)) {
                    clQrCodeSuccess.setVisibility(View.VISIBLE);

                    //
                    tvKodeBooking.setText(bookings.get(0).getKd_booking());
                    tvUserId.setText(bookings.get(0).getUser_buy());
                    tvCheckOut.setText(bookings.get(0).getCheck_out());
                    tvCheckIn.setText(bookings.get(0).getCheck_in());
                    tvTotalHarga.setText(bookings.get(0).getTotal());
                } else {
                    clQrCodeSuccess.setVisibility(View.GONE);
                }
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // NULL DATA
            }
        });
    }
}