package com.example.app_epidengue;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.FichaDengueDB;

public class ReporteEpimiologico extends AppCompatActivity {

    private EditText etHistoriaClinica;
    private LinearLayout linearLayoutResultados;
    private FichaDengueDB fichaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte_epimiologico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etHistoriaClinica = findViewById(R.id.txtHistoriaClinica);
        linearLayoutResultados = findViewById(R.id.linearLayoutResultados);
        fichaDB = new FichaDengueDB(this);

    }

    public void buscarFicha(View view) {
        String historiaClinicaStr = etHistoriaClinica.getText().toString().trim();
        if (historiaClinicaStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el N° de Historia Clínica", Toast.LENGTH_SHORT).show();
            return;
        }

        int historiaClinica = Integer.parseInt(historiaClinicaStr);

        // Limpiar los resultados previos
        linearLayoutResultados.removeAllViews();

        Cursor cursor = fichaDB.searchFichaByHC(historiaClinica);
        if (cursor != null && cursor.moveToFirst()) {
            agregarDato(cursor, "NombreCompleto", "Nombre");
            agregarDato(cursor, "Sexo", "Sexo");
            agregarDato(cursor, "Nombre_diagnostico", "Nombre Diagnóstico");
            agregarDato(cursor, "tipo_de_diagnostico", "Tipo Diagnóstico");
            agregarDato(cursor, "fiebre", "Fiebre");
            agregarDato(cursor, "sintomas", "Síntomas");
            agregarDato(cursor, "establecimiento_de_salud", "Establecimiento");
            agregarDato(cursor, "fecha_inicio_sintoma", "Fecha Inicio Síntomas");
            agregarDato(cursor, "Departamento", "Departamento");
            agregarDato(cursor, "Provincia", "Provincia");
            etHistoriaClinica.setText("");
        } else {
            Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public void eliminarFicha(View view) {
        String historiaClinicaStr = etHistoriaClinica.getText().toString().trim();
        if (historiaClinicaStr.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el N° de Historia Clínica", Toast.LENGTH_SHORT).show();
            return;
        }

        int historiaClinica = Integer.parseInt(historiaClinicaStr);

        boolean isDeleted = fichaDB.deleteFichaByHC(historiaClinica);
        if (isDeleted) {
            Toast.makeText(this, "Ficha eliminada correctamente", Toast.LENGTH_SHORT).show();
            linearLayoutResultados.removeAllViews(); // Limpiar vista
        } else {
            Toast.makeText(this, "Error al eliminar la ficha", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarDato(Cursor cursor, String columnName, String label) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex != -1) {
            String value = cursor.getString(columnIndex);
            agregarTextView(label + ": " + value);
        } else {
            agregarTextView(label + ": No disponible");
        }
    }

    private void agregarTextView(String texto) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setTextColor(Color.parseColor("#000080"));
        linearLayoutResultados.addView(textView);
    }

    public void regresarHome(View view){
        Intent instanciarH = new Intent(this, Home.class);
        startActivity(instanciarH);
        finish();
    }
}