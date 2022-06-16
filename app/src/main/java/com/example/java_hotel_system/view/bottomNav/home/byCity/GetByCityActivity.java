package com.example.java_hotel_system.view.bottomNav.home.byCity;

import androidx.annotation.NonNull;
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
import com.example.java_hotel_system.dao.DaoKamar;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GetByCityActivity extends AppCompatActivity {
    private RecyclerViewHorizontal recyclerViewAdapterGetByCityRoom;
    private ImageView ivBack;
    private ConstraintLayout clEmptyRoom;
    RecyclerView rvByCity;

    //
    DaoKamar dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_by_city);

        Intent intent = getIntent();
        String city = intent.getExtras().getString("city");

        ivBack = findViewById(R.id.ivBack);
        clEmptyRoom = findViewById(R.id.clEmptyRoom);
        rvByCity = findViewById(R.id.rvByCity);

        dao = new DaoKamar();

        initRecyclerViewGetByCityRoom();
        getKotaRoom(city);

        //
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            }
        });
    }

    private void initRecyclerViewGetByCityRoom() {
        recyclerViewAdapterGetByCityRoom = new RecyclerViewHorizontal(this);
        rvByCity = findViewById(R.id.rvByCity);

        RecyclerView.LayoutManager mLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getApplicationContext(), 2);
        rvByCity.setLayoutManager(mLayoutManager);
        recyclerViewAdapterGetByCityRoom = new RecyclerViewHorizontal(this);
        rvByCity.setAdapter(recyclerViewAdapterGetByCityRoom);
    }

    private void getKotaRoom(String kotaKamar) {
        dao.getByKota(kotaKamar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Kamar> kamar = new ArrayList<>();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Kamar kmr = data.getValue(Kamar.class);
                    kmr.setKey(data.getKey());
                    kamar.add(kmr);
                }

                if (kamar.isEmpty() || kamar.get(0).getNama().isEmpty()) {
                    noData();
                } else {
                    data();
                }

                recyclerViewAdapterGetByCityRoom.setListDataItems(kamar);
                recyclerViewAdapterGetByCityRoom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // NULL DATA
            }
        });
    }

    private void noData() {
        clEmptyRoom.setVisibility(View.VISIBLE);
    }

    private void data() {
        clEmptyRoom.setVisibility(View.GONE);
    }
}