package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

public class RegistrarPacientBD {

    private DataBaseHelp dbHelp;
    private Context context;

    public RegistrarPacientBD(Context context){
        dbHelp = new DataBaseHelp(context);
        dbHelp.openDatabase();
        this.context = context;
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

    public boolean sendPacienteTemp(String dni,String nombreComplet, String edad, String sexo, String telefono){
        SharedPreferences sharedPreferences = context.getSharedPreferences("PacienteRegistrado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editarShared = sharedPreferences.edit();
        // Guardar los valores en SharedPreferences
        editarShared.putBoolean("isRegistered1", true);
        int edadInt = Integer.parseInt(edad);


        editarShared.putString("dni", dni);
        editarShared.putString("nombreCompleto", nombreComplet);
        editarShared.putInt("edad",edadInt);
        editarShared.putString("telefono", telefono);

        // Aplicar los cambios
        editarShared.apply();

        //vereficar si se guardo o no
        SharedPreferences prefsCheck = context.getSharedPreferences("PacienteRegistrado", Context.MODE_PRIVATE);
        boolean isRegistered = prefsCheck.getBoolean("isRegistered1", false);
        String savedDni = prefsCheck.getString("dni", "");
        String savedNombreCompleto = prefsCheck.getString("nombreCompleto", "");
        int savedEdad = prefsCheck.getInt("edad", -1);
        String savedSexo = prefsCheck.getString("sexo", "");
        String savedTelefono = prefsCheck.getString("telefono", "");

        return (isRegistered && dni.equals(savedDni) && nombreComplet.equals(savedNombreCompleto) &&
                edadInt == savedEdad && sexo.equals(savedSexo) && telefono.equals(savedTelefono));

    }



    //////////////////PRIVATE/////////////////////////////////////////

    private boolean insertPaciente(String dni,String nombreComplet, String edad, String sexo, String telefono) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int edadInt = Integer.parseInt(edad);
        contentValues.put("DNI", dni);
        contentValues.put("NombreCompleto", nombreComplet);
        contentValues.put("Edad", edadInt);
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
