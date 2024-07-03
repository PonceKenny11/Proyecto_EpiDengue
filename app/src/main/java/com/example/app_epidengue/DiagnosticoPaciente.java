package com.example.app_epidengue;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.DiagnosticoDB;

public class DiagnosticoPaciente extends AppCompatActivity {

    private Spinner cboNombreDiagnostico, cboTipoDiagnostico;
    private EditText txtFiebre;

    private DiagnosticoDB diagDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diagnostico_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicalizandoParametros();
    }

    private void inicalizandoParametros(){
        cboNombreDiagnostico = findViewById(R.id.cboDiagnostico);
        cboTipoDiagnostico = findViewById(R.id.cboTipoDiag);
        txtFiebre = findViewById(R.id.txtFiebre);
        diagDB = new DiagnosticoDB(this);

        ArrayAdapter<CharSequence> adapterNombreDiagnostico = ArrayAdapter.createFromResource(this,
                R.array.nombres_diagnostico, android.R.layout.simple_spinner_item);

        adapterNombreDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cboNombreDiagnostico.setAdapter(adapterNombreDiagnostico);

        ArrayAdapter<CharSequence> adapterTipoDiagnostico = ArrayAdapter.createFromResource(this,
                R.array.tipos_diagnostico, android.R.layout.simple_spinner_item);
        adapterTipoDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboTipoDiagnostico.setAdapter(adapterTipoDiagnostico);
    }

    private void iniciarDatos(){
        String nombreDiagnostico = cboNombreDiagnostico.getSelectedItem().toString();
        String tipoDiagnostico = cboTipoDiagnostico.getSelectedItem().toString();
        String fiebreStr = txtFiebre.getText().toString().trim();

        if (TextUtils.isEmpty(fiebreStr) ) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }

        float fiebre;

        try {
            fiebre = Float.parseFloat(fiebreStr);

            boolean wasFever = fiebre >= 37.5 && fiebre <= 40.0;

            if (!wasFever) {
                Toast.makeText(this, "se considera si hubo Fiebre entre: 37.5 y 40 grados", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese un valor válido para la fiebre", Toast.LENGTH_SHORT).show();

        }



        /*boolean isInserted = dbHelper.insertDiagnostico(nombreDiagnostico, tipoDiagnostico, fiebre, sintomas);

        if (isInserted) {
            Toast.makeText(this, "Diagnóstico registrado exitosamente", Toast.LENGTH_SHORT).show();
            etFiebre.setText("");
            etSintomas.setText("");
        } else {
            Toast.makeText(this, "Error al registrar el diagnóstico", Toast.LENGTH_SHORT).show();
        }*/
    }


}