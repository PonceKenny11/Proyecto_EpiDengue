package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FichaDengueDB {
    private DataBaseHelp dbHelp;

    public FichaDengueDB(Context context){
        dbHelp = new DataBaseHelp(context);
    }

    public boolean insertFichaPaciente(int historiaClinica, String establecimientoDeSalud, int diagnosticoId, int lugarInfeccionId,
                                       String fechaInicioSintoma, int semanaEpidemiologica, String fechaHospitalizacion, String fechaMuestraLaboratorio) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Historia_clinica", historiaClinica);
        contentValues.put("establecimiento_de_salud", establecimientoDeSalud);
        contentValues.put("id_diagnostico", diagnosticoId);
        contentValues.put("id_lugar_infeccion", lugarInfeccionId);
        contentValues.put("fecha_inicio_sintoma", fechaInicioSintoma);
        contentValues.put("semana_epidemiologica", semanaEpidemiologica);
        contentValues.put("fecha_hospitalizacion", fechaHospitalizacion);
        contentValues.put("fecha_muestra_laboratorio", fechaMuestraLaboratorio);

        long result = db.insert("ficha_paciente", null, contentValues);
        return result != -1;
    }

}
