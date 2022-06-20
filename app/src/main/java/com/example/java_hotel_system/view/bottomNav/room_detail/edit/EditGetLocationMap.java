package com.example.java_hotel_system.view.bottomNav.room_detail.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.view.bottomNav.profile.add_room.AddRoomActivity;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class EditGetLocationMap extends AppCompatActivity implements MapboxMap.OnMapClickListener, OnMapReadyCallback {
    private MapView mapView;
    private MapboxMap map;
    private TextView tvLatLng;
    private Point originPositon;
    private Point destinationPosition;
    private Marker destinationMarker;
    private Button btnNext;
    private Double lat, lng;
    String location2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_edit_get_location_map);

        mapView = (MapView) findViewById(R.id.mapView);
        btnNext = (Button) findViewById(R.id.btnNext);
        tvLatLng = findViewById(R.id.tvLatLong);

        //
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

        // error klo jalan di emulator
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location2 = lat + "," + lng;

                Intent i = new Intent(view.getContext(), EditRoomActivity.class);
                i.putExtra("location2", location2);
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

//                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        if (destinationMarker != null) {
            map.removeMarker(destinationMarker);
        }

        destinationMarker = map.addMarker(new MarkerOptions().position(point));

        destinationPosition = Point.fromLngLat(point.getLongitude(), point.getLatitude());

        // get POINT
        lng = point.getLongitude();
        lat = point.getLatitude();

        tvLatLng.setText("Long: " + lng + " Lat: " + lat);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.addOnMapClickListener(this);
        mapboxMap.setStyle(Style.OUTDOORS);
    }
}