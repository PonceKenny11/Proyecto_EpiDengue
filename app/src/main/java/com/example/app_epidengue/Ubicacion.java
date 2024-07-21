package com.example.app_epidengue;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.UbicacionDB;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Ubicacion extends AppCompatActivity  implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    EditText txtLatitud, txtLongitud;
    GoogleMap mMap;
    private final LatLng defaultLocation = new LatLng(-8.3791, -74.5539); // Pucallpa, Perú
    private UbicacionDB repository = new UbicacionDB(this);

    private LatLng lastSelectedLocation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubicacion);
        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);
        System.out.println("estoy aqui");
        LatLng defaultLocation = new LatLng(-8.3791, -74.5539); // Pucallpa, Perú
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Pucallpa, Perú"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        txtLatitud.setText(String.valueOf(latLng.latitude));
        txtLongitud.setText(String.valueOf(latLng.longitude));

        lastSelectedLocation = latLng;
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación Seleccionada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        getGeocodingData(latLng);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        txtLatitud.setText(String.valueOf(latLng.latitude));
        txtLongitud.setText(String.valueOf(latLng.longitude));


    }
    private void getGeocodingData(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String departamento = address.getAdminArea(); // Departamento o Región
                String provincia = address.getSubAdminArea(); // Provincia
                String distrito = address.getLocality(); // Distrito o Ciudad
                String direccion = address.getAddressLine(0); // Dirección completa
               if( repository.insertLugarInfeccion(direccion,departamento,provincia,distrito,latLng.latitude,latLng.longitude))
               {
                   Toast.makeText(this,"insertado correctamente",Toast.LENGTH_LONG).show();
               }else {
                   Toast.makeText(this,"no insertado",Toast.LENGTH_LONG).show();

               }
               // Muestra los datos recuperados
                Toast.makeText(this, "Departamento: " + departamento + "\nProvincia: " + provincia +
                        "\nDistrito: " + distrito + "\nDirección: " + direccion, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al obtener la dirección.", Toast.LENGTH_SHORT).show();
        }
    }
}