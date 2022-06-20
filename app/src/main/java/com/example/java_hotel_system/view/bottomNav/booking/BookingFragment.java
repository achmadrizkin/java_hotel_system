package com.example.java_hotel_system.view.bottomNav.booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewBooking;
import com.example.java_hotel_system.adapter.RecyclerViewHorizontal;
import com.example.java_hotel_system.dao.DaoBooking;
import com.example.java_hotel_system.model.booking.Booking;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.bottomNav.booking.booking_details.BookingDetailsActivity;
import com.example.java_hotel_system.view.bottomNav.booking.booking_filter.BookingFilter;
import com.example.java_hotel_system.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class BookingFragment extends Fragment {
    private ConstraintLayout clLogin, clNotLogin;
    private Button btnToLogin, btnPickDate;
    private FirebaseAuth mAuth;
    private TextView textView3, textView4;
    private CalendarView calendarView;
    private String date = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        clLogin = view.findViewById(R.id.clLogin);
        clNotLogin = view.findViewById(R.id.clNotLogin);
        btnToLogin = view.findViewById(R.id.btnToLogin);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        calendarView = view.findViewById(R.id.calendarView);
        btnPickDate = view.findViewById(R.id.btnPickDate);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            clLogin.setVisibility(View.VISIBLE);
            clNotLogin.setVisibility(View.GONE);
        } else {
            clNotLogin.setVisibility(View.VISIBLE);
            clLogin.setVisibility(View.GONE);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if (i1 > 10) {
                    date = i2 + "-" + (i1 + 1) + "-" + i;
                } else {
                    date = i2 + "-0" + (i1 + 1) + "-" + i;
                }
//                tvDate.setText(date);
            }
        });

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date == "") {
                    Intent i = new Intent(view.getContext(), BookingFilter.class);
                    i.putExtra("current_date", currentDate);
                    startActivity(i);
                } else {
                    Intent i = new Intent(view.getContext(), BookingFilter.class);
                    i.putExtra("current_date", date);
                    startActivity(i);
                }
            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
    }
}