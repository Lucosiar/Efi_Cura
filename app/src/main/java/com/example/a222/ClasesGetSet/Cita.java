package com.example.a222.ClasesGetSet;

public class Cita {
    //Clase con los atributos de las citas
    public String nombreMedico, dia, hora;

    public Cita(String nombreMedico, String dia, String hora) {
        this.nombreMedico = nombreMedico;
        this.dia = dia;
        this.hora = hora;
    }

    public Cita() {
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
