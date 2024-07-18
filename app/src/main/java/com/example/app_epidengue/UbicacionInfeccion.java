package com.example.app_epidengue;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

    private EditText  txtDireccion, txtDepartamento, txtProvincia, txtDistrito, txtLatitud, txtLongitud;

    private RegistrarPacientBD pacientBD;
    private DiagnosticoDB diagnosticoDB;

    private UbicacionDB ubicacionDB;
    private static final String TAG = "UbicacionInfeccion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    private boolean captureAndSavePacient() {
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

    private boolean captureAndSaveDiagnost() {
        SharedPreferences prefsCheck = this.getSharedPreferences("DiagnosticoRegistrado", Context.MODE_PRIVATE);
        boolean isRegistered2 = prefsCheck.getBoolean("isRegistered2", false);
        String savedNombreDiagnostico = prefsCheck.getString("nombreDiagnostico", "");
        String savedTipoDiagnostico = prefsCheck.getString("tipoDiagnostico", "");
        float savedFiebre = prefsCheck.getFloat("fiebre", -1);
        String savedSintomas = prefsCheck.getString("sintomas", "");

        Log.d(TAG, "Datos del diagnóstico: NombreDiagnostico=" + savedNombreDiagnostico + ", TipoDiagnostico=" + savedTipoDiagnostico + ", Fiebre=" + savedFiebre + ", Sintomas=" + savedSintomas);
        return diagnosticoDB.insertDiagnostico(savedNombreDiagnostico, savedTipoDiagnostico, savedFiebre, savedSintomas) && isRegistered2;
    }

    private boolean ingresadoLugarInfeccion() {
        String direccion = txtDireccion.getText().toString().trim();
        String departamento = txtDepartamento.getText().toString().trim();
        String provincia = txtProvincia.getText().toString().trim();
        String distrito = txtDistrito.getText().toString().trim();
        String latitudStr = txtLatitud.getText().toString().trim();
        String longitudStr = txtLongitud.getText().toString().trim();

        if (TextUtils.isEmpty(direccion) || TextUtils.isEmpty(departamento) || TextUtils.isEmpty(provincia)
                || TextUtils.isEmpty(distrito) || TextUtils.isEmpty(latitudStr) || TextUtils.isEmpty(longitudStr)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        double latitud, longitud;
        try {
            latitud = Double.parseDouble(latitudStr);
            longitud = Double.parseDouble(longitudStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese valores válidos para latitud y longitud", Toast.LENGTH_SHORT).show();
            return false;
        }

        return ubicacionDB.insertLugarInfeccion(direccion, departamento, provincia, distrito, latitud, longitud);
    }

    public void retrocederPestana(View view) {
        Intent instanciar2 = new Intent(this, DiagnosticoPaciente.class);
        startActivity(instanciar2);
        finish();
    }

    public void registraDatosLoad(View view) {
        boolean isLugarInfeccionIngresado = ingresadoLugarInfeccion();
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