package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DiagnosticoDB {

    private DataBaseHelp dbHelp;

    public DiagnosticoDB(Context context){
        dbHelp = new DataBaseHelp(context);
    }
    public boolean insertDiagnostico(String nombreDiagnostico, String tipoDiagnostico, float fiebre, String sintomas) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nombre_diagnostico", nombreDiagnostico);
        contentValues.put("tipo_de_diagnostico", tipoDiagnostico);
        contentValues.put("fiebre", fiebre);
        contentValues.put("sintomas", sintomas);

        long result = db.insert("diagnostico", null, contentValues);
        return result != -1;
    }

    public Cursor getDiagnosticoByName(String nombreDiagnostico) {/*Consultar por Nombre de Diagnostico*/
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        return db.rawQuery("SELECT * FROM diagnostico WHERE Nombre_diagnostico = ?", new String[]{nombreDiagnostico});
    }

    public boolean deleteDiagnosticoByName(String nombreDiagnostico) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        int result = db.delete("diagnostico", "Nombre_diagnostico = ?", new String[]{nombreDiagnostico});
        return result > 0;
    }
}
