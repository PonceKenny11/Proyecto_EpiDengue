package com.example.app_epidengue;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.RegistrarPacientBD;

public class ReporteEpimiologico extends AppCompatActivity {

    private EditText etSearchDNI;

    private TableLayout tableLayout;
    private TextView tvNombreCompleto, tvEdad, tvSexo;

    private RegistrarPacientBD pacientBD;
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

        etSearchDNI = findViewById(R.id.txtSearchDNI);
        tableLayout = findViewById(R.id.tableLayout);
        tvNombreCompleto = findViewById(R.id.tvNombreCompleto);
        tvEdad = findViewById(R.id.tvEdad);
        tvSexo = findViewById(R.id.tvSexo);

        pacientBD = new RegistrarPacientBD(this);

    }


    public void BuscarPaciente(View view){
        String dni = etSearchDNI.getText().toString().trim();
        if (TextUtils.isEmpty(dni)) {
            Toast.makeText(this, "Por favor, ingrese un DNI", Toast.LENGTH_SHORT).show();
            etSearchDNI.setText("");
            etSearchDNI.requestFocus();
        }

        boolean encontro = pacientBD.searchPatient(dni, tvNombreCompleto,tvEdad, tvSexo);
        if(encontro){
            tableLayout.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this, "Paciente no encontrado", Toast.LENGTH_SHORT).show();
            tableLayout.setVisibility(View.GONE);
        }
    }
}