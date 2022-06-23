package com.example.java_hotel_system.view.bottomNav.profile.history_booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewBooking;
import com.example.java_hotel_system.dao.DaoBooking;
import com.example.java_hotel_system.model.booking.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryBookingActivity extends AppCompatActivity {

    private ImageView ivBack;
    private RecyclerView rvHistoryBooking;
    private RecyclerViewBooking recyclerViewBooking;
    private ConstraintLayout clNoData;
    private DaoBooking dao;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_booking);

        ivBack = findViewById(R.id.ivBack);
        rvHistoryBooking = findViewById(R.id.rvHistoryBooking);
        clNoData = findViewById(R.id.clNoData);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dao = new DaoBooking();

        //
        initRecyclerViewTrendingRoom();
        getBookingByUserUID(user.getUid());

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRecyclerViewTrendingRoom() {
        recyclerViewBooking = new RecyclerViewBooking(getApplicationContext());

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvHistoryBooking.setLayoutManager(horizontalLayoutManager);
        rvHistoryBooking.setAdapter(recyclerViewBooking);
    }

    private void getBookingByUserUID(String uid) {
        dao.getBookingByUser(uid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Booking> booking = new ArrayList<>();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Booking book = data.getValue(Booking.class);
                    booking.add(book);
                }

                // check data if null
                if (booking.isEmpty()) {
                    rvHistoryBooking.setVisibility(View.GONE);
                    clNoData.setVisibility(View.VISIBLE);
                } else {
                    clNoData.setVisibility(View.GONE);
                    rvHistoryBooking.setVisibility(View.VISIBLE);
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
}