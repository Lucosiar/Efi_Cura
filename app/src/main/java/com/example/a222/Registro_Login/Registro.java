package com.example.a222.Registro_Login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FormularioMedicacion.Formulario1Activity;
import com.example.a222.R;

import java.util.Calendar;

public class Registro extends AppCompatActivity {

    TextView usuario, contrasena, correo, repetir, cumple;
    AdminSQLiteOpenHelper db;

    Activity registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializar();

    }

    public void escogerCumple(View view){
        final Calendar c = Calendar.getInstance();
        //Fecha establecida para que salga como predeterminada en el calendario
        c.set(2000, 0, 1);

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(registro, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cumple.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        }, ano, mes, dia);
        //Establecemos la fecha que hemos elegido antes como preestablecida
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        //Establecemos la fecha máxima permitida por la fecha actual del dispositivo
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    //Boton retroceso
    public void volver(View view) {
        Intent i = new Intent(this, InicioSesion.class);
        startActivity(i);
    }

    //Registro de los usuarios
    public void registro(View view) {
        String usu = usuario.getText().toString().trim();
        String con = contrasena.getText().toString().trim();
        String repe = repetir.getText().toString().trim();
        String corr = correo.getText().toString().trim();
        String fech = cumple.getText().toString();

        //Si campos vacios
        if (!validar()) {
            Toast.makeText(Registro.this, "Campos requeridos", Toast.LENGTH_SHORT).show();
        }else{
            if(con.equals(repe)){
                boolean check = db.checkUsu(usu);
                if(!check){
                    boolean insert = db.insertarDatos(usu, con, corr, fech);
                    if(insert){
                        Toast.makeText(Registro.this, "Registro completo", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, Formulario1Activity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(Registro.this, "Registro fallido, el correo ya está usado", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Registro.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Registro.this, "Error, contraseñas no iguales", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void probar(View view){
        Intent o = new Intent(this, Formulario1Activity.class);
        startActivity(o);
    }




    //validacion de si estan vacios los campos, con warnings
    public boolean validar(){
        boolean ret = true;
        String errorUsuario = usuario.getText().toString();
        String errorContra = contrasena.getText().toString();
        String errorCorreo = correo.getText().toString();
        String errorRepetir = repetir.getText().toString();
        String errorCumple = cumple.getText().toString();

        //Warning en cada campo.
        if(errorUsuario.isEmpty()){
            usuario.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        if(errorCumple.isEmpty()){
            cumple.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        if(errorContra.isEmpty()){
            contrasena.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        if(errorRepetir.isEmpty()){
            repetir.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        if(errorCorreo.isEmpty()){
            correo.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        return ret;
    }

    private void inicializar(){
        usuario = findViewById(R.id.idUsuarioRegistro);
        contrasena = findViewById(R.id.idContrasenaRegistro);
        correo = findViewById(R.id.idCorreoRegistro);
        repetir = findViewById(R.id.idRepetir);
        cumple = findViewById(R.id.idFechaNac);
        db = new AdminSQLiteOpenHelper(this);
        registro = this;
    }
}