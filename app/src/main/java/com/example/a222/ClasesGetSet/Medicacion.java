package com.example.a222.ClasesGetSet;

import java.util.Date;

public class Medicacion {
    //Clase con los atributos de las medicaciones

    public String nombre;
    public String cantidadDiaria, duracion, cantidadCaja;
    public String fechaFin, fechaIni;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidadDiaria() {
        return cantidadDiaria;
    }

    public void setCantidadDiaria(String cantidadDiaria) {
        this.cantidadDiaria = cantidadDiaria;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCantidadCaja() {
        return cantidadCaja;
    }

    public void setCantidadCaja(String cantidadCaja) {
        this.cantidadCaja = cantidadCaja;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }
}
