package com.example.app_epidengue;

import android.content.Intent;
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

    public void insertarPacienteTemp(View view){
        String dni = txtDNI.getText().toString().trim();
        String nombreCompleto = txtNombreApellidos.getText().toString().trim();
        String edadStr = txtEdad.getText().toString().trim();
        String telefono = txtNTelefono.getText().toString().trim();

        boolean isRegistered = pacienteBD.sendPacienteTemp(dni,nombreCompleto,edadStr,getSexo(),telefono);//debe devolver true si se guardo correctamente
        boolean isValidated = validar.validarPaciente(dni,nombreCompleto,edadStr,getSexo(),telefono,isRegistered);

        if (isValidated){
            Intent instanciar= new Intent(this, DiagnosticoPaciente.class);
            startActivity(instanciar);
            finish();
        }else{
            limpiarCampos();
        }
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


   /* public void NextOrNoPaciente(View view){
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
    }*/

    public void validarDNI(View view){
        //inicializandoData();
    }
}
