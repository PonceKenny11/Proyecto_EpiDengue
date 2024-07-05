package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.TextView;

public class RegistrarPacientBD {

    private String dni, nombreComplet, edad, sexo, telefono;
    private DataBaseHelp dbHelp;

    public RegistrarPacientBD(Context context){
        dbHelp = new DataBaseHelp(context);
        dbHelp.openDatabase();
    }
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

    public boolean searchPatient(String idDNI, TextView tvNombreCompleto, TextView tvEdad,TextView tvSexo) {

        Cursor cursor = getPatientByDNI(idDNI);
        if (cursor != null && cursor.moveToFirst()) {
            int nombreCompletoIndex = cursor.getColumnIndex("NombreCompleto");
            int edadIndex = cursor.getColumnIndex("Edad");
            int sexoIndex = cursor.getColumnIndex("Sexo");

            if (nombreCompletoIndex != -1) {
                tvNombreCompleto.setText(cursor.getString(nombreCompletoIndex));
            }
            if (edadIndex != -1) {
                tvEdad.setText(String.valueOf(cursor.getInt(edadIndex)));
            }
            if (sexoIndex != -1) {
                tvSexo.setText(cursor.getString(sexoIndex));
            }
            return  true;
        } else {
            return  false;
        }
    }

    public boolean deletePatientByDNI(String dni) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        int result = db.delete("paciente", "DNI = ?", new String[]{dni});
        return result > 0;
    }

    //PRIVATE
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



    private Cursor getPatientByDNI(String dni) {
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        return db.rawQuery("SELECT * FROM paciente WHERE DNI = ?", new String[]{dni});
    }

}
