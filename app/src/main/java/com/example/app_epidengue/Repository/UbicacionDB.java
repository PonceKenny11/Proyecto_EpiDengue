package com.example.app_epidengue.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UbicacionDB {
    private DataBaseHelp dbHelp;
    private static final String TAG = "UbicacionDB";

    public UbicacionDB(Context context) {
        dbHelp = new DataBaseHelp(context);
    }

    public boolean insertLugarInfeccion(String direccion, String departamento, String provincia, String distrito, double latitud, double longitud) {
        SQLiteDatabase db = this.dbHelp.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Direccion_PI", direccion);
        contentValues.put("Departamento", departamento);
        contentValues.put("Provincia", provincia);
        contentValues.put("Distrito", distrito);
        contentValues.put("latitud", latitud);
        contentValues.put("longitud", longitud);
        long result = -1;
        try {
            result = db.insert("lugar_infeccion", null, contentValues);
        } catch (Exception e) {
            Log.e("UbicacionDB", "Error inserting lugar_infeccion: " + e.getMessage());
        }
        return result != -1;
    }
}
