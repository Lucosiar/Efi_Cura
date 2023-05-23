package com.example.a222.ClasesGetSet;

public class Sintomas {

    private int id;
    private String tipo, hora, usuario, fecha;
    private int alta, baja, dolor;

    public Sintomas(){}

    public Sintomas(int id, String tipo, String hora, String fecha, int alta, int baja, int dolor, String usuario){
        this.id = id;
        this.tipo = tipo;
        this.hora = hora;
        this.fecha = fecha;
        this.alta = alta;
        this.baja = baja;
        this.dolor = dolor;
        this.usuario = usuario;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getHora() {return hora;}

    public void setHora(String hora) {this.hora = hora;}

    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario;}

    public int getAlta() {return alta;}

    public void setAlta(int alta) {this.alta = alta;}

    public int getBaja() {return baja;}

    public void setBaja(int baja) {this.baja = baja;}

    public int getDolor() {return dolor;}

    public void setDolor(int dolor) {this.dolor = dolor;}

    public String getFecha() {return fecha;}

    public void setFecha(String fecha) {this.fecha = fecha;}

    @Override
    public String toString() {
        return "Sintomas{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", hora='" + hora + '\'' +
                ", usuario='" + usuario + '\'' +
                ", fecha='" + fecha + '\'' +
                ", alta=" + alta +
                ", baja=" + baja +
                ", dolor=" + dolor +
                '}';
    }
}
