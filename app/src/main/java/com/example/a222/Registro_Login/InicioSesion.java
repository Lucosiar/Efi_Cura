package com.example.a222.Registro_Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;


public class InicioSesion extends AppCompatActivity {

    TextView usuario, contra;
    Button inicio, registro;
    AdminSQLiteOpenHelper db;
    CheckBox cbIniSesion;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String llave = "sesion";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Lo inicializamos todo
        inicializar();

        if (revisarSesion()){
            //Cambiar de intent
            Intent i = new Intent(getApplicationContext(), PaginaPrincipal.class);
            startActivity(i);
            Toast.makeText(this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debe iniciar sesion", Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarSesion(boolean checked){
        editor.putBoolean(llave, checked);

        //preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        //editor = preferences.edit();
        editor.putString("nombre", usuario.getText().toString());
        editor.putString("contra", contra.getText().toString());

        editor.apply();
    }

    public boolean revisarSesion(){
        return this.preferences.getBoolean(llave, false);
    }


    //Boton para iniciar sesion (Se deberia de cambiar)
    public void iniciar(View view) {
        String usu = usuario.getText().toString().trim();
        String con = contra.getText().toString().trim();

        //Descomentar cuando haya finalizado la programacion

        if (validar() == false) {
            Toast.makeText(InicioSesion.this, "Faltan campos", Toast.LENGTH_SHORT).show();
        }else{
            Boolean checkusucontra = db.checkUsuContra(usu, con);
            if(checkusucontra == true){
                Toast.makeText(InicioSesion.this, "Login completado", Toast.LENGTH_SHORT).show();
                guardarSesion(cbIniSesion.isChecked());
                Intent i = new Intent(getApplicationContext(), PaginaPrincipal.class);
                startActivity(i);
            }else{
                Toast.makeText(InicioSesion.this, "Login erroneo", Toast.LENGTH_SHORT).show();
            }
        }
        //  Intent i = new Intent(this, PaginaPrincipal.class);
        //startActivity(i);
    }

    //Boton para pasar a la pantalla de registro
    public void registrar(View view) {
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    //Validar que los campos esten rellenos
    public boolean validar() {
        boolean ret = true;
        String errorUsuario = usuario.getText().toString();
        String errorContra = contra.getText().toString();
        //Warning en cada campo.
        if (errorUsuario.isEmpty()) {
            usuario.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        if (errorContra.isEmpty()) {
            contra.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        return ret;
    }

    public void inicializar(){
        usuario = findViewById(R.id.idUsuario);
        contra = findViewById(R.id.idContrasena);
        inicio = findViewById(R.id.b1InicioSesion);
        registro = findViewById(R.id.b2Registro);
        cbIniSesion = findViewById(R.id.cbIniSesion);
        db = new AdminSQLiteOpenHelper(this);

        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
}