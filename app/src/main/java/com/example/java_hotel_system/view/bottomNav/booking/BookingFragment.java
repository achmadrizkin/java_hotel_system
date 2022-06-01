package com.example.java_hotel_system.view.bottomNav.booking;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class BookingFragment extends Fragment {
    private ConstraintLayout clLogin, clNotLogin;
    private Button btnToLogin;
    private FirebaseAuth mAuth;

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

        //
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

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
}