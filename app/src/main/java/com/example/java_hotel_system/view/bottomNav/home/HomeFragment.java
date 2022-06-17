package com.example.java_hotel_system.view.bottomNav.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.java_hotel_system.dao.DaoKamar;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.bottomNav.home.byCity.GetByCityActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    String query = "";

    DaoKamar dao;

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
        dao = new DaoKamar();

        initSearchBook();

        initRecyclerViewAllRoom(view);
        initRecyclerViewSearchRoom(view);
        initRecyclerViewTrendingRoom(view);

        getAllRoom();
        getKotaRoom();

        civJogja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "jogja";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civBandung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "bandung";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civJakarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "jakarta";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civBali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "bali";
                Intent i = new Intent(getActivity(), GetByCityActivity.class);
                i.putExtra("city", city);
                startActivity(i);
            }
        });

        civSurabaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = "surabaya";
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
                query = charSequence.toString();
                getSearchRoom(charSequence.toString());

                if (query.isEmpty()) {
                    clHome.setVisibility(View.VISIBLE);
                    rcyclerHome.setVisibility(View.GONE);
                } else {
                    clHome.setVisibility(View.GONE);
                    rcyclerHome.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initRecyclerViewAllRoom(View view) {
        recyclerViewAdapterAllRoom = new RecyclerViewHorizontal(getActivity());
        RecyclerView rvAllRoom = view.findViewById(R.id.rvAllRoom);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvAllRoom.setLayoutManager(horizontalLayoutManager);
        rvAllRoom.setAdapter(recyclerViewAdapterAllRoom);
    }

    private void initRecyclerViewSearchRoom(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcyclerHome);
        RecyclerView.LayoutManager mLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerViewAdapterSearchRoom = new RecyclerViewHorizontal(getActivity());
        recyclerView.setAdapter(recyclerViewAdapterSearchRoom);
    }

    private void initRecyclerViewTrendingRoom(View view) {
        recyclerViewAdapterTrendingRoom = new RecyclerViewHorizontal(getActivity());
        RecyclerView rvTrendingRoom = view.findViewById(R.id.rvTrendingRoom);

        //
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTrendingRoom.setLayoutManager(horizontalLayoutManager);
        rvTrendingRoom.setAdapter(recyclerViewAdapterTrendingRoom);
    }

    private void getAllRoom() {
        dao.getAll().addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Kamar> kamar = new ArrayList<>();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Kamar kmr = data.getValue(Kamar.class);
                    kmr.setKey(data.getKey());
                    kamar.add(kmr);
                }

                recyclerViewAdapterAllRoom.setListDataItems(kamar);
                recyclerViewAdapterAllRoom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // NULL DATA
            }
        });
    }

    private void getKotaRoom() {
        dao.getByHarga().addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Kamar> kamar = new ArrayList<>();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Kamar kmr = data.getValue(Kamar.class);
                    kmr.setKey(data.getKey());
                    kamar.add(kmr);
                }

                recyclerViewAdapterTrendingRoom.setListDataItems(kamar);
                recyclerViewAdapterTrendingRoom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // NULL DATA
            }
        });
    }

    private void getSearchRoom(String kamar) {
        dao.searchKamar(kamar).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Kamar> kamar = new ArrayList<>();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Kamar kmr = data.getValue(Kamar.class);
                    kmr.setKey(data.getKey());
                    kamar.add(kmr);
                }

                // check if data was null (empty)
                if (kamar.isEmpty() || kamar.get(0).getNama().isEmpty()) {
                    noData();
                } else {
                    data();
                }

                recyclerViewAdapterSearchRoom.setListDataItems(kamar);
                recyclerViewAdapterSearchRoom.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void noData() {
        ivNoResult.setVisibility(View.VISIBLE);
        tvNoResult.setVisibility(View.VISIBLE);
    }

    private void data() {
        ivNoResult.setVisibility(View.GONE);
        tvNoResult.setVisibility(View.GONE);
    }
}