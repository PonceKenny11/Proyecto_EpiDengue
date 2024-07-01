package com.example.app_epidengue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarPaciente extends AppCompatActivity {

    EditText txtDNI, txtNombreApellidos, txtEdad, txtNTelefono;
    RadioGroup rdoGroup;

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
        rdoGroup = findViewById(R.id.rdoGpSexo);


    }


    private String getSexo(){
        String data;
        int selectId = rdoGroup.getCheckedRadioButtonId();
        if(selectId != -1){
            RadioButton rdoSex = findViewById(selectId);
            data = rdoSex.getText().toString();
            return  data;
        }else{
            data = "Selecione el sexo por favor!";
            return data;
        }
    }
}
