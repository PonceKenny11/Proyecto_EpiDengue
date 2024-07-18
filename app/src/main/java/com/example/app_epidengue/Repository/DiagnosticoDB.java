package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DiagnosticoDB {

    private DataBaseHelp dbHelp;
    private Context context;
    private static final String TAG = "DiagnosticoDB";

    public DiagnosticoDB(Context context) {
        dbHelp = new DataBaseHelp(context);
        this.context = context;
    }

    public boolean insertDiagnostico(String nombreDiagnostico, String tipoDiagnostico, float fiebre, String sintomas) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nombre_diagnostico", nombreDiagnostico);
        contentValues.put("tipo_de_diagnostico", tipoDiagnostico);
        contentValues.put("fiebre", fiebre);
        contentValues.put("sintomas", sintomas);

        long result = -1;
        try {
            result = db.insertOrThrow("diagnostico", null, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Error inserting diagnostico: " + e.getMessage());
        } finally {
            db.close();
        }
        return result != -1;
    }

    public boolean sendDiagnosticoTemp(String nombreDiagnostico, String tipoDiagnostico, float fiebre, String sintomas){
        SharedPreferences sharedPreferences = context.getSharedPreferences("DiagnosticoRegistrado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editarShared = sharedPreferences.edit();
        editarShared.putBoolean("isRegistered2", true);
        editarShared.putString("nombreDiagnostico", nombreDiagnostico);
        editarShared.putString("tipoDiagnostico", tipoDiagnostico);
        editarShared.putFloat("fiebre", fiebre);
        editarShared.putString("sintomas", sintomas);
        editarShared.apply();

        SharedPreferences prefsCheck = context.getSharedPreferences("DiagnosticoRegistrado", Context.MODE_PRIVATE);
        boolean isRegistered = prefsCheck.getBoolean("isRegistered2", false);
        String savedNombreDiagnostico = prefsCheck.getString("nombreDiagnostico", "");
        String savedTipoDiagnostico = prefsCheck.getString("tipoDiagnostico", "");
        float savedFiebre = prefsCheck.getFloat("fiebre", -1);
        String savedSintomas = prefsCheck.getString("sintomas", "");

        return (isRegistered && nombreDiagnostico.equals(savedNombreDiagnostico) &&
                tipoDiagnostico.equals(savedTipoDiagnostico) &&
                fiebre == savedFiebre &&
                sintomas.equals(savedSintomas));
    }

    public Cursor getDiagnosticoByName(String nombreDiagnostico) {
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        return db.rawQuery("SELECT * FROM diagnostico WHERE Nombre_diagnostico = ?", new String[]{nombreDiagnostico});
    }

    public boolean deleteDiagnosticoByName(String nombreDiagnostico) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        int result = db.delete("diagnostico", "Nombre_diagnostico = ?", new String[]{nombreDiagnostico});
        db.close();
        return result > 0;
    }
}
