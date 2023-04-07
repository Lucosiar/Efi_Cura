package com.example.a222;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.sql.Time;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String nombreBD = "login.db";

    public AdminSQLiteOpenHelper(Context context) {
        super(context, nombreBD , null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Usuarios
        db.execSQL("create table usuarios(usuario TEXT, contra TEXT, correo TEXT Primary key, cumple date)");

        //Medicaciones
        db.execSQL("create table medicacion(nombre text primary key, cantidadDiaria int, fechaIni text, " +
               "fechaFin text, duracion int, toma1 text, toma2 text, toma3 text, toma4 text, cantidadCaja int, formato TEXT, "
                + "notaComida TEXT, usuario TEXT)");

        //Medicos
        db.execSQL("create table medicos(id int Primary key, nombre TEXT, especialidad text, hospital text, numero String, correo text, usuario TEXT)");

        //Citas
        db.execSQL("create table citas(nombreMedico TEXT primary key, dia text, hora text, usuario TEXT)");
        //Servicio de la cita por ejemplo: medicina interna...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuarios");
        db.execSQL("drop table if exists medicacion");
        db.execSQL("drop table if exists medicos");
        db.execSQL("drop table if exists citas");
        onCreate(db);
    }


    //Introducir los datos en la base de datos.
    public boolean insertarDatos(String usuario, String contra, String correo, String cumple){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("usuario", usuario);
        values.put("contra", contra);
        values.put("correo", correo);
        values.put("cumple", cumple);

        long result = db.insert("usuarios", null, values);
        return result != -1;
    }

    //checkear el usuario en la base de datos
    //Se revisa el correo para que una persona solo tnega una cuenta
    public boolean checkUsu(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where correo = ? and contra = ?", new String []{correo});
        return cursor.getCount() > 0;
    }

    //checkear le usuario y la contraseña
    public boolean checkUsuContra(String usuario, String contra){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where usuario = ? and contra = ?", new String[]{usuario, contra});
        return cursor.getCount() > 0;
    }

    //introducir medico -- NO FUNCIONA NO LO USO --
    public boolean insertarMedicos(String nombre, String especialidad, String hospital, String numero, String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("especialidad", especialidad);
        values.put("hospital", hospital);
        values.put("numero", numero);
        values.put("correo", correo);

        long result = db.insert("medicos", null, values);
        return result != -1;

    }


    //intertar en la base de citas
    public void insertarCita(String nombreMedico, String dia, String hora, String usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombreMedico", nombreMedico);
        values.put("dia", dia);
        values.put("hora", hora);
        values.put("usuario", usuario);
        db.insert("citas", null, values);

        /*
        if(db != null){
            db.execSQL("insert into citas values ('" + nombreMedico + "', '"
            + dia + "', '" + hora + "')");*/

        db.close();

    }

    public void insertarMedicacion(String nombre, String cantidadDiaria, String fechaIni, String fechaFin, String duracion, String frecuencia,
                                   String toma1, String toma2, String toma3, String toma4, String cantidadCaja, String formato, String notaComida, String usuario){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("cantidadDiaria", cantidadDiaria);
        values.put("fechaIni", fechaIni);
        values.put("fechaFin", fechaFin);
        values.put("duracion", duracion);
        values.put("frecuencia", frecuencia);
        values.put("toma1", toma1);
        values.put("toma2", toma2);
        values.put("toma3", toma3);
        values.put("toma4", toma4);
        values.put("cantidadCaja", cantidadCaja);
        values.put("formato", formato);
        values.put("notaComida", notaComida);
        values.put("usuario", usuario);
        db.insert("medicacion", null, values);
        db.close();

    }

    //LO uso-- ES DE LOS MEDICOS
    public void insertarDatosVoid(String nombre, String especialidad, String hospital, String numero, String correo, String nombreU){
        SQLiteDatabase db = this.getWritableDatabase();
        int nuevoID = obtenerUltimo();
        String is = String.valueOf(nuevoID);

        if(db != null){
            //Complicaciones de la vida innecesarias
            //db.execSQL("insert into medicos values ('" + nombre + "', '" + especialidad + "', '" + hospital + "', '" +
                    //numero + "', " +"'" + correo + "," + nombreU + "'" +")");

            db.execSQL("insert into medicos values (?, ?, ?, ?, ?, ?, ?)", new String[]{is, nombre, especialidad, hospital, numero, correo, nombreU});
            db.close();
        }
    }

    public int obtenerUltimo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select MAX(id) from medicos", null);
        int id = 1;

        if(cursor.moveToFirst() && !cursor.isNull(0)){
            id = cursor.getInt(0) + 1;
        }
        return id;
    }

    //check medico
    public boolean checkMedico(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from medicos where nombre = ?", new String[]{nombre});
        return cursor.getCount() > 0;
    }

    public void insertarMedicacion(String nombre, int cantidadDiaria, Date fechaIni, Date fechaFin, int duracion, Time tiempo, int cantidadCaja){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String sfechaIni = String.valueOf(fechaIni);
        String sfechaFin = String.valueOf(fechaFin);
        String sTime = String.valueOf(tiempo);
        values.put("nombre", nombre);
        values.put("cantidadDiaria", cantidadDiaria);
        values.put("fechaIni", sfechaIni);
        values.put("fechaFin",sfechaFin);
        values.put("duracion", duracion);
        values.put("tiempo", sTime);
        values.put("cantidadCaja", cantidadCaja);

        db.insert("medicacion", null, values);
        db.close();
    }

}
