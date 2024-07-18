package com.example.app_epidengue;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.RegistrarPacientBD;
import com.example.app_epidengue.validaciones.Validaciones;

public class RegistrarPaciente extends AppCompatActivity {

    private EditText txtDNI, txtNombreApellidos, txtEdad, txtNTelefono;
    private RadioGroup rdoGroup;
    private RegistrarPacientBD pacienteBD;
    private Validaciones validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnverificar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular vistas
        txtDNI = findViewById(R.id.txtdni);
        txtNombreApellidos = findViewById(R.id.txtnombreapellidos);
        txtEdad = findViewById(R.id.txtedad);
        txtNTelefono = findViewById(R.id.txtntelefono);
        rdoGroup = findViewById(R.id.rdoGpSexo);
        pacienteBD = new RegistrarPacientBD(this);
        validar = new Validaciones(this);

    }

    public void insertarPacienteTemp(View view) {
        String dni = txtDNI.getText().toString().trim();
        String nombreCompleto = txtNombreApellidos.getText().toString().trim();
        String edadStr = txtEdad.getText().toString().trim();
        String telefono = txtNTelefono.getText().toString().trim();
        String sexo = getSexo();

        if (!validar.validarPaciente(dni, nombreCompleto, edadStr, sexo, telefono)) {
            return;
        }

        int edad = Integer.parseInt(edadStr);
        boolean isRegistered = pacienteBD.sendPacienteTemp(dni, nombreCompleto, edad, sexo, telefono);

        if (isRegistered) {
            Toast.makeText(this, "Paciente registrado correctamente", Toast.LENGTH_SHORT).show();
            Intent instanciar = new Intent(this, DiagnosticoPaciente.class);
            startActivity(instanciar);
            finish();
        } else {
            Toast.makeText(this, "Error al registrar el paciente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        }
    }

    private String getSexo() {
        int selectId = rdoGroup.getCheckedRadioButtonId();
        if (selectId != -1) {
            RadioButton rdoSex = findViewById(selectId);
            return rdoSex.getText().toString();
        } else {
            Toast.makeText(this, "Por favor, seleccione el sexo", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void limpiarCampos() {
        txtDNI.setText("");
        txtNombreApellidos.setText("");
        txtEdad.setText("");
        txtNTelefono.setText("");
        rdoGroup.clearCheck();
        txtDNI.requestFocus();
    }

    public void validarDNI(View view) {
        String dni = txtDNI.getText().toString().trim();
        Cursor cursor = pacienteBD.getPatientByDNI(dni);

        if (cursor != null && cursor.moveToFirst()) {
            showAlert("Paciente encontrado", "El paciente con DNI " + dni + " existe.");
        } else {
            showAlert("Paciente no encontrado", "No se encontró un paciente con este DNI " + dni + ".");
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public void regresarHome(View view){
        Intent instanciarH = new Intent(this, Home.class);
        startActivity(instanciarH);
        finish();
    }
}
