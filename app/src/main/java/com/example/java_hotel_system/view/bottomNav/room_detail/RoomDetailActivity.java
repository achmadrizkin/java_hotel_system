package com.example.java_hotel_system.view.bottomNav.room_detail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.dao.DaoBooking;
import com.example.java_hotel_system.dao.DaoKamar;
import com.example.java_hotel_system.model.booking.Booking;
import com.example.java_hotel_system.view.bottomNav.BottomNavigationActivity;
import com.example.java_hotel_system.view.bottomNav.profile.add_room.AddRoomActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.edit.EditRoomActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.payment.PaymentSuccessActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RoomDetailActivity extends AppCompatActivity {
    ImageView ivPhotoRoom, ivBack, ivEdit, ivDelete;
    TextView tvName, tvRating, tvCity, tvDeskripsi, tvBed, tvRoom, tvNotLogin, tvHarga, tvURL, tvHari;
    Button btnMaps, btnBooking;
    EditText etCheckIn, etCheckOut;
    DaoBooking dao;
    DaoKamar daoKamar;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    DateTimeFormatter dtf;
    private String checkIn = "", checkOut = "", daysBetween;
    String daysString = "a";

    //
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        //
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

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

        ivPhotoRoom = findViewById(R.id.ivPhotoRoom);
        tvURL = findViewById(R.id.tvURL);
        tvName = findViewById(R.id.textView17);
        tvRating = findViewById(R.id.textView18);
        tvCity = findViewById(R.id.textView16);
        tvDeskripsi = findViewById(R.id.textView23);
        tvBed = findViewById(R.id.tvBed);
        tvRoom = findViewById(R.id.tvRoom);
        btnMaps = findViewById(R.id.btnMaps);
        ivBack = findViewById(R.id.ivBack);
        ivEdit = findViewById(R.id.ivEdit);
        btnBooking = findViewById(R.id.btnBooking);
        etCheckIn = findViewById(R.id.editTextDate2);
        etCheckOut = findViewById(R.id.editTextDate3);
        tvNotLogin = findViewById(R.id.tvNotLogin);
        tvHarga = findViewById(R.id.tvHarga);
        ivDelete = findViewById(R.id.ivDelete);
        tvHari = findViewById(R.id.tvHari);

        mAuth = FirebaseAuth.getInstance();

        //
        dao = new DaoBooking();
        daoKamar = new DaoKamar();
        dtf = DateTimeFormatter.ofPattern("dd MM yyyy");

        // calendar
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar cal = Calendar.getInstance();

        etCheckIn.setText(dateFormat.format(cal.getTime()));
        checkIn = dateFormat.format(cal.getTime());

        etCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RoomDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String monthConvertedCheckIn = ""+month;
                        if (month < 10) {
                            monthConvertedCheckIn = "0"+month;
                        }

                        String date = day + "/" + monthConvertedCheckIn + "/" + year;

                        //
                        checkIn = day + " " + monthConvertedCheckIn + " "+ year;

                        etCheckIn.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        etCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RoomDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String monthConvertedCheckIn = ""+month;
                        if (month < 10) {
                            monthConvertedCheckIn = "0"+month;
                        }

                        String date = day + "/" + monthConvertedCheckIn + "/" + year;

                        //
                        checkOut = day + " " + monthConvertedCheckIn + " "+ year;

                        etCheckOut.setText(date);

                        //
                        try {
                            final LocalDate firstDate = LocalDate.parse(checkIn, dtf);
                            final LocalDate secondDate = LocalDate.parse(checkOut, dtf);
                            final long days = ChronoUnit.DAYS.between(firstDate, secondDate);

                            //
                            daysString = Long.toString(days);
                            tvHari.setText(daysString);

                            System.out.println("Days between: " + days);

                        } catch (ParseException e) {

                        }

                        int hargaInt = Integer.parseInt(harga);
                        int daysInt = Integer.parseInt(daysString);
                        int totalHarga = daysInt * hargaInt;
                        tvHarga.setText(String.format("%d",totalHarga));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        if (mAuth.getCurrentUser() == null) {
            ivEdit.setVisibility(View.GONE);
            ivDelete.setVisibility(View.GONE);
        }


        // SET
        tvName.setText(name);
        tvHarga.setText(harga);
        tvBed.setText(jmlh_kasur);
        tvRoom.setText(jmlh_ruangan);
        tvRating.setText("☆ " + rating);
        tvCity.setText(kota);


        tvDeskripsi.setText(deskripsi);
        Glide.with(this).load(image_url).placeholder(R.drawable.erorr_picture).dontAnimate().into(ivPhotoRoom);
        //
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OPEN GOOGLE MAPS SPESIFIC LOCATION
                // CONTOH: -6.193125, 106.821810
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditRoomActivity.class);
                i.putExtra("key", key);
                i.putExtra("name", name);
                i.putExtra("kd_kamar", kd_kamar);
                i.putExtra("image_url", image_url);
                i.putExtra("rating", rating);
                i.putExtra("kota", kota);
                i.putExtra("deskripsi", deskripsi);
                i.putExtra("jmlh_ruangan", jmlh_ruangan);
                i.putExtra("jmlh_kasur", jmlh_kasur);
                i.putExtra("location", location);
                i.putExtra("harga", harga);
                startActivity(i);
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DELETE
                removeKamar(key);
            }
        });

        if (mAuth.getCurrentUser() != null) {
            btnBooking.setVisibility(View.VISIBLE);
        } else {
            btnBooking.setVisibility(View.GONE);
            tvNotLogin.setVisibility(View.VISIBLE);
        }

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String checkIn = etCheckIn.getText().toString();
                String checkOut = etCheckOut.getText().toString();

                if (checkIn.isEmpty() || checkOut.isEmpty()) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Fill empty Check In and Check Out",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    UUID uuid = UUID.randomUUID();

                    // BOOKING
                    Booking booking = new Booking(uuid.toString(), tvHarga.getText().toString(), mAuth.getCurrentUser().getUid(), rating, name, kota, etCheckIn.getText().toString(), etCheckOut.getText().toString(), location, image_url, currentDate);
                    dao.addBooking(booking).addOnSuccessListener(suc -> {
                        Toast.makeText(RoomDetailActivity.this, "Booking Berhasil", Toast.LENGTH_LONG).show();

                        // PASS INTENT
                        Intent i = new Intent(view.getContext(), PaymentSuccessActivity.class);
                        startActivity(i);
                        finish();

                        //DELETE DATA AFTER BOOKING
                        daoKamar.remove(key);
                    }).addOnFailureListener(er -> {
                        Toast.makeText(RoomDetailActivity.this, "Booking ERROR: " + er.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private void removeKamar(String key) {
        DaoKamar dao = new DaoKamar();
        dao.remove(key).addOnSuccessListener(suc -> {
            Toast.makeText(RoomDetailActivity.this, "Delete Data Success", Toast.LENGTH_LONG).show();
            finish();
        }).addOnFailureListener(er -> {
            Toast.makeText(RoomDetailActivity.this, "Delete Data Fail", Toast.LENGTH_LONG).show();
        });
    }

}