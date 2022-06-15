package com.example.java_hotel_system.view.bottomNav.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.view.bottomNav.profile.add_room.AddRoomActivity;
import com.example.java_hotel_system.view.bottomNav.profile.info_app.InfoAppActivity;
import com.example.java_hotel_system.view.bottomNav.profile.map.GetLocationMap;
import com.example.java_hotel_system.view.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {
    private Button btnSignOut;
    private FirebaseAuth mAuth;
    private ImageView profile_image;
    private TextView tvDisplayName, tvEmail;

    private ConstraintLayout clLogin, clNotLogin;
    private Button btnToLogin, btnInfoApp, btnAddRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        profile_image = view.findViewById(R.id.profile_image);
        tvDisplayName = view.findViewById(R.id.tvDisplayName);
        tvEmail = view.findViewById(R.id.tvEmail);

        btnToLogin = view.findViewById(R.id.btnToLogin);
        btnSignOut = view.findViewById(R.id.btnLogOut);
        btnInfoApp = view.findViewById(R.id.btnInfoApp);
        btnAddRoom = view.findViewById(R.id.btnAddRoom);

        clLogin = view.findViewById(R.id.clLogin);
        clNotLogin = view.findViewById(R.id.clNotLogin);

        if (user != null) {
            if (user.getDisplayName() != "" && user.getPhotoUrl().toString() != "") {
                tvDisplayName.setText(user.getDisplayName());
                tvEmail.setText(user.getUid());
                Glide.with(profile_image).load(user.getPhotoUrl()).into(profile_image);
            } else if (user.getDisplayName() == null || user.getDisplayName() == "") {
                tvDisplayName.setText("Anonim");
                tvEmail.setText(user.getUid());
                Glide.with(profile_image).load(user.getPhotoUrl()).into(profile_image);
            }

            clLogin.setVisibility(View.VISIBLE);
            clNotLogin.setVisibility(View.GONE);
        } else {
            clNotLogin.setVisibility(View.VISIBLE);
            clLogin.setVisibility(View.GONE);
        }


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), GetLocationMap.class));
            }
        });

        btnInfoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InfoAppActivity.class));
            }
        });

        return view;
    }
}