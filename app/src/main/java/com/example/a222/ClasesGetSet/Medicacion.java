package com.example.a222.ClasesGetSet;

import java.util.Date;

public class Medicacion {
    //Clase con los atributos de las medicaciones

    public String nombre, toma1, toma2, toma3, toma4, formato, notaComida;
    public String cantidadDiaria, duracion, cantidadCaja, frecuencia, diasTomas;
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

    public String getToma1() {return toma1;}

    public void setToma1(String toma1) {this.toma1 = toma1;}

    public String getFormato() {return formato;}

    public void setFormato(String formato) {this.formato = formato;}

    public String getNotaComida() {return notaComida;}

    public void setNotaComida(String notaComida) {this.notaComida = notaComida;}

    public String getFrecuencia() {return frecuencia;}

    public void setFrecuencia(String frecuencia) {this.frecuencia = frecuencia;}

    public String getDiasTomas() {return diasTomas;}

    public void setDiasTomas(String diasTomas) {this.diasTomas = diasTomas;}

    public String getToma2() {return toma2;}

    public void setToma2(String toma2) {this.toma2 = toma2;}

    public String getToma3() {return toma3;}

    public void setToma3(String toma3) {this.toma3 = toma3;}

    public String getToma4() {return toma4;}

    public void setToma4(String toma4) {this.toma4 = toma4;}

    @Override
    public String toString() {
        return "Medicacion{" +
                "nombre='" + nombre + '\'' +
                ", toma1='" + toma1 + '\'' +
                ", formato='" + formato + '\'' +
                ", notaComida='" + notaComida + '\'' +
                ", cantidadDiaria='" + cantidadDiaria + '\'' +
                ", duracion='" + duracion + '\'' +
                ", cantidadCaja='" + cantidadCaja + '\'' +
                ", frecuencia='" + frecuencia + '\'' +
                ", diasTomas='" + diasTomas + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", fechaIni='" + fechaIni + '\'' +
                '}';
    }
}


