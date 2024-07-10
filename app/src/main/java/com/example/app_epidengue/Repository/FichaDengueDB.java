package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FichaDengueDB {
    private DataBaseHelp dbHelp;

    public FichaDengueDB(Context context){
        dbHelp = new DataBaseHelp(context);
    }

    public boolean insertFichaPaciente(String idPaciente, int idDiagnostico, int idLugarInfeccion, int historiaClinica, String establecimientoSalud,
                                       String fechaInicioSintomas, int semanaEpidemiologica, String fechaHospitalizacion, String fechaMuestraLaboratorio) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_paciente", idPaciente);
        contentValues.put("id_diagnostico", idDiagnostico);
        contentValues.put("id_lugar_infeccion", idLugarInfeccion);
        contentValues.put("Historia_clinica", historiaClinica);
        contentValues.put("establecimiento_de_salud", establecimientoSalud);
        contentValues.put("fecha_inicio_sintoma", fechaInicioSintomas);
        contentValues.put("semana_epidemiologica", semanaEpidemiologica);
        contentValues.put("fecha_hospitalizacion", fechaHospitalizacion);
        contentValues.put("fecha_muestra_laboratorio", fechaMuestraLaboratorio);
        long result = db.insert("ficha_paciente", null, contentValues);
        return result != -1;
    }

    public String getLastPacienteId() {
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        Cursor cursor = null;
        String lastId = null;
        try {
            cursor = db.rawQuery("SELECT DNI FROM paciente ORDER BY ROWID DESC LIMIT 1", null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex("DNI");
                if (index != -1) {
                    lastId = cursor.getString(index);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lastId;
    }

    public int getLastDiagnosticoId() {
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        Cursor cursor = null;
        int lastId = -1;
        try {
            cursor = db.rawQuery("SELECT id_diagnostico FROM diagnostico ORDER BY id_diagnostico DESC LIMIT 1", null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex("id_diagnostico");
                if (index != -1) {
                    lastId = cursor.getInt(index);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lastId;
    }

    public int getLastLugarInfeccionId() {
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        Cursor cursor = null;
        int lastId = -1;
        try {
            cursor = db.rawQuery("SELECT id_lugar_infeccion FROM lugar_de_probable_infeccion ORDER BY id_lugar_infeccion DESC LIMIT 1", null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex("id_lugar_infeccion");
                if (index != -1) {
                    lastId = cursor.getInt(index);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lastId;
    }

}
