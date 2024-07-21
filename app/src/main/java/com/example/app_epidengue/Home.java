package com.example.app_epidengue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Vincular ImageViews
        ImageView imageRegistrarFicha = findViewById(R.id.image_registrar_ficha);
        ImageView imageReporteEpidemiologico = findViewById(R.id.image_reporte_epidemiologico);
        ImageView imageGraficoEpidemiologico = findViewById(R.id.image_grafico_epidemiologico);

        // Configurar OnClickListeners
        imageRegistrarFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad RegistrarPaciente
                Intent intent = new Intent(Home.this, RegistrarPaciente.class);
                startActivity(intent);
                finish();
            }
        });

        imageReporteEpidemiologico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad ReporteEpidemiologico (suponiendo que tienes esta actividad)
              Intent intent = new Intent(Home.this, ReporteEpimiologico.class);
              startActivity(intent);
              finish();
            }
        });

        imageGraficoEpidemiologico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad GraficoEpidemiologico (suponiendo que tienes esta actividad)
              Intent intent = new Intent(Home.this, Ubicacion.class);
               startActivity(intent);
            }
        });
    }
}