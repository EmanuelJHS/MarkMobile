package com.example.markmobile.entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.markmobile.mydatabase.MyBD;
import com.example.markmobile.ui.gallery.VistaTabla;
import com.example.markmobile.utilities.Utilities;

import java.util.ArrayList;

public class Marcacion {
    private String id = null;
    private String tipo_marcacion;
    private String marcada;
    private String fechahora;
    private String comentario;
    private Context context; //Importante para obtener el contexto de la aplicacion, que no las pase del activity.

    public Marcacion (){} //Constructor para invocar la clase.

    public Marcacion(String tipo_marcacion, String marcada, String fechahora, String comentario) {
        this.tipo_marcacion = tipo_marcacion;
        this.marcada = marcada;
        this.fechahora = fechahora;
        this.comentario = comentario;
    }

    //Setter and Getter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_marcacion() {
        return tipo_marcacion;
    }

    public void setTipo_marcacion(String tipo_marcacion) {
        this.tipo_marcacion = tipo_marcacion;
    }

    public String getMarcada() {
        return marcada;
    }

    public void setMarcada(String marcada) {
        this.marcada = marcada;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //Storage Procedure///////////////////////////////
    //Insert
    public boolean sp_insert(){
        boolean resultado = false;

        //Set Valores
        ContentValues cv = new ContentValues();
        cv.put(Utilities.CAMPO_ID, id);
        cv.put(Utilities.CAMPO_TIPO_MARCACION, tipo_marcacion);
        cv.put(Utilities.CAMPO_MARCADA, marcada);
        cv.put(Utilities.CAMPO_FECHAHORA, fechahora);
        cv.put(Utilities.CAMPO_COMENTARIO, comentario);

        //Conexion a la base de datos
        MyBD bd = new MyBD(context);
        SQLiteDatabase db = bd.getWritableDatabase(); //Abrir base de datos
        try {

            if(db.insert(Utilities.TABLE_MARCACION,null,cv)>0)
                resultado = true;

        }catch (Exception _ex){
            System.out.println("Error: Clase Marcacion - sp_insert");
            System.out.println(_ex.toString());
        }finally {
            db.close(); //Cerramos la base de datos.
        }
        System.out.println("tipo de marcada: " + tipo_marcacion +", marcada: "+ marcada +", fechahora: "+fechahora+", comentario: "+ comentario);
        return resultado;
    }
    //Consultar

    //Obtener marcaciones de un mes
    public ArrayList<Marcacion> sp_Marcaciones_get(int yyyy, int MM){
        ArrayList<Marcacion> m = new ArrayList<>();

        //Meses menores a 10, se concatenan un cero por delante.
        String mes = "0";
        String mesNext = "0";
        if(MM<10){
            mes += String.valueOf(MM); // Se concatena un cero
            mesNext += String.valueOf(MM+1);
        }else{
            mes = String.valueOf(MM); // Se transforma a String
            mesNext = String.valueOf(MM+1);
        };

        Cursor dbCursor = null;
        //Conexion a la base de datos
        MyBD bd = new MyBD(context);
        SQLiteDatabase db = bd.getWritableDatabase(); //Abrir base de datos
        String query = "SELECT * FROM MARCACION " +
                "WHERE DATE(FECHAHORA) >= DATE('"+yyyy+"-"+mes+"-01 00:00:00') " +
                "AND  DATE(FECHAHORA) < DATE('"+yyyy+"-"+mesNext+"-01 00:00:00') " +
                "ORDER BY DATE(FECHAHORA) ASC;";
        //System.out.println("Query: "+ query);
        try {
            dbCursor = db.rawQuery(query,null);

            if (dbCursor != null)
                if (dbCursor.moveToFirst()){
                    do{
                        String _id = dbCursor.getString(0).toString();
                        String _tipo_marcacion = dbCursor.getString(1).toString();
                        String _marcada = dbCursor.getString(2).toString();
                        String _fechahora = dbCursor.getString(3).toString();
                        String _comentario = dbCursor.getString(4).toString();

                        m.add(new Marcacion(_tipo_marcacion,_marcada,_fechahora,_comentario));

                    }while (dbCursor.moveToNext());
                }

        }catch (Exception _ex){
            System.out.println("Error: Clase Marcacion - sp_Marcaciones_get");
            System.out.println(_ex.toString());
        }finally {
            db.close(); //Cerramos la base de datos.
        }

        return  m;
    }

    //Cantidad de marcaciones del dia
    public int sp_cantidad_marcaciones_del_dia(String fecha) {
        int resultado =0;

        Cursor dbCursor = null;
        //Conexion a la base de datos
        MyBD bd = new MyBD(context);
        SQLiteDatabase db = bd.getWritableDatabase(); //Abrir base de datos
        try {
            dbCursor = db.rawQuery("SELECT COUNT(*) " +
                    "FROM MARCACION " +
                    "WHERE DATE(FECHAHORA) = DATE('"+ fecha +"');",null);
            if (dbCursor != null)
                if (dbCursor.moveToFirst()){
                    do{
                        resultado = Integer.parseInt(dbCursor.getString(0)); //Columna 0
                    }while (dbCursor.moveToNext());
                }
        }catch (Exception _ex){
            System.out.println("Error: Clase Marcacion - sp_cantidad_marcaciones_del_dia");
            System.out.println(_ex.toString());
        }finally {
            db.close(); //Cerramos la base de datos.
        }

        //System.out.println(" Clase Marcacion - sp_cantidad_marcaciones_del_dia: " + resultado);
        return resultado;
    }

    //Cantidad de marcaciones del dia
    public String sp_ultima_marcacion_del_dia(String fecha) {
        String resultado = "";

        Cursor dbCursor = null;
        //Conexion a la base de datos
        MyBD bd = new MyBD(context);
        SQLiteDatabase db = bd.getWritableDatabase(); //Abrir base de datos
        try {
            dbCursor = db.rawQuery("SELECT * " +
                    "FROM MARCACION " +
                    "WHERE DATE(FECHAHORA) = '"+fecha+"'" +
                    "ORDER BY TIME(FECHAHORA) DESC " +
                    "LIMIT 1;",null);

            if (dbCursor != null)
                if (dbCursor.moveToFirst()){
                    do{
                        resultado = dbCursor.getString(0).toString(); //Columna 2 Entra, Almuerzo, Salida
                    }while (dbCursor.moveToNext());
                }
        }catch (Exception _ex){
            System.out.println("Error: Clase Marcacion - sp_cantidad_marcaciones_del_dia");
            System.out.println(_ex.toString());
        }finally {
            db.close(); //Cerramos la base de datos.
        }

        System.out.println(" Clase Marcacion - sp_cantidad_marcaciones_del_dia: " + resultado);
        return resultado;
    }

    public Marcacion sp_ultima_marcacion() {
        Marcacion marcacion  = new Marcacion();

        Cursor dbCursor = null;
        //Conexion a la base de datos
        MyBD bd = new MyBD(context);
        SQLiteDatabase db = bd.getWritableDatabase(); //Abrir base de datos
        try {
            dbCursor = db.rawQuery("SELECT * " +
                    "FROM MARCACION " +
                    "ORDER BY DATETIME(FECHAHORA) DESC " +
                    "LIMIT 1;",null);

            if (dbCursor != null)
                if (dbCursor.moveToFirst()){
                    do{
                        marcacion.setId(dbCursor.getString(0).toString()); //Columna 2 Entra, Almuerzo, Salida
                        marcacion.setTipo_marcacion(dbCursor.getString(1).toString());
                        marcacion.setMarcada(dbCursor.getString(2).toString());
                        marcacion.setFechahora(dbCursor.getString(3).toString());
                        marcacion.setComentario(dbCursor.getString(4).toString());
                    }while (dbCursor.moveToNext());
                }
        }catch (Exception _ex){
            System.out.println("Error: Clase Marcacion - sp_ultima_marcacion");
            System.out.println(_ex.toString());
        }finally {
            db.close(); //Cerramos la base de datos.
        }

        //System.out.println(" Clase Marcacion - sp_cantidad_marcaciones_del_dia: " + resultado);
        return marcacion;
    }

    //Storage Procedure///////////////////////////////
}
