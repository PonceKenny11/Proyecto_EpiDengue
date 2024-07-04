package com.example.app_epidengue.validaciones;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class Validaciones {

    private Context contexto;
    public Validaciones(Context context){
        this.contexto = context;
    }


    public boolean validarDiagnosticos(String fiebreStr, String selectCboDiag, String selectCboTipo){
        if (TextUtils.isEmpty(fiebreStr) || selectCboDiag.equals("Seleccione....") || selectCboTipo.equals("Seleccione....")) {
            mensajeToast("Por favor, complete todos los campos");
            return false;
        }else{
            return  true;
        }
    }
    private void mensajeToast(String msj){
        Toast.makeText(contexto, msj, Toast.LENGTH_SHORT).show();
    }
}
