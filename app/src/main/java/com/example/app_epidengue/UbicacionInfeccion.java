package com.example.app_epidengue;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionInfeccion extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    EditText texLatitud, txtLongitud;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubicacion_infeccion);

        texLatitud=findViewById(R.id.idlatitud);
        txtLongitud=findViewById(R.id.idlongitud);

        SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng peru= new LatLng(-8.3921746,-74.5567636);
        mMap.addMarker(new MarkerOptions().position(peru).title("pucallpa"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(peru));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        texLatitud.setText("" + latLng.latitude);
        txtLongitud.setText("" + latLng.longitude);

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        texLatitud.setText(""+latLng.latitude);
        txtLongitud.setText(""+latLng.longitude);

    }
}