package com.example.markmobile.mydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.markmobile.utilities.Utilities;

public class MyBD extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MarkMobile.db";
   // private Context context;


    public MyBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.CREATE_TABLE_MARCACION);
        db.execSQL("INSERT INTO MARCACION VALUES (null,'SYSTEM','ENTRADA','2022-01-18 12:20:00','no_comment')");
        db.execSQL("INSERT INTO MARCACION VALUES (null,'SYSTEM','SALIDA','2022-01-18 22:09:00','no_comment')");
        db.execSQL("INSERT INTO MARCACION VALUES (null,'MANUAL','E_ALMUERZO','2021-04-18 22:09:00','no_comment')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilities.TABLE_MARCACION);

    }


}
