package com.example.app_epidengue;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class DiagnosticoPaciente extends AppCompatActivity {

    private Spinner cboNombreDiagnostico, cboTipoDiagnostico;
    private EditText txtFiebre;

    private DiagnosticoDB diagDB;

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

        inicalizandoParametros();
        rellenarCombo();
    }

    private void inicalizandoParametros(){
        cboNombreDiagnostico = findViewById(R.id.cboDiagnostico);//spinners
        cboTipoDiagnostico = findViewById(R.id.cboTipoDiag);//spinners
        txtFiebre = findViewById(R.id.txtFiebre);
        diagDB = new DiagnosticoDB(this);//clase Diagnostico

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
                R.array.tipos_diagnostico, android.R.layout.simple_spinner_item);/*obtener desde string register datos para el Spinner*/
        adapterTipoDiagnostico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboTipoDiagnostico.setAdapter(adapterTipoDiagnostico);
    }


    private void iniciarDatos(){
        String nombreDiagnostico = cboNombreDiagnostico.getSelectedItem().toString();
        String tipoDiagnostico = cboTipoDiagnostico.getSelectedItem().toString();
        String fiebreStr = txtFiebre.getText().toString().trim();



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