package com.example.app_epidengue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.DiagnosticoDB;
import com.example.app_epidengue.Repository.RegistrarPacientBD;
import com.example.app_epidengue.Repository.UbicacionDB;

public class UbicacionInfeccion extends AppCompatActivity {



    private EditText  txtDireccion, txtDepartamento, txtProvincia, txtDistrito, txtLatitud, txtLongitud;

    private RegistrarPacientBD pacientBD;
    private DiagnosticoDB diagnosticoDB;

    private UbicacionDB ubicacionDB;
    private static final String TAG = "UbicacionInfeccion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubicacion_infeccion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btningresar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pacientBD = new RegistrarPacientBD(this);
        diagnosticoDB = new DiagnosticoDB(this);
        ubicacionDB = new UbicacionDB(this);

        txtDepartamento = findViewById(R.id.iddepartamento);
        txtDistrito = findViewById(R.id.iddistrito);
        txtProvincia = findViewById(R.id.idprovincia);
        txtDireccion = findViewById(R.id.iddireccion);
        txtLatitud = findViewById(R.id.idlatitud);
        txtLongitud = findViewById(R.id.idlongitud);

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
    /////////////////////////////////////////////

}