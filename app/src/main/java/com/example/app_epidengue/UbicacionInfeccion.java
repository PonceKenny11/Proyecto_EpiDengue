package com.example.app_epidengue;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.DiagnosticoDB;
import com.example.app_epidengue.Repository.RegistrarPacientBD;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UbicacionInfeccion extends AppCompatActivity {

    private String pac_dni,pac_names,pac_sex,pac_telef; private int pac_edad;
    private String diag_names, diag_tipo,diag_sintom; private float diag_fiebre;

    private ProgressBar loadbar;
    private RegistrarPacientBD pacientBD;
    private DiagnosticoDB diagnosticoDB;


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
        loadbar = findViewById(R.id.loadDengue);
    }


    private boolean captureAndSavePacient(){
        SharedPreferences sharedPref1 = getSharedPreferences("PacienteRegistrado", MODE_PRIVATE);
        boolean isRegistered1 = sharedPref1.getBoolean("isRegistered1", false);

        pac_dni = sharedPref1.getString("dni", "");
        pac_names = sharedPref1.getString("nombreCompleto", "");
        pac_edad = sharedPref1.getInt("edad", -1);
        pac_sex = sharedPref1.getString("sexo", "");
        pac_telef = sharedPref1.getString("telefono", "");

        return pacientBD.insertPaciente(pac_dni,pac_names,pac_edad,pac_sex,pac_telef) && isRegistered1;
    }

    private boolean captureAndSaveDiagnost(){
        SharedPreferences sharedPref2 = getSharedPreferences("DiagnosticoRegistrado", MODE_PRIVATE);

        boolean isRegistered2 = sharedPref2.getBoolean("isRegistered2", false);
        diag_names = sharedPref2.getString("nombreDiagnostico", "");
        diag_tipo = sharedPref2.getString("tipoDiagnostico", "");
        diag_fiebre = sharedPref2.getFloat("fiebre", -1);
        diag_sintom = sharedPref2.getString("sintomas", "");


        return diagnosticoDB.insertDiagnostico(diag_names,diag_tipo,diag_fiebre,diag_sintom) && isRegistered2;
    }


    public void registraDatosLoad(View view){
        loadbar.setVisibility(View.VISIBLE);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        /*Future<Boolean> result = executor.submit(() -> {

            boolean pacientRegistered = captureAndSavePacient();
            boolean diagnostRegistered = captureAndSaveDiagnost();

            if (!pacientRegistered || !diagnostRegistered) {
                return false;
            }

            boolean lugarInfeccionInserted = dbHelper.insertLugarInfeccion(direccion, departamento, provincia, distrito, latitud, longitud);

            if (!lugarInfeccionInserted) {
                return false;
            }

            String idPaciente = dbHelper.getLastPacienteId();
            int idDiagnostico = dbHelper.getLastDiagnosticoId();
            int idLugarInfeccion = dbHelper.getLastLugarInfeccionId();

            if (idPaciente == null || idDiagnostico == -1 || idLugarInfeccion == -1) {
                return false;
            }

            return dbHelper.insertFichaPaciente(idPaciente, idDiagnostico, idLugarInfeccion, 0, "", "", 0, "", "");
        });*/

        Future<Boolean> result = executor.submit(() -> {

            return captureAndSaveDiagnost() && captureAndSavePacient();
        });

        executor.execute(() -> {
            try {
                boolean insertResult = result.get();
                runOnUiThread(() -> {
                    loadbar.setVisibility(View.GONE);
                    if (insertResult) {
                        Toast.makeText(UbicacionInfeccion.this, "Ficha de paciente registrada exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UbicacionInfeccion.this, "Error al registrar la ficha de paciente", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    loadbar.setVisibility(View.GONE);
                    Toast.makeText(UbicacionInfeccion.this, "Error al registrar la ficha de paciente", Toast.LENGTH_SHORT).show();
                });
            } finally {
                executor.shutdown();
            }
        });
    }


    /* public void NextOrNoPaciente(View view){
        // Mostrar el cuadro de diálogo de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Registro");
        builder.setMessage("¿Está seguro de que desea guardar estos datos?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Registrar el diagnóstico
                validarPaciente();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/





    /////////////////////////////////////////////

}