package com.example.app_epidengue.validaciones;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Validaciones {

    private Context contexto;
    public Validaciones(Context context){
        this.contexto = context;
    }



    public boolean validarPaciente(String dni,String nombreComplet, String edad, String sexo, String telefono,boolean isInserted){
        if(TextUtils.isEmpty(dni) || TextUtils.isEmpty(nombreComplet) || TextUtils.isEmpty(edad) ||
                TextUtils.isEmpty(sexo) || TextUtils.isEmpty(telefono)){
            mensajeToast("Por favor, complete todos los campos");
            return false;
            // !isInsert(si enviar false - por lo tanto se convierte en true, lo que indica que hay error al registrar en Shared)
        }else if (!isInserted){
            mensajeToast("Error al registrar el paciente // Error Shared");
            return false;
        }else {
            mensajeToast("Paciente registrado");
            return true;
        }
    }
    public boolean validarDiagnosticos(String fiebreStr, String selectCboDiag, String selectCboTipo,
                                       String checkDSSA, String checkDCSA, String checkDG){

        float fiebre = Float.parseFloat(fiebreStr);

        if (TextUtils.isEmpty(fiebreStr) || selectCboDiag.equals("Seleccione....") || selectCboTipo.equals("Seleccione....")) {
            mensajeToast("Por favor, complete todos los campos");
            return false;
        } else if (fiebre >= 37.5 && fiebre <= 40.0) {
            try {
                mensajeToast("Se considera si hubo Fiebre entre: 37.5 y 40 grados");
                return false;
            } catch (NumberFormatException e) {
                mensajeToast("Por favor, ingrese un valor válido para la fiebre");
                return false;

            }

        } else if (checkDSSA.isEmpty() && checkDCSA.isEmpty() && checkDG.isEmpty()) {
            mensajeToast("Por favor seleccione al menos una opción");
            return false;
        } else{
            return  true;
        }
    }


    public void validarFichaDengue(EditText editTextFecha1, EditText editTextFecha2, EditText editTextFecha3 ) {
        if (areDatesValid(editTextFecha1,editTextFecha2,editTextFecha3)) {
            mensajeToast("Todas las fechas son válidas");
        } else {
            mensajeToast("Todas las fechas son válidas");
        }
    }

    private boolean areDatesValid(EditText editTextFecha1, EditText editTextFecha2, EditText editTextFecha3) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        sdf.setLenient(false);
        try {
            Date date1 = sdf.parse(editTextFecha1.getText().toString());
            Date date2 = sdf.parse(editTextFecha2.getText().toString());
            Date date3 = sdf.parse(editTextFecha3.getText().toString());
            Date currentDate = new Date();

            if (date1.after(currentDate) || date2.after(currentDate) || date3.after(currentDate)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getStrSintomas(String s1, String s2, String s3){

        if(!s1.isEmpty() && !s2.isEmpty() &&!s3.isEmpty()){
            return s1 + s2 + s3;
        } else if (!s1.isEmpty() && !s2.isEmpty()) {
            return s1 + s2;
        } else if (!s1.isEmpty()) {
            return  s1;
        }else {
            return "S:NULL";
        }

    }
    private void mensajeToast(String msj){
        Toast.makeText(contexto, msj, Toast.LENGTH_SHORT).show();
    }
}
