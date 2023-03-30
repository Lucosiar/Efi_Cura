package com.example.a222.ClasesGetSet;

import java.util.Date;

public class Medicacion {
    //Clase con los atributos de las medicaciones

    public String nombre;
    public int cantidadDiaria, duracion, cantidadCaja;
    public Date fechaFin, fechaIni;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadDiaria() {
        return cantidadDiaria;
    }

    public void setCantidadDiaria(int cantidadDiaria) {
        this.cantidadDiaria = cantidadDiaria;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getCantidadCaja() {
        return cantidadCaja;
    }

    public void setCantidadCaja(int cantidadCaja) {
        this.cantidadCaja = cantidadCaja;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }
}
