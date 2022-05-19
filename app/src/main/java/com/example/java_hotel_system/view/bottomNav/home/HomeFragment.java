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
    private RecyclerViewHorizontal recyclerViewAdapter;
    ConstraintLayout clHome;
    EditText inputBookName;
    RecyclerView rcyclerHome;

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
        rcyclerHome = view.findViewById(R.id.rcyclerHome);

        initRecyclerView(view);
        initSearchBook();

        return view;
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcyclerHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewHorizontal();
        recyclerView.setAdapter(recyclerViewAdapter);
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
        viewModel.getSearchKamarObservable().observe(getActivity(), new Observer<List<Kamar>>() {
            @Override
            public void onChanged(List<Kamar> recyclerData) {
                if (recyclerData != null) {
                    // berhasil
                    recyclerViewAdapter.setListDataItems(recyclerData);
                    recyclerViewAdapter.notifyDataSetChanged();

                    clHome.setVisibility(View.GONE);
                    rcyclerHome.setVisibility(View.VISIBLE);
                } else {
                    clHome.setVisibility(View.VISIBLE);
                    rcyclerHome.setVisibility(View.GONE);
                    // if not search
                }

                if (nama.isEmpty() || nama == "") {
                    clHome.setVisibility(View.VISIBLE);
                    rcyclerHome.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getSearchKamar(nama);
    }
}