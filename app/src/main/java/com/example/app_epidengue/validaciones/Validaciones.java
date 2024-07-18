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

    public boolean validarPaciente(String dni, String nombreCompleto, String edadStr, String sexo, String telefono) {
        if (TextUtils.isEmpty(dni) || TextUtils.isEmpty(nombreCompleto) || TextUtils.isEmpty(edadStr) ||
                TextUtils.isEmpty(sexo) || TextUtils.isEmpty(telefono)) {
            mensajeToast("Por favor, complete todos los campos");
            return false;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            mensajeToast("Por favor, ingrese una edad válida");
            return false;
        }

        if (edad < 0) {
            mensajeToast("La edad no puede ser negativa");
            return false;
        }

        return true;
    }

    public boolean validarDiagnosticos(String fiebreStr, String selectCboDiag, String selectCboTipo,
                                       String checkDSSA, String checkDCSA, String checkDG) {

        if (TextUtils.isEmpty(fiebreStr) || selectCboDiag.equals("Seleccione....") || selectCboTipo.equals("Seleccione....")) {
            mensajeToast("Por favor, complete todos los campos");
            return false;
        }

        float fiebre;
        try {
            fiebre = Float.parseFloat(fiebreStr);
        } catch (NumberFormatException e) {
            mensajeToast("Por favor, ingrese un valor válido para la fiebre");
            return false;
        }

        if (fiebre < 37.5 || fiebre > 40.0) {
            mensajeToast("Se considera fiebre si está entre 37.5 y 40 grados");
            return false;
        }

        if (TextUtils.isEmpty(checkDSSA) && TextUtils.isEmpty(checkDCSA) && TextUtils.isEmpty(checkDG)) {
            mensajeToast("Por favor seleccione al menos una opción");
            return false;
        }

        return true;
    }

    public boolean validarFichaDengue(EditText editTextFecha1, EditText editTextFecha2, EditText editTextFecha3, int idDiagnostico,
                                      int idLugarInfeccion, String idPaciente) {
        if (!areDatesValid(editTextFecha1, editTextFecha2, editTextFecha3)) {
            mensajeToast("Una o más fechas no son válidas");
            return false;
        }

        if (idPaciente == null || idDiagnostico == -1 || idLugarInfeccion == -1) {
            mensajeToast("Error al obtener identificadores de las tablas");
            return false;
        }

        mensajeToast("Ficha validada correctamente");
        return true;
    }

    private boolean areDatesValid(EditText editTextFecha1, EditText editTextFecha2, EditText editTextFecha3) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        sdf.setLenient(false);
        try {
            Date date1 = sdf.parse(editTextFecha1.getText().toString());
            Date date2 = sdf.parse(editTextFecha2.getText().toString());
            Date date3 = sdf.parse(editTextFecha3.getText().toString());
            Date currentDate = new Date();

            if (date1 == null || date2 == null || date3 == null) {
                return false;
            }

            return !date1.after(currentDate) && !date2.after(currentDate) && !date3.after(currentDate);
        } catch (Exception e) {
            return false;
        }
    }

    public String getStrSintomas(String s1, String s2, String s3) {
        StringBuilder sintomas = new StringBuilder();

        if (!TextUtils.isEmpty(s1)) sintomas.append(s1);
        if (!TextUtils.isEmpty(s2)) {
            if (sintomas.length() > 0) sintomas.append("; ");
            sintomas.append(s2);
        }
        if (!TextUtils.isEmpty(s3)) {
            if (sintomas.length() > 0) sintomas.append("; ");
            sintomas.append(s3);
        }

        return sintomas.length() > 0 ? sintomas.toString() : "S:NULL";
    }

    private void mensajeToast(String msj) {
        Toast.makeText(contexto, msj, Toast.LENGTH_SHORT).show();
    }
}
