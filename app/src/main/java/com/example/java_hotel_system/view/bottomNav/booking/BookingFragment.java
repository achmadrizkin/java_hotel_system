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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewBooking;
import com.example.java_hotel_system.adapter.RecyclerViewHorizontal;
import com.example.java_hotel_system.dao.DaoBooking;
import com.example.java_hotel_system.model.booking.Booking;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BookingFragment extends Fragment {
    private ConstraintLayout clLogin, clNotLogin;
    private Button btnToLogin;
    private FirebaseAuth mAuth;
    DaoBooking dao;
    private RecyclerView rcyclerViewBooking;
    private RecyclerViewBooking recyclerViewBooking;
    private TextView textView3, textView4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        clLogin = view.findViewById(R.id.clLogin);
        clNotLogin = view.findViewById(R.id.clNotLogin);
        btnToLogin = view.findViewById(R.id.btnToLogin);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        rcyclerViewBooking = view.findViewById(R.id.rcyclerViewBooking);

        //
        dao = new DaoBooking();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //
        getBookingByUserUID(user.getUid());
        initRecyclerViewTrendingRoom(view);

        if (user != null) {
            clLogin.setVisibility(View.VISIBLE);
            clNotLogin.setVisibility(View.GONE);
        } else {
            clNotLogin.setVisibility(View.VISIBLE);
            clLogin.setVisibility(View.GONE);
        }

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
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

                if (booking.isEmpty() || booking.get(0).getKd_booking().isEmpty()) {
                    clNotLogin.setVisibility(View.VISIBLE);
                    clLogin.setVisibility(View.GONE);

                    btnToLogin.setVisibility(View.GONE);

                    textView3.setText("Booking First");
                    textView4.setText("Empty Booking, U Must Booking First");
                } else {
                    clNotLogin.setVisibility(View.GONE);
                    clLogin.setVisibility(View.VISIBLE);
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

    private void initRecyclerViewTrendingRoom(View view) {
        recyclerViewBooking = new RecyclerViewBooking(getActivity());

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcyclerViewBooking.setLayoutManager(horizontalLayoutManager);
        rcyclerViewBooking.setAdapter(recyclerViewBooking);
    }
}