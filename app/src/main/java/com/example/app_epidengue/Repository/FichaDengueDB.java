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


    public Cursor searchFichaByHC(int historiaClinica) { //buscar ficha por 3 tablas
        SQLiteDatabase db = this.dbHelp.getReadableDatabase();
        String query = "SELECT f.id_paciente, f.id_diagnostico, f.id_lugar_infeccion, p.NombreCompleto, p.Sexo, " +
                "d.Nombre_diagnostico, d.tipo_de_diagnostico, d.fiebre, d.sintomas, " +
                "f.establecimiento_de_salud, f.fecha_inicio_sintoma, l.Departamento, l.Provincia " +
                "FROM ficha_paciente f " +
                "INNER JOIN paciente p ON f.id_paciente = p.DNI " +
                "INNER JOIN diagnostico d ON f.id_diagnostico = d.id_diagnostico " +
                "INNER JOIN lugar_de_probable_infeccion l ON f.id_lugar_infeccion = l.id_lugar_infeccion " +
                "WHERE f.Historia_clinica = ?";
        return db.rawQuery(query, new String[]{String.valueOf(historiaClinica)});
    }

    public boolean deleteFichaByHC(int historiaClinica) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();

        // Obtener los IDs relacionados
        Cursor cursor = db.rawQuery("SELECT id_paciente, id_diagnostico, id_lugar_infeccion FROM ficha_paciente WHERE Historia_clinica = ?", new String[]{String.valueOf(historiaClinica)});
        if (cursor != null && cursor.moveToFirst()) {
            int idPacienteIndex = cursor.getColumnIndex("id_paciente");
            int idDiagnosticoIndex = cursor.getColumnIndex("id_diagnostico");
            int idLugarInfeccionIndex = cursor.getColumnIndex("id_lugar_infeccion");

            if (idPacienteIndex >= 0 && idDiagnosticoIndex >= 0 && idLugarInfeccionIndex >= 0) {
                String idPaciente = cursor.getString(idPacienteIndex);
                int idDiagnostico = cursor.getInt(idDiagnosticoIndex);
                int idLugarInfeccion = cursor.getInt(idLugarInfeccionIndex);
                cursor.close();

                // Iniciar transacción
                db.beginTransaction();
                try {
                    // Eliminar registros relacionados
                    db.delete("paciente", "DNI = ?", new String[]{idPaciente});
                    db.delete("diagnostico", "id_diagnostico = ?", new String[]{String.valueOf(idDiagnostico)});
                    db.delete("lugar_de_probable_infeccion", "id_lugar_infeccion = ?", new String[]{String.valueOf(idLugarInfeccion)});

                    // Eliminar la ficha
                    db.delete("ficha_paciente", "Historia_clinica = ?", new String[]{String.valueOf(historiaClinica)});

                    // Confirmar transacción
                    db.setTransactionSuccessful();
                    return true;
                } finally {
                    db.endTransaction();
                }
            }
            cursor.close();
        }
        return false;
    }



    //buscar por IDs

    public String getLastPacienteId() { // DNI el ultimo dato de paciente
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

    public int getLastDiagnosticoId() { //busca el id de diagnostico
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

    public int getLastLugarInfeccionId() { //busca el ultimo id de lugar infeccion
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
