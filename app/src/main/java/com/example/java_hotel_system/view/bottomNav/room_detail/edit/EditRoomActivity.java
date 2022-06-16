package com.example.java_hotel_system.view.bottomNav.room_detail.edit;

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
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.RoomDetailActivity;

import java.util.HashMap;

public class EditRoomActivity extends AppCompatActivity {
    ImageView ivBack;
    private EditText etNamaKamar, etUrlGambar, etKota, etLokasi, etHarga, etDeskripsi, etJmlhKasur, etJmlhRuangan, etRating;
    private Button btnInsertData;
    DaoKamar dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        Intent intent = getIntent();
        String key = intent.getExtras().getString("key");
        String name = intent.getExtras().getString("name");
        String image_url = intent.getExtras().getString("image_url");
        String rating = intent.getExtras().getString("rating");
        String kota = intent.getExtras().getString("kota");
        String deskripsi = intent.getExtras().getString("deskripsi");
        String jmlh_ruangan = intent.getExtras().getString("jmlh_ruangan");
        String jmlh_kasur = intent.getExtras().getString("jmlh_kasur");
        String location = intent.getExtras().getString("location");
        String kd_kamar = intent.getExtras().getString("kd_kamar");
        String harga = intent.getExtras().getString("harga");

        //
        dao = new DaoKamar();

        //
        etNamaKamar = findViewById(R.id.etNamaKamar);
        btnInsertData = findViewById(R.id.btnInsertData);
        etUrlGambar = findViewById(R.id.etUrlGambar);
        etKota = findViewById(R.id.etKota);
        etLokasi = findViewById(R.id.etLokasi);
        etHarga = findViewById(R.id.etHarga);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        etJmlhKasur = findViewById(R.id.etJmlhKasur);
        etRating = findViewById(R.id.etRating);
        etJmlhRuangan = findViewById(R.id.etJmlhRuangan);

        etNamaKamar.setText(name);
        etUrlGambar.setText(image_url);
        etKota.setText(kota);
        etHarga.setText(harga);
        etDeskripsi.setText(deskripsi);
        etJmlhKasur.setText(jmlh_kasur);
        etJmlhRuangan.setText(jmlh_ruangan);
        etRating.setText(rating);
        etLokasi.setText(location);

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(key);
            }
        });
    }

    private void updateData(String key) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nama", etNamaKamar.getText().toString());
        hashMap.put("image_url", etUrlGambar.getText().toString());
        hashMap.put("rating", etRating.getText().toString());
        hashMap.put("harga", etHarga.getText().toString());
        hashMap.put("lokasi", etLokasi.getText().toString());
        hashMap.put("kota", etKota.getText().toString());
        hashMap.put("deskripsi", etDeskripsi.getText().toString());
        hashMap.put("jmlh_kasur", etJmlhKasur.getText().toString());
        hashMap.put("jmlh_ruangan", etJmlhRuangan.getText().toString());

        // dao
        dao.update(key, hashMap).addOnSuccessListener(suc -> {
            Toast.makeText(this, "Update Data Success", Toast.LENGTH_LONG).show();

            //
            startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
        }).addOnFailureListener(er -> {
            Toast.makeText(this, "Update Data Failed", Toast.LENGTH_LONG).show();
        });
    }
}