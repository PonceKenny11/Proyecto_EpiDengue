package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
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



}
