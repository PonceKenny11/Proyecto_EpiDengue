package com.example.app_epidengue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.DiagnosticoDB;
import com.example.app_epidengue.Repository.RegistrarPacientBD;
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
    EditText departamento, provincia, distrito, direccion;
    GoogleMap mMap;
    private RegistrarPacientBD pacientBD;
    private DiagnosticoDB diagnosticoDB;

    private static final String TAG = "UbicacionInfeccion";
    private final LatLng defaultLocation = new LatLng(-8.3791, -74.5539); // Pucallpa, Perú
    private UbicacionDB ubicacionDB;
    private LatLng lastSelectedLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubicacion);
        departamento = findViewById(R.id.idDepa);
        provincia = findViewById(R.id.idProvin);
        distrito = findViewById(R.id.idDistri);
        direccion = findViewById(R.id.idDirecc);
        pacientBD = new RegistrarPacientBD(this);
        diagnosticoDB = new DiagnosticoDB(this);
        ubicacionDB = new UbicacionDB(this);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       btnGuardar.setOnClickListener(v -> registraDatosLoad());
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
        lastSelectedLocation = latLng;
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación Seleccionada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        getGeocodingData(latLng);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        // Assuming you have txtLatitud and txtLongitud EditText variables defined and initialized

    }

    private void getGeocodingData(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String dep = address.getAdminArea(); // Departamento o Región
                String prov = address.getSubAdminArea(); // Provincia
                String dist = address.getLocality(); // Distrito o Ciudad
                String dir = address.getAddressLine(0); // Dirección completa

                departamento.setText(dep);
                provincia.setText(prov);
                distrito.setText(dist);
                direccion.setText(dir);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al obtener la dirección.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean captureAndSavePacient(){
        SharedPreferences prefsCheck = this.getSharedPreferences("PacienteRegistrado", Context.MODE_PRIVATE);
        boolean isRegistered1 = prefsCheck.getBoolean("isRegistered1", false);
        String savedDni = prefsCheck.getString("dni", "");
        String savedNombreCompleto = prefsCheck.getString("nombreCompleto", "");
        int savedEdad = prefsCheck.getInt("edad", -1);
        String savedSexo = prefsCheck.getString("sexo", "");
        String savedTelefono = prefsCheck.getString("telefono", "");

        Log.d(TAG, "Datos del paciente: DNI=" + savedDni + ", NombreCompleto=" + savedNombreCompleto + ", Edad=" + savedEdad + ", Sexo=" + savedSexo + ", Telefono=" + savedTelefono);
        return pacientBD.insertPaciente(savedDni, savedNombreCompleto, savedEdad, savedSexo, savedTelefono) && isRegistered1;
    }

    private boolean captureAndSaveDiagnost(){
        SharedPreferences prefsCheck = this.getSharedPreferences("DiagnosticoRegistrado", Context.MODE_PRIVATE);
        boolean isRegistered2 = prefsCheck.getBoolean("isRegistered2", false);
        String savedNombreDiagnostico = prefsCheck.getString("nombreDiagnostico", "");
        String savedTipoDiagnostico = prefsCheck.getString("tipoDiagnostico", "");
        float savedFiebre = prefsCheck.getFloat("fiebre", -1);
        String savedSintomas = prefsCheck.getString("sintomas", "");

        Log.d(TAG, "Datos del diagnóstico: NombreDiagnostico=" + savedNombreDiagnostico + ", TipoDiagnostico=" + savedTipoDiagnostico + ", Fiebre=" + savedFiebre + ", Sintomas=" + savedSintomas);
        return diagnosticoDB.insertDiagnostico(savedNombreDiagnostico, savedTipoDiagnostico, savedFiebre, savedSintomas) && isRegistered2;
    }

    private boolean guardarUbicacion() {
        if (lastSelectedLocation != null) {
            String dep = departamento.getText().toString();
            String prov = provincia.getText().toString();
            String dist = distrito.getText().toString();
            String dir = direccion.getText().toString();

            if (ubicacionDB.insertLugarInfeccion(dir, dep, prov, dist, lastSelectedLocation.latitude, lastSelectedLocation.longitude)) {
                Toast.makeText(this, "Insertado correctamente", Toast.LENGTH_LONG).show();
                return true;
            } else {
                Toast.makeText(this, "No insertado", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(this, "No se ha seleccionado ninguna ubicación.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void registraDatosLoad() {
        boolean isLugarInfeccionIngresado = guardarUbicacion();
        boolean isDiagnosticoGuardado = captureAndSaveDiagnost();
        boolean isPacienteGuardado = captureAndSavePacient();

        if (isLugarInfeccionIngresado && isDiagnosticoGuardado && isPacienteGuardado) {
            Toast.makeText(this, "Ficha de paciente registrada exitosamente", Toast.LENGTH_SHORT).show();
            Intent instanciar3 = new Intent(this, FichaDengue.class);
            startActivity(instanciar3);
            finish();
        } else {
            Toast.makeText(this, "Error al registrar la ficha de paciente", Toast.LENGTH_SHORT).show();
        }
    }

}