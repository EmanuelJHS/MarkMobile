package com.example.markmobile.entidades;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.markmobile.mydatabase.MyBD;

import java.util.ArrayList;

public class Conexion {

    public Conexion() {
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //Funciones o Query

    public ArrayList<Integer> sp_getYears(){

        ArrayList<Integer> resultado = new ArrayList<>();

        Cursor dbCursor = null;
        //Conexion a la base de datos
        MyBD bd = new MyBD(context);
        SQLiteDatabase db = bd.getWritableDatabase(); //Abrir base de datos
        String query = "SELECT DISTINCT CAST (SUBSTR(FECHAHORA,1,4) AS NUMERIC) AS YEARS FROM MARCACION ORDER BY YEARS DESC;";
        //System.out.println("Query: "+ query);
        try {
            dbCursor = db.rawQuery(query,null);

            if (dbCursor != null)
                if (dbCursor.moveToFirst()){
                    do{
                        Integer s = dbCursor.getInt(0);
                        resultado.add(s);
                    }while (dbCursor.moveToNext());
                }

        }catch (Exception _ex){
            System.out.println("Error: Clase Conexion - sp_getYears");
            System.out.println(_ex.toString());
        }finally {
            db.close(); //Cerramos la base de datos.
        }


        return  resultado;
    }

    public ArrayList<Integer> sp_getMonths(int year){

        ArrayList<Integer> resultado = new ArrayList<>();

        Cursor dbCursor = null;
        //Conexion a la base de datos
        MyBD bd = new MyBD(context);
        SQLiteDatabase db = bd.getWritableDatabase(); //Abrir base de datos
        String query = "SELECT  CAST (SUBSTR(FECHAHORA,6,2) AS NUMERIC) AS MONTHS, CAST (SUBSTR(FECHAHORA,1,4) AS NUMERIC) AS YEARS " +
                "FROM MARCACION " +
                "WHERE YEARS = "+year+" "+
                "GROUP BY MONTHS " +
                "ORDER BY MONTHS ASC;";
        //System.out.println("Query: "+ query);
        try {
            dbCursor = db.rawQuery(query,null);

            if (dbCursor != null)
                if (dbCursor.moveToFirst()){
                    do{
                        Integer s = dbCursor.getInt(0);
                        resultado.add(s);
                    }while (dbCursor.moveToNext());
                }

        }catch (Exception _ex){
            System.out.println("Error: Clase Conexion - sp_getMonth");
            System.out.println(_ex.toString());
        }finally {
            db.close(); //Cerramos la base de datos.
        }


        return  resultado;
    }

}
