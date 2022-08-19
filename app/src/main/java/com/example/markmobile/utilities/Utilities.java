package com.example.markmobile.utilities;

public class Utilities {

    //Tablas Marcaciones
    public static final String TABLE_MARCACION = "MARCACION";
    //Campos marcaciones
    public static final String CAMPO_ID = "ID";
    public static final String CAMPO_TIPO_MARCACION = "TIPO_MARCACION";
    public static final String CAMPO_FECHAHORA = "FECHAHORA";
    public static final String CAMPO_MARCADA = "MARCADA";
    public static final String CAMPO_COMENTARIO = "COMENTARIO";


    //CREANDO TABLAS
    public static final String CREATE_TABLE_MARCACION = "CREATE TABLE " + TABLE_MARCACION +" ( "+
           CAMPO_ID+" integer primary key autoincrement, " +
           CAMPO_TIPO_MARCACION +" text not null, " +
           CAMPO_MARCADA +" text not null, " +
           CAMPO_FECHAHORA +" text not null, " +
           CAMPO_COMENTARIO +" text not null)";

}
