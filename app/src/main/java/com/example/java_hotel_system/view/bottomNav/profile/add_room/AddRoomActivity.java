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
                Kamar kmr = new Kamar(etNamaKamar.getText().toString(), etUrlGambar.getText().toString(), etRating.getText().toString(), etHarga.getText().toString(), etLokasi.getText().toString(), etDeskripsi.getText().toString(), etJmlhKasur.getText().toString(), etJmlhRuangan.getText().toString(), etKota.getText().toString(), uuid.toString());
                dao.addKamar(kmr).addOnSuccessListener(suc -> {
                    Toast.makeText(AddRoomActivity.this, "Insert Data Success", Toast.LENGTH_LONG).show();
                    finish();
                }).addOnFailureListener(er -> {
                    Toast.makeText(AddRoomActivity.this, "Insert Data ERROR: " + er.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}