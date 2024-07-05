package com.example.app_epidengue.validaciones;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class Validaciones {

    private Context contexto;
    public Validaciones(Context context){
        this.contexto = context;
    }


    public boolean validarDiagnosticos(String fiebreStr, String selectCboDiag, String selectCboTipo, String checkDSSA, String checkDCSA, String checkDG){
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
