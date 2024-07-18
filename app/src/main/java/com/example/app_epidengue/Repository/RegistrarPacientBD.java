package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class RegistrarPacientBD {

    private DataBaseHelp dbHelp;
    private Context context;
    private static final String TAG = "RegistrarPacientBD";

    public RegistrarPacientBD(Context context) {
        dbHelp = new DataBaseHelp(context);
        dbHelp.openDatabase();
        this.context = context;
    }

    /*public boolean searchPatient(String idDNI, TextView tvNombreCompleto, TextView tvEdad, TextView tvSexo) {
        Cursor cursor = null;
        try {
            cursor = getPatientByDNI(idDNI);
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
                return true;
            } else {
                return false;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }*/

    public boolean insertPaciente(String dni, String nombreCompleto, int edad, String sexo, String telefono) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DNI", dni);
        contentValues.put("NombreCompleto", nombreCompleto);
        contentValues.put("Edad", edad);
        contentValues.put("Sexo", sexo);
        contentValues.put("NroTelefono", telefono);

        long result = -1;
        try {
            result = db.insertOrThrow("paciente", null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error inserting paciente: " + e.getMessage());
        } finally {
            db.close();
        }
        return result != -1;
    }


    public boolean sendPacienteTemp(String dni, String nombreCompleto, int edad, String sexo, String telefono) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PacienteRegistrado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editarShared = sharedPreferences.edit();
        editarShared.putBoolean("isRegistered1", true);
        editarShared.putString("dni", dni);
        editarShared.putString("nombreCompleto", nombreCompleto);
        editarShared.putInt("edad", edad);
        editarShared.putString("sexo", sexo);
        editarShared.putString("telefono", telefono);
        editarShared.apply();

        SharedPreferences prefsCheck = context.getSharedPreferences("PacienteRegistrado", Context.MODE_PRIVATE);
        boolean isRegistered = prefsCheck.getBoolean("isRegistered1", false);
        String savedDni = prefsCheck.getString("dni", "");
        String savedNombreCompleto = prefsCheck.getString("nombreCompleto", "");
        int savedEdad = prefsCheck.getInt("edad", -1);
        String savedSexo = prefsCheck.getString("sexo", "");
        String savedTelefono = prefsCheck.getString("telefono", "");

        return (isRegistered && dni.equals(savedDni) && nombreCompleto.equals(savedNombreCompleto) &&
                edad == savedEdad && sexo.equals(savedSexo) && telefono.equals(savedTelefono));
    }

    public boolean deletePatientByDNI(String dni) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        int result = db.delete("paciente", "DNI = ?", new String[]{dni});
        db.close();
        return result > 0;
    }

    public Cursor getPatientByDNI(String dni) {
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        return db.rawQuery("SELECT * FROM paciente WHERE DNI = ?", new String[]{dni});
    }


}
