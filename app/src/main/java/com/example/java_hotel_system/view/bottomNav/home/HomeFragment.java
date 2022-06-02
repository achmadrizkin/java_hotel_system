package com.example.java_hotel_system.view.bottomNav.home;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.adapter.RecyclerViewHorizontal;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.bottomNav.home.byCity.GetByCityActivity;
import com.example.java_hotel_system.view_model.HomeViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import de.hdodenhof.circleimageview.CircleImageView;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private RecyclerViewHorizontal recyclerViewAdapterAllRoom, recyclerViewAdapterTrendingRoom, recyclerViewAdapterSearchRoom;
    ConstraintLayout clHome;
    RecyclerView rcyclerHome;
    EditText inputBookName;

    //
    private ImageView ivNoResult;
    private TextView tvNoResult;

    private CircleImageView civJogja, civBandung, civJakarta, civSurabaya, civBali;

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
        rcyclerHome = view.findViewById(R.id.rcyclerHome);
        clHome = view.findViewById(R.id.clHome);

        ivNoResult = view.findViewById(R.id.ivNoResult);
        tvNoResult = view.findViewById(R.id.tvNoResult);

        // FILTER BY CITY
        civJogja = view.findViewById(R.id.profile_image6);
        civBandung = view.findViewById(R.id.profile_image5);
        civJakarta = view.findViewById(R.id.profile_image2);
        civSurabaya = view.findViewById(R.id.profile_image3);
        civBali = view.findViewById(R.id.profile_image4);

        //
        initSearchBook();
        initRecyclerViewSearchRoom(view);

        // get all room
        initRecyclerViewAllRoom(view);
        getAllHotel();

        // get trending room
        initRecyclerViewTrendingRoom(view);
        getTrendingHotel();

        civJogja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "Jogja";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civBandung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "Bandung";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civJakarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "Jakarta";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civBali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "Bali";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civSurabaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "Surabaya";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        return view;
    }

    private void initSearchBook() {
        inputBookName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                makeApiCall(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void makeApiCall(String nama) {
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.getSearchHotelObservable().observe(getActivity(), new Observer<List<Kamar>>() {
            @Override
            public void onChanged(List<Kamar> recyclerData) {
                if (recyclerData != null) {
                    // berhasil
                    recyclerViewAdapterSearchRoom.setListDataItems(recyclerData);
                    recyclerViewAdapterSearchRoom.notifyDataSetChanged();

                    clHome.setVisibility(View.GONE);
                    rcyclerHome.setVisibility(View.VISIBLE);

                    ivNoResult.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.GONE);
                } else {
                    clHome.setVisibility(View.GONE);
                    rcyclerHome.setVisibility(View.GONE);

                    // IF DATA NULL
                    ivNoResult.setVisibility(View.VISIBLE);
                    tvNoResult.setVisibility(View.VISIBLE);
                }

                if (nama.isEmpty() || nama == "") {
                    clHome.setVisibility(View.VISIBLE);
                    rcyclerHome.setVisibility(View.GONE);

                    ivNoResult.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getSearchHotelOfData(nama);
    }

    private void initRecyclerViewSearchRoom(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcyclerHome);
        RecyclerView.LayoutManager mLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerViewAdapterSearchRoom = new RecyclerViewHorizontal();
        recyclerView.setAdapter(recyclerViewAdapterSearchRoom);
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