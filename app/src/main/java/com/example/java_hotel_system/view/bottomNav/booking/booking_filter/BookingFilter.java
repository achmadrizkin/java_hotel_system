package com.example.java_hotel_system.view.bottomNav.booking.booking_filter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewBooking;
import com.example.java_hotel_system.dao.DaoBooking;
import com.example.java_hotel_system.dao.DaoKamar;
import com.example.java_hotel_system.model.booking.Booking;
import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.view.login.LoginActivity;
import com.example.java_hotel_system.view_model.UserDetailsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingFilter extends AppCompatActivity {
    private RecyclerView rvBooking;
    private RecyclerViewBooking recyclerViewBooking;
    private DaoBooking dao;
    private FirebaseAuth mAuth;
    private ConstraintLayout clHaveData, clNoData;
    private ImageView ivBack;
    private ProgressBar pbLoading;
    private UserDetailsViewModel viewModel;
    String current_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_filter);

        rvBooking = findViewById(R.id.rvBooking);
        clHaveData = findViewById(R.id.clHaveData);
        clNoData = findViewById(R.id.clNoData);
        ivBack = findViewById(R.id.ivBack);
        pbLoading = findViewById(R.id.pbLoading);

        Intent intent = getIntent();
        current_date = intent.getExtras().getString("current_date");

        dao = new DaoBooking();
        mAuth = FirebaseAuth.getInstance();

        //
        clHaveData.setVisibility(View.GONE);
        clNoData.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);

        // check role user
        getUserDetailByUIDFromView(mAuth.getCurrentUser().getUid());

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

    private void getBookingByNotRoleUserUID(String current_date) {
        dao.getBookingByCurrentDate(current_date).addValueEventListener(new ValueEventListener() {
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

    private void getUserDetailByUIDFromView(String uid) {
        viewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);
        viewModel.getUserDetailsByUIDObservable().observe(this, new Observer<ListUser>() {
            @Override
            public void onChanged(ListUser t) {
                if (t == null) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    if (t.getData().get(0).getRole().equals("user")) {
                        getBookingByUserUID(mAuth.getCurrentUser().getUid(), current_date);
                        initRecyclerViewTrendingRoom();

                        clHaveData.setVisibility(View.VISIBLE);
                        pbLoading.setVisibility(View.GONE);
                    } else {
                        getBookingByNotRoleUserUID(current_date);
                        initRecyclerViewTrendingRoom();

                        clHaveData.setVisibility(View.VISIBLE);
                        pbLoading.setVisibility(View.GONE);
                    }
                }
            }
        });

        viewModel.getUserDetailsByUIDOfData(uid);
    }


    private void initRecyclerViewTrendingRoom() {
        recyclerViewBooking = new RecyclerViewBooking(getApplicationContext());

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvBooking.setLayoutManager(horizontalLayoutManager);
        rvBooking.setAdapter(recyclerViewBooking);
    }
}