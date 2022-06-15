package com.example.java_hotel_system.view.bottomNav.home.byCity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewHorizontal;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GetByCityActivity extends AppCompatActivity {
    private RecyclerViewHorizontal recyclerViewAdapterGetByCityRoom;
    private ImageView ivBack;
    private ConstraintLayout clEmptyRoom;
    RecyclerView rvByCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_by_city);

        Intent intent = getIntent();
        String city = intent.getExtras().getString("city");

        ivBack = findViewById(R.id.ivBack);
        clEmptyRoom = findViewById(R.id.clEmptyRoom);
        rvByCity = findViewById(R.id.rvByCity);


        //
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            }
        });
    }
}