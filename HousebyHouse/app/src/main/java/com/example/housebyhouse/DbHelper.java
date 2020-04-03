package com.example.housebyhouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    String tabla2="Create Table Propietario(IdPropietario Integer primary key,Nombre Text,Telefono Text,Password Text,Viviendas Integer)";
    String tabla="Create Table UnidadHabitacional(IdUnidad Integer primary key,Tipo Text,Precio REAL,Direccion Text,FechaRecepcion Text,EstaArrendada Text,IdPropietario Integer, FOREIGN KEY(\"IdPropietario\") REFERENCES \"Propietario\"(\"IdPropietario\"))";
    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
        db.execSQL(tabla2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table UnidadHabitacional");
        db.execSQL(tabla);
        db.execSQL("drop table Propietario");
        db.execSQL(tabla2);
    }
}
