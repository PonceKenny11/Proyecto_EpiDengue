package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
public class RegistrarPacientBD {

    private String dni, nombreComplet, edad, sexo, telefono;
    private DataBaseHelp dbHelp;

    public RegistrarPacientBD(String dni,String nombreComplet, String edad, String sexo, String telefono, Context context){
        dbHelp = new DataBaseHelp(context);
        this.dni = dni;
        this.nombreComplet = nombreComplet;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
    }

    public int registerPatient() {
        if (TextUtils.isEmpty(dni) || TextUtils.isEmpty(nombreComplet) || TextUtils.isEmpty(edad) ||
                TextUtils.isEmpty(sexo) || TextUtils.isEmpty(telefono)) {
            return 0;
        }

        int edadInt = Integer.parseInt(edad);

        boolean isInserted = insertPaciente(dni, nombreComplet, edadInt, sexo, telefono);

        if (isInserted) {
            return  1;
        } else {
            return 2;
        }
    }

    private boolean insertPaciente(String dni, String nombreCompleto, int edad, String sexo, String telefono) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DNI", dni);
        contentValues.put("NombreCompleto", nombreCompleto);
        contentValues.put("Edad", edad);
        contentValues.put("Sexo", sexo);
        contentValues.put("NroTelefono", telefono);

        long result = db.insert("paciente", null, contentValues);
        return result != -1;
    }
}
