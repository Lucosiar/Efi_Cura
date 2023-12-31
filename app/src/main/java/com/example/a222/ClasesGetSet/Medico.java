package com.example.a222.ClasesGetSet;

public class Medico {

    //Clase con los atributos de los medicos
    private int id;
    private String nombre;
    private String especialidad;
    private String hospital;
    private String numero;
    private String correo;

    public Medico(int id, String nombre, String especialidad, String hospital, String numero, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.hospital = hospital;
        this.numero = numero;
        this.correo = correo;
    }

    public Medico(){

    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHospital() {return hospital;}

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
