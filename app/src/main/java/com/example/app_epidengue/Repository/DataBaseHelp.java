package com.example.app_epidengue.Repository;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DataBaseHelp extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dengue.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PACIENTE = "CREATE TABLE paciente (" +
                "DNI TEXT PRIMARY KEY, " +
                "NombreCompleto TEXT, " +
                "Edad INTEGER, " +
                "Sexo TEXT, " +
                "NroTelefono INTEGER)";

        String CREATE_TABLE_FICHA_PACIENTE = "CREATE TABLE ficha_paciente (" +
                "id_ficha INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_paciente TEXT, " +
                "Historia_clinica INTEGER, " +
                "establecimiento_de_salud TEXT, " +
                "id_diagnostico INTEGER, " +
                "id_lugar_infeccion INTEGER, " +
                "fecha_inicio_sintoma TEXT, " +
                "semana_epidemiologica INTEGER, " +
                "fecha_hospitalizacion TEXT, " +
                "fecha_muestra_laboratorio TEXT, " +
                "FOREIGN KEY(id_paciente) REFERENCES paciente(DNI), " +
                "FOREIGN KEY(id_diagnostico) REFERENCES diagnostico(id_diagnostico), " +
                "FOREIGN KEY(id_lugar_infeccion) REFERENCES lugar_infeccion(id_lugar_infeccion))";

        String CREATE_TABLE_DIAGNOSTICO = "CREATE TABLE diagnostico (" +
                "id_diagnostico INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre_diagnostico TEXT, " +
                "tipo_de_diagnostico TEXT, " +
                "fiebre REAL, " +
                "sintomas TEXT, " +
                "lugar_P_infeccion TEXT)";

        String CREATE_TABLE_LUGAR_INFECCION = "CREATE TABLE lugar_infeccion (" +
                "id_lugar_infeccion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Direccion_PI TEXT, " +
                "Departamento TEXT, " +
                "Provincia TEXT, " +
                "Distrito TEXT, " +
                "latitud REAL, " +
                "longitud REAL)";

        db.execSQL(CREATE_TABLE_PACIENTE);
        db.execSQL(CREATE_TABLE_FICHA_PACIENTE);
        db.execSQL(CREATE_TABLE_DIAGNOSTICO);
        db.execSQL(CREATE_TABLE_LUGAR_INFECCION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ficha_paciente");
        db.execSQL("DROP TABLE IF EXISTS paciente");
        db.execSQL("DROP TABLE IF EXISTS diagnostico");
        db.execSQL("DROP TABLE IF EXISTS lugar_infeccion");
        onCreate(db);
    }
}
