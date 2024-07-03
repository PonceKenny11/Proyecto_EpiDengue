package com.example.app_epidengue.Repository;

import android.content.Context;

public class UbicacionDB {
    private DataBaseHelp dbHelp;

    public UbicacionDB(Context context){
        dbHelp = new DataBaseHelp(context);
    }
}
