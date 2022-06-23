package com.example.java_hotel_system.view.bottomNav.profile.all_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewAllUser;
import com.example.java_hotel_system.adapter.RecyclerViewHorizontal;
import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.select_role.SelectRoleActivity;
import com.example.java_hotel_system.view_model.LoginFacebookViewModel;
import com.example.java_hotel_system.view_model.UserDetailsViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AllUserActivity extends AppCompatActivity {
    private RecyclerViewAllUser recyclerViewAllUser;
    private RecyclerView rvAllUser;
    private UserDetailsViewModel viewModel;
    private ImageView ivBack;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        rvAllUser = findViewById(R.id.rvAllUser);
        ivBack = findViewById(R.id.ivBack);
        pbLoading = findViewById(R.id.pbLoading);

        pbLoading.setVisibility(View.VISIBLE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initRecyclerView();
        getAllUserFromView();
    }

    private void initRecyclerView() {
        rvAllUser = findViewById(R.id.rvAllUser);
        rvAllUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewAllUser = new RecyclerViewAllUser(this);
        rvAllUser.setAdapter(recyclerViewAllUser);
    }

    private void getAllUserFromView() {
        viewModel = new ViewModelProvider(this).get(UserDetailsViewModel.class);
        viewModel.getAllUserObservable().observe(AllUserActivity.this, new Observer<ListUser>() {
            @Override
            public void onChanged(ListUser userRequest) {
                if (userRequest != null) {
                    pbLoading.setVisibility(View.GONE);
                    recyclerViewAllUser.setListDataItems(userRequest);
                    recyclerViewAllUser.notifyDataSetChanged();
                }
            }
        });

        viewModel.getAllUserOfData();
    }
}