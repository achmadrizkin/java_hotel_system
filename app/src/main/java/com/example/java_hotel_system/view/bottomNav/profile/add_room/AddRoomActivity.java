package com.example.java_hotel_system.view.bottomNav.profile.add_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.dao.DaoKamar;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.edit.EditRoomActivity;

import java.util.UUID;

public class AddRoomActivity extends AppCompatActivity {
    ImageView ivBack;
    private EditText etNamaKamar, etUrlGambar, etKota, etLokasi, etHarga, etDeskripsi, etJmlhKasur, etJmlhRuangan, etRating;
    private Button btnInsertData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        //
        Intent intent = getIntent();
        String location = intent.getExtras().getString("location");

        ivBack = findViewById(R.id.ivBack);
        btnInsertData = findViewById(R.id.btnInsertData);

        // DAO
        DaoKamar dao = new DaoKamar();

        //
        etNamaKamar = findViewById(R.id.etNamaKamar);
        etUrlGambar = findViewById(R.id.etUrlGambar);
        etKota = findViewById(R.id.etKota);
        etLokasi = findViewById(R.id.etLokasi);
        etHarga = findViewById(R.id.etHarga);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        etJmlhKasur = findViewById(R.id.etJmlhKasur);
        etRating = findViewById(R.id.etRating);
        etJmlhRuangan = findViewById(R.id.etJmlhRuangan);

        etLokasi.setText(location);
        etLokasi.setTextIsSelectable(true);



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID uuid = UUID.randomUUID();

                // validate first
                if (etNamaKamar.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "NAMA KAMAR Cannot NULL", Toast.LENGTH_LONG).show();
                } else if (etUrlGambar.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "LINK URL Cannot NULL: ", Toast.LENGTH_LONG).show();
                } else if (etRating.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "Rating Cannot NULL: ", Toast.LENGTH_LONG).show();
                } else if (etHarga.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "Harga Cannot NULL: ", Toast.LENGTH_LONG).show();
                } else if (etDeskripsi.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "Deskripsi Cannot NULL: ", Toast.LENGTH_LONG).show();
                } else if (etJmlhKasur.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "Jmlh Kasur Cannot NULL: ", Toast.LENGTH_LONG).show();
                } else if (etJmlhRuangan.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "Jmlh Ruangan Cannot NULL: ", Toast.LENGTH_LONG).show();
                } else if (etKota.getText().toString().isEmpty()) {
                    Toast.makeText(AddRoomActivity.this, "Kota Cannot NULL: ", Toast.LENGTH_LONG).show();

                } else {
                    Kamar kmr = new Kamar(etNamaKamar.getText().toString().toLowerCase(), etUrlGambar.getText().toString(), etRating.getText().toString(), etHarga.getText().toString(), etLokasi.getText().toString(), etDeskripsi.getText().toString(), etJmlhKasur.getText().toString(), etJmlhRuangan.getText().toString(), etKota.getText().toString().toLowerCase(), uuid.toString());
                    dao.addKamar(kmr).addOnSuccessListener(suc -> {
                        Toast.makeText(AddRoomActivity.this, "Insert Data Success", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(view.getContext(), BottomNavigationActivity.class);
                        startActivity(i);
                    }).addOnFailureListener(er -> {
                        Toast.makeText(AddRoomActivity.this, "Insert Data ERROR: " + er.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }
}