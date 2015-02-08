package com.example.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table positions(position_at DATETIME DEFAULT CURRENT_TIMESTAMP primary key, latitud integer,longitud integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists posicions");
        db.execSQL("create table positions(position_at DATETIME DEFAULT CURRENT_TIMESTAMP primary key, latitud integer,longitud integer)");        
    }    
}