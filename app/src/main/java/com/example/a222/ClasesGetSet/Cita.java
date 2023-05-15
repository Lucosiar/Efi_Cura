package com.example.a222.ClasesGetSet;

public class Cita {
    //Clase con los atributos de las citas
    public String nombreMedico, dia, hora, usuario;
    public int id;

    public Cita(int id, String nombreMedico, String dia, String hora, String usuario) {
        this.id = id;
        this.nombreMedico = nombreMedico;
        this.dia = dia;
        this.hora = hora;
        this.usuario = usuario;
    }

    public Cita() {}

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

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

    public String getUsuario(){return usuario;}

    public void setUsuario(){this.usuario = usuario;}

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", nombreMedico='" + nombreMedico + '\'' +
                ", dia='" + dia + '\'' +
                ", hora='" + hora + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}

