package com.example.java_hotel_system.view.bottomNav.profile.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.java_hotel_system.R;
import com.example.java_hotel_system.view.bottomNav.profile.add_room.AddRoomActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.RoomDetailActivity;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class GetLocationMap extends AppCompatActivity implements MapboxMap.OnMapClickListener, OnMapReadyCallback {
    private MapView mapView;
    private MapboxMap map;
    private TextView tvLatLng;
    private Point originPositon;
    private Point destinationPosition;
    private Marker destinationMarker;
    private Button btnNext;
    private Double lat, lng;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_get_location_map);

        mapView = (MapView) findViewById(R.id.mapView);
        btnNext = (Button) findViewById(R.id.btnNext);
        tvLatLng = findViewById(R.id.tvLatLong);

        // error klo jalan di emulator
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = lat + "," + lng;

                Intent i = new Intent(view.getContext(), AddRoomActivity.class);
                i.putExtra("location", location);

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
    public void onMapClick(@NonNull LatLng point) {

        if (destinationMarker != null) {
            map.removeMarker(destinationMarker);
        }

        destinationMarker = map.addMarker(new MarkerOptions().position(point));

        destinationPosition = Point.fromLngLat(point.getLongitude(), point.getLatitude());

        // get POINT
        lng = point.getLongitude();
        lat = point.getLatitude();

        tvLatLng.setText("Long: " + lng + " Lat: " + lat);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.addOnMapClickListener(this);
    }
}