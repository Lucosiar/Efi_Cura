package com.example.a222.Ajustes_AcercaDe;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.R;
import com.example.a222.Registro_Login.InicioSesion;

public class AjustesActivity extends AppCompatActivity {

    Button cerrarSesion, bVolverAjustes;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String llave = "sesion";
    Switch sMedicacion, sRecargas, sCitas, sSintomas;

    TextView tvUsuario, tvCorreo;
    String usu;
    AdminSQLiteOpenHelper db;


    //Ajustes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        inicializar();

        //Mostrar nombre y correo
        preferences = getSharedPreferences("usuarios",Context.MODE_PRIVATE);
        usu = preferences.getString("nombre", "");
        tvUsuario.setText(usu);
        tvCorreo.setText("" + consultarCorreo(usu));

        tvCorreo.setTypeface(tvCorreo.getTypeface(), Typeface.ITALIC);

        cerrarSesion.setOnClickListener(v -> logout());

        bVolverAjustes.setOnClickListener(v -> {

            preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
            editor = preferences.edit();
            boolean sSintomasState = sSintomas.isChecked();
            boolean sCitasState = sCitas.isChecked();
            boolean sRecargasState = sRecargas.isChecked();
            boolean sMedicacionState = sMedicacion.isChecked();

            editor.putBoolean("sSintomas", sSintomasState);
            editor.putBoolean("sCitas", sCitasState);
            editor.putBoolean("sRecargas", sRecargasState);
            editor.putBoolean("sMedicacion", sMedicacionState);
            editor.apply();

            finish();
        });
    }

    //Cerrar sesion
    private void logout(){
        //Eliminamos los valores del sharedPreferences
        SharedPreferences preferences2 = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.apply();

        //Cambiamos el checkBox
        editor2.putBoolean(llave, false);
        editor2.apply();

        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, InicioSesion.class);
        startActivity(i);
        finish();

    }

    //Sacar el correo electronico de la base de datos
    private String consultarCorreo(String usuario){
        SQLiteDatabase base = db.getReadableDatabase();
        Cursor cursor = base.rawQuery("select correo from usuarios where usuario = ?", new String[]{usuario});

        String correo = "";
        int columIndex = cursor.getColumnIndex("correo");
        if(columIndex>=0 && cursor.moveToFirst()){
            correo = cursor.getString(columIndex);
        }
        cursor.close();
        return correo;
    }

    public void inicializar(){
        cerrarSesion = findViewById(R.id.cerrarSesion);
        bVolverAjustes = findViewById(R.id.bVolverAjustes);

        tvUsuario = findViewById(R.id.tvUsuario);
        tvCorreo = findViewById(R.id.tvCorreo);

        sSintomas = findViewById(R.id.sSintomas);
        sCitas = findViewById(R.id.sCitas);
        sRecargas = findViewById(R.id.sRecargas);
        sMedicacion = findViewById(R.id.sMedicacion);

        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        boolean sSintomaState = preferences.getBoolean("sSintomas", false);
        boolean sCitasState = preferences.getBoolean("sCitas", false);
        boolean sRecargasState = preferences.getBoolean("sRecargas", false);
        boolean sMedicacionState = preferences.getBoolean("sMedicacion", false);


        sSintomas.setChecked(sSintomaState);
        sCitas.setChecked(sCitasState);
        sRecargas.setChecked(sRecargasState);
        sMedicacion.setChecked(sMedicacionState);

        db = new AdminSQLiteOpenHelper(this);
    }
}