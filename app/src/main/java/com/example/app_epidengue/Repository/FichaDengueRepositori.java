package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_epidengue.models.FichaDengue;

import java.util.ArrayList;
import java.util.List;
public class FichaDengueRepositori {
    private DataBaseHelp dbHelper;

    public FichaDengueRepositori(Context context) {
        dbHelper = new DataBaseHelp(context);
    }

    public void insert(FichaDengue fichaPaciente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_paciente", fichaPaciente.getHistoriaClinica());
        values.put("Historia_clinica", fichaPaciente.getHistoriaClinica());
        /*values.put("establecimiento_de_salud", fichaPaciente.getEstablecimientoDeSalud());
        values.put("id_diagnostico", fichaPaciente.getIdDiagnostico());
        values.put("id_lugar_infeccion", fichaPaciente.getIdLugarInfeccion());
        values.put("fecha_inicio_sintoma", fichaPaciente.getFechaInicioSintoma());
        values.put("semana_epidemiologica", fichaPaciente.getSemanaEpidemiologica());
        values.put("fecha_hospitalizacion", fichaPaciente.getFechaHospitalizacion());
        values.put("fecha_muestra_laboratorio", fichaPaciente.getFechaMuestraLaboratorio());*/
        db.insert("ficha_paciente", null, values);
        db.close();
    }

    public List<FichaDengue> getAll() {
        List<FichaDengue> fichas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("ficha_paciente", null, null, null, null, null, null);

        /*if (cursor.moveToFirst()) {
            do {
                FichaPaciente ficha = new FichaPaciente();
                ficha.setIdFicha(cursor.getInt(cursor.getColumnIndex("id_ficha")));
                ficha.setIdPaciente(cursor.getString(cursor.getColumnIndex("id_paciente")));
                ficha.setHistoriaClinica(cursor.getInt(cursor.getColumnIndex("Historia_clinica")));
                ficha.setEstablecimientoDeSalud(cursor.getString(cursor.getColumnIndex("establecimiento_de_salud")));
                ficha.setIdDiagnostico(cursor.getInt(cursor.getColumnIndex("id_diagnostico")));
                ficha.setIdLugarInfeccion(cursor.getInt(cursor.getColumnIndex("id_lugar_infeccion")));
                ficha.setFechaInicioSintoma(cursor.getString(cursor.getColumnIndex("fecha_inicio_sintoma")));
                ficha.setSemanaEpidemiologica(cursor.getInt(cursor.getColumnIndex("semana_epidemiologica")));
                ficha.setFechaHospitalizacion(cursor.getString(cursor.getColumnIndex("fecha_hospitalizacion")));
                ficha.setFechaMuestraLaboratorio(cursor.getString(cursor.getColumnIndex("fecha_muestra_laboratorio")));
                fichas.add(ficha);
            } while (cursor.moveToNext());
        }*/

        cursor.close();
        db.close();
        return fichas;
    }
}
