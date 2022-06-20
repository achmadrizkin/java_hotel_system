package com.example.java_hotel_system.view.bottomNav.booking.booking_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.util.MCrypt;
import com.example.java_hotel_system.view.bottomNav.booking.show_qr.ShowQrActivity;

import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BookingDetailsActivity extends AppCompatActivity {

    private ImageView ivBack, ivPhotoRoom;
    private TextView tvNamaKamar, tvRating, tvKota, tvCheckIn, tvCheckOut, tvKdBooking;
    private Button btnMaps, btnShowQR, btnPrintPDF;
    String encrypted;
    Bitmap bitmap, scaleBitmap, scaleBitmapQrCode;
    private String image_url, nama_kamar, rating, lokasi, kd_booking, kota, check_in, check_out, user_buy, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        Intent intent = getIntent();
        image_url = intent.getExtras().getString("image_url");
        nama_kamar = intent.getExtras().getString("nama_kamar");
        rating = intent.getExtras().getString("rating");
        lokasi = intent.getExtras().getString("lokasi");
        kd_booking = intent.getExtras().getString("kd_booking");
        kota = intent.getExtras().getString("kota");
        check_in = intent.getExtras().getString("check_in");
        check_out = intent.getExtras().getString("check_out");
        user_buy = intent.getExtras().getString("user_buy");
        total = intent.getExtras().getString("total");

        ivBack = findViewById(R.id.ivBack);
        ivPhotoRoom = findViewById(R.id.ivPhotoRoom);
        tvNamaKamar = findViewById(R.id.tvNamaKamar);
        tvRating = findViewById(R.id.textView18);
        btnMaps = findViewById(R.id.btnMaps);
        btnShowQR = findViewById(R.id.btnShowQR);
        tvKota = findViewById(R.id.textView16);
        tvCheckIn = findViewById(R.id.tvCheckIn);
        tvCheckOut = findViewById(R.id.tvCheckOut);
        tvKdBooking = findViewById(R.id.tvKdBooking);
        btnPrintPDF = findViewById(R.id.btnPrintPDF);

        tvNamaKamar.setText(nama_kamar);
        tvKota.setText(kota);
        tvRating.setText(rating);
        tvCheckIn.setText(check_in);
        tvCheckOut.setText(check_out);
        tvKdBooking.setText(kd_booking);

        // PERMISSION
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //cover header
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hotel);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 850, false);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // encrypted
        MCrypt mcrypt = new MCrypt();
        try {
            encrypted = MCrypt.bytesToHex( mcrypt.encrypt(kd_booking));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // QR CODE MAKE QR
        Bitmap myBitmap = QRCode.from(encrypted).bitmap();
        scaleBitmapQrCode = Bitmap.createScaledBitmap(myBitmap, 500, 500, false);

        Glide.with(this).load(image_url).placeholder(R.drawable.erorr_picture).dontAnimate().into(ivPhotoRoom);

        //
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OPEN GOOGLE MAPS SPESIFIC LOCATION
                // CONTOH: -6.193125, 106.821810
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(lokasi));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnPrintPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // print pdf
                createPDF();
            }
        });

        btnShowQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ShowQrActivity.class);

                i.putExtra("kd_booking", encrypted);
                startActivity(i);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void createPDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        //
        PdfDocument.PageInfo pageInfo
                = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(scaleBitmap, 0, 0, paint);

        //
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(70);
        canvas.drawText("Your Invoice", 1800, 500, titlePaint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setTextSize(35f);

        canvas.drawText("Room Name: " + nama_kamar, 20, 740, paint);
        canvas.drawText("User Id: " + user_buy, 20, 790, paint);
        canvas.drawText("Code Booking: " + kd_booking, 20, 840, paint);
        canvas.drawText("City: " + kota, 20, 890, paint);
        canvas.drawText("Check In: " + check_in, 20, 940, paint);
        canvas.drawText("Check Out: " + check_out, 20, 990, paint);


        paint.setColor(Color.rgb(247, 147, 30));
        canvas.drawRect(680, 1350, 1200 - 20, 1450, paint);

        // QR CODE
        Canvas canvas1 = page.getCanvas();
        canvas1.drawBitmap(scaleBitmapQrCode, 700, 800, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(50f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total", 700, 1415, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(total, 1200 - 40, 1415, paint);

        //
        pdfDocument.finishPage(page);

        File file = new File(Environment.getExternalStorageDirectory(), "/Booking-" + kd_booking + ".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
        Toast.makeText(BookingDetailsActivity.this, "PDF Already Downloaded", Toast.LENGTH_LONG).show();
    }
}