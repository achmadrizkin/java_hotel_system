package com.example.java_hotel_system.view.bottomNav.booking.booking_filter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewBooking;
import com.example.java_hotel_system.dao.DaoBooking;
import com.example.java_hotel_system.dao.DaoKamar;
import com.example.java_hotel_system.model.booking.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingFilter extends AppCompatActivity {
    private RecyclerView rvBooking;
    private RecyclerViewBooking recyclerViewBooking;
    private DaoBooking dao;
    private FirebaseAuth mAuth;
    private ConstraintLayout clHaveData, clNoData;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_filter);

        rvBooking = findViewById(R.id.rvBooking);
        clHaveData = findViewById(R.id.clHaveData);
        clNoData = findViewById(R.id.clNoData);
        ivBack = findViewById(R.id.ivBack);

        Intent intent = getIntent();
        String current_date = intent.getExtras().getString("current_date");

        dao = new DaoBooking();
        mAuth = FirebaseAuth.getInstance();

        //
        clHaveData.setVisibility(View.GONE);
        clNoData.setVisibility(View.GONE);

        //
        getBookingByUserUID(mAuth.getCurrentUser().getUid(), current_date);
        initRecyclerViewTrendingRoom();

        //
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getBookingByUserUID(String uid, String current_date) {
        dao.getBookingByUser(uid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Booking> booking = new ArrayList<>();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Booking book = data.getValue(Booking.class);

                    // check if user get current date, and then add data to list
                    if (book.getCurrent_date().equals(current_date)) {
                        booking.add(book);
                    }
                }

                // check data if null
                if (booking.isEmpty()) {
                    clHaveData.setVisibility(View.GONE);
                    clNoData.setVisibility(View.VISIBLE);
                } else {
                    clNoData.setVisibility(View.GONE);
                    clHaveData.setVisibility(View.VISIBLE);
                }

                recyclerViewBooking.setListDataItems(booking);
                recyclerViewBooking.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // NULL DATA
            }
        });
    }

    private void initRecyclerViewTrendingRoom() {
        recyclerViewBooking = new RecyclerViewBooking(getApplicationContext());

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvBooking.setLayoutManager(horizontalLayoutManager);
        rvBooking.setAdapter(recyclerViewBooking);
    }
}