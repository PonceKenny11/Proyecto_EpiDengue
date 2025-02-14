package com.example.app_epidengue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.DiagnosticoDB;
import com.example.app_epidengue.validaciones.Validaciones;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticoPaciente extends AppCompatActivity {

    private Spinner cboNombreDiagnostico, cboTipoDiagnostico;
    private EditText txtFiebre;

    private String nombreDiagnostico, tipoDiagnostico,fiebreStr,selectDG, selectDSSA, selectDCSA;
    private DiagnosticoDB diagDB;
    private Validaciones validar;
    private CheckBox CheckSS1,CheckSS2,CheckSS3,CheckSS4;
    private CheckBox CheckCS1,CheckCS2,CheckCS3,CheckCS4;
    private CheckBox CheckG1,CheckG2,CheckG3;
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

        inicializarParametros();
        rellenarCombo();
    }

    private void inicializarParametros() {
        cboNombreDiagnostico = findViewById(R.id.cboDiagnostico);
        cboTipoDiagnostico = findViewById(R.id.cboTipoDiag);
        txtFiebre = findViewById(R.id.txtFiebre);

        diagDB = new DiagnosticoDB(this);
        validar = new Validaciones(this);

        CheckSS1 = findViewById(R.id.checkSS1);
        CheckSS2 = findViewById(R.id.checkSS2);
        CheckSS3 = findViewById(R.id.checkSS3);
        CheckSS4 = findViewById(R.id.checkSS4);

        CheckCS1 = findViewById(R.id.checkCS1);
        CheckCS2 = findViewById(R.id.checkCS2);
        CheckCS3 = findViewById(R.id.checkCS3);
        CheckCS4 = findViewById(R.id.checkCS4);

        CheckG1 = findViewById(R.id.checkG1);
        CheckG2 = findViewById(R.id.checkG2);
        CheckG3 = findViewById(R.id.checkG3);
    }

    private void rellenarCombo() {
        ArrayAdapter<CharSequence> adapterNombreDiagnostico = ArrayAdapter.createFromResource(this,
                R.array.nombres_diagnostico, android.R.layout.simple_spinner_item);
        adapterNombreDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboNombreDiagnostico.setAdapter(adapterNombreDiagnostico);

        ArrayAdapter<CharSequence> adapterTipoDiagnostico = ArrayAdapter.createFromResource(this,
                R.array.tipos_diagnostico, android.R.layout.simple_spinner_item);
        adapterTipoDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboTipoDiagnostico.setAdapter(adapterTipoDiagnostico);
    }

    private void iniciarDatos() {
        nombreDiagnostico = cboNombreDiagnostico.getSelectedItem().toString();
        tipoDiagnostico = cboTipoDiagnostico.getSelectedItem().toString();
        fiebreStr = txtFiebre.getText().toString().trim();

        // Obtener todos los datos seleccionados
        selectDG = getSelectedCheckBoxData(CheckG1, CheckG2, CheckG3);
        selectDSSA = getSelectedCheckBoxData(CheckSS1, CheckSS2, CheckSS3, CheckSS4);
        selectDCSA = getSelectedCheckBoxData(CheckCS1, CheckCS2, CheckCS3, CheckCS4);
    }

    /////////////////////////////ONCLICK///////////////////////////
    public void startRegistrarDiag(View view) {
        iniciarDatos();

        if (!validar.validarDiagnosticos(fiebreStr, nombreDiagnostico, tipoDiagnostico, selectDSSA, selectDCSA, selectDG)) {
            return;
        }

        float fiebre = Float.parseFloat(fiebreStr); // Ya validado en `validarDiagnosticos`
        String sintomas = validar.getStrSintomas(selectDSSA, selectDCSA, selectDG);
        boolean isInserted = diagDB.sendDiagnosticoTemp(nombreDiagnostico, tipoDiagnostico, fiebre, sintomas);
        dataAlreadyEntered(isInserted);
    }

    public void retrocederPestana(View view) {
        Intent instanciar2 = new Intent(this, RegistrarPaciente.class);
        startActivity(instanciar2);
        finish();
    }

    private void dataAlreadyEntered(boolean isInserted) {
        if (isInserted) {
            Toast.makeText(this, "Diagnóstico registrado", Toast.LENGTH_SHORT).show();
            Intent instanciar = new Intent(this, Ubicacion.class);
            startActivity(instanciar);
            finish();
        } else {
            Toast.makeText(this, "Error al registrar el diagnóstico", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para obtener los datos seleccionados de un conjunto de CheckBox
    private String getSelectedCheckBoxData(CheckBox... checkBoxes) {
        List<String> selectedData = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                selectedData.add(checkBox.getText().toString());
            }
        }
        return String.join(";", selectedData);
    }

    // Método para obtener el primer dato seleccionado de un conjunto de CheckBox
    private String getFirstSelectedCheckBoxData(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                return checkBox.getText().toString();
            }
        }
        return "";
    }


}