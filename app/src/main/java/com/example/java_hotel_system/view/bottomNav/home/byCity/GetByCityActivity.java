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
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view_model.GetByCityViewModel;
import com.example.java_hotel_system.view_model.HomeViewModel;

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
        initRecyclerViewGetByCityRoom();
        getByCityRoom(city);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            }
        });
    }

    private void initRecyclerViewGetByCityRoom() {
        recyclerViewAdapterGetByCityRoom = new RecyclerViewHorizontal();
        rvByCity = findViewById(R.id.rvByCity);

        RecyclerView.LayoutManager mLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getApplicationContext(), 2);
        rvByCity.setLayoutManager(mLayoutManager);
        recyclerViewAdapterGetByCityRoom = new RecyclerViewHorizontal();
        rvByCity.setAdapter(recyclerViewAdapterGetByCityRoom);
    }

    private void getByCityRoom(String city) {
        GetByCityViewModel viewModel = new ViewModelProvider(this).get(GetByCityViewModel.class);
        viewModel.getByCityKamarObservable().observe(GetByCityActivity.this, new Observer<List<Kamar>>() {
            @Override
            public void onChanged(List<Kamar> recyclerData) {
                if (recyclerData != null) {
                    recyclerViewAdapterGetByCityRoom.setListDataItems(recyclerData);
                    recyclerViewAdapterGetByCityRoom.notifyDataSetChanged();

                    clEmptyRoom.setVisibility(View.GONE);
                    rvByCity.setVisibility(View.VISIBLE);
                } else {
                    clEmptyRoom.setVisibility(View.VISIBLE);
                    rvByCity.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getByCityKamarOfData(city);
    }
}