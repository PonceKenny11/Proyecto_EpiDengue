package com.example.app_epidengue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class RegistrarPaciente extends AppCompatActivity {

    private EditText txtDNI, txtNombreApellidos, txtEdad, txtNTelefono;
    private RadioGroup rdoGroup;
    private RegistrarPacientBD pacienteBD;
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

    private void inicializandoData(){
        String dni = txtDNI.getText().toString().trim();
        String nombreCompleto = txtNombreApellidos.getText().toString().trim();
        String edadStr = txtEdad.getText().toString().trim();
        String telefono = txtNTelefono.getText().toString().trim();

        pacienteBD = new RegistrarPacientBD(dni,nombreCompleto,edadStr,getSexo(),telefono,this);
    }

    private String getSexo(){
        String data;
        int selectId = rdoGroup.getCheckedRadioButtonId();
        if(selectId != -1){
            RadioButton rdoSex = findViewById(selectId);
            data = rdoSex.getText().toString();
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
            return  data;
        }else{
            Toast.makeText(this, "data null", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void limpiarCampos(){
        txtDNI.setText("");
        txtNombreApellidos.setText("");
        txtEdad.setText("");
        txtNTelefono.setText("");
        rdoGroup.clearCheck();
        txtDNI.requestFocus();
    }

    ///PUBLIC
    private void validarPaciente(){
        int getPac = pacienteBD.registerPatient();
        if(getPac == 1){
            Toast.makeText(this, "Paciente registrado exitosamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            Intent instanciar = new Intent(this, ReporteEpimiologico.class);
            startActivity(instanciar);
            finish();
        }else if(getPac == 0){
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else if (getPac == 2) {
            Toast.makeText(this, "Error al registrar el paciente -- Error 2", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        }
    }


    public void NextOrNoPaciente(View view){
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
    }

    public void validarDNI(View view){
        inicializandoData();
    }
}
