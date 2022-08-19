package com.example.markmobile.ui.gallery;

import android.widget.TextView;

public class VistaTabla {

    private TextView day;
    private TextView entrada;
    private TextView e_almuerzo;
    private TextView s_almuerzo;
    private TextView salida;

    public VistaTabla() {
    }

    public VistaTabla(TextView day, TextView entrada, TextView e_almuerzo, TextView s_almuerzo, TextView salida) {
        this.day = day;
        this.entrada = entrada;
        this.e_almuerzo = e_almuerzo;
        this.s_almuerzo = s_almuerzo;
        this.salida = salida;
    }

    public TextView getDay() {
        return day;
    }

    public void setDay(TextView day) {
        this.day = day;
    }

    public TextView getEntrada() {
        return entrada;
    }

    public void setEntrada(TextView entrada) {
        this.entrada = entrada;
    }

    public TextView getE_almuerzo() {
        return e_almuerzo;
    }

    public void setE_almuerzo(TextView e_almuerzo) {
        this.e_almuerzo = e_almuerzo;
    }

    public TextView getS_almuerzo() {
        return s_almuerzo;
    }

    public void setS_almuerzo(TextView s_almuerzo) {
        this.s_almuerzo = s_almuerzo;
    }

    public TextView getSalida() {
        return salida;
    }

    public void setSalida(TextView salida) {
        this.salida = salida;
    }
}
