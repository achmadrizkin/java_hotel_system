package com.example.java_hotel_system.view.bottomNav.home;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewHorizontal;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view_model.HomeViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private RecyclerViewHorizontal recyclerViewAdapterAllRoom, recyclerViewAdapterTrendingRoom;
    ConstraintLayout clHome;
    EditText inputBookName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        inputBookName = view.findViewById(R.id.inputBookName);
        clHome = view.findViewById(R.id.clHome);

        // get all room
        initRecyclerViewAllRoom(view);
        getAllHotel();

        // get trending room
        initRecyclerViewTrendingRoom(view);
        getTrendingHotel();

        return view;
    }

    private void initRecyclerViewAllRoom(View view) {
        recyclerViewAdapterAllRoom = new RecyclerViewHorizontal();
        RecyclerView rvAllRoom = view.findViewById(R.id.rvAllRoom);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvAllRoom.setLayoutManager(horizontalLayoutManager);
        rvAllRoom.setAdapter(recyclerViewAdapterAllRoom);
    }

    private void initRecyclerViewTrendingRoom(View view) {
        recyclerViewAdapterTrendingRoom = new RecyclerViewHorizontal();
        RecyclerView rvTrendingRoom = view.findViewById(R.id.rvTrendingRoom);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTrendingRoom.setLayoutManager(horizontalLayoutManager);
        rvTrendingRoom.setAdapter(recyclerViewAdapterTrendingRoom);
    }

    private void getAllHotel() {
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getALlHotelObservable().observe(getActivity(), new Observer<List<Kamar>>() {
            @Override
            public void onChanged(List<Kamar> recyclerData) {
                if (recyclerData != null) {
                    recyclerViewAdapterAllRoom.setListDataItems(recyclerData);
                    recyclerViewAdapterAllRoom.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Error in getting data", Toast.LENGTH_LONG).show();
                }
            }
        });
        viewModel.getALlHotelOfData();
    }

    private void getTrendingHotel() {
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getTrendingHotelObservable().observe(getActivity(), new Observer<List<Kamar>>() {
            @Override
            public void onChanged(List<Kamar> recyclerData) {
                if (recyclerData != null) {
                    recyclerViewAdapterTrendingRoom.setListDataItems(recyclerData);
                    recyclerViewAdapterTrendingRoom.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Error in getting data", Toast.LENGTH_LONG).show();
                }
            }
        });
        viewModel.getTrendingHotelOfData();
    }
}