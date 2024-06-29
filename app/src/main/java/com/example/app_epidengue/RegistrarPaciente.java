package com.example.app_epidengue;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarPaciente extends AppCompatActivity {

    EditText txtDNI, txtNombreApellidos, txtEdad, txtNTelefono;
    Button btnVerificar;

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
        btnVerificar = findViewById(R.id.btnverificar);

        // Configurar el botón de Verificar
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = txtDNI.getText().toString();

                if (dni.isEmpty()) {
                    Toast.makeText(RegistrarPaciente.this, "Por favor, ingrese el DNI", Toast.LENGTH_SHORT).show();
                } else {
                    // Simulación de verificación de DNI
                    if (dni.equals("12345678")) {
                        txtNombreApellidos.setText("Juan Pérez");
                        txtEdad.setText("30");
                        txtNTelefono.setText("987654321");
                    } else {
                        Toast.makeText(RegistrarPaciente.this, "DNI no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
