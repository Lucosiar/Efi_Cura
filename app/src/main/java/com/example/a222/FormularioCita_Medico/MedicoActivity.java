package com.example.a222.FormularioCita_Medico;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.R;


public class MedicoActivity extends AppCompatActivity {

    //Activity para crear un nuevo médico
    TextView tvNombreM;
    TextView tvEspecialidadM;
    TextView tvHospitalM;
    TextView tvTelefonoM;
    TextView tvCorreoM;
    AdminSQLiteOpenHelper db;
    Button button3;
    Activity esta;

    String usu;
    SharedPreferences preferences;

    CitaActivity citas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);

        inicializar();

        button3.setOnClickListener(v -> {
            String nombre = tvNombreM.getText().toString();
            String especialidad = tvEspecialidadM.getText().toString();
            String hospital = tvHospitalM.getText().toString();
            String numero = tvTelefonoM.getText().toString();
            String correo = tvCorreoM.getText().toString();
            String usuario;

            if (!validar()) {
                Toast.makeText(MedicoActivity.this, "Faltan campos", Toast.LENGTH_SHORT).show();
            }else{
                preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
                usu = preferences.getString("nombre", "");
                usuario = consultarCorreo(usu);

                db.insertarDatosVoid(nombre, especialidad, hospital, numero, correo, usuario);
                Toast.makeText(MedicoActivity.this, "Registro completo", Toast.LENGTH_SHORT).show();
                MedicoActivity.super.onBackPressed();
            }
        });
    }

    //Metodo de validacion de campos vacios
    public boolean validar() {
        boolean ret = true;
        String errorEspecialidad = tvEspecialidadM.getText().toString();
        String errorHospital = tvHospitalM.getText().toString();

        if(errorEspecialidad.isEmpty()){
            tvEspecialidadM.setError("Este campo no puede quedar vacío.");
            ret = false;
        }
        if(errorHospital.isEmpty()){
            tvHospitalM.setError("Este campo no puede quedar vacío.");
            ret = false;
        }
        return ret;
    }

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
        //Activity bar
        this.setTitle("Añadir médico");
        esta = this;

        //Datos del médico
        tvNombreM = findViewById(R.id.tvNombreM);
        tvEspecialidadM = findViewById(R.id.tvEspecialidadM);
        tvHospitalM = findViewById(R.id.tvHospitalM);
        tvTelefonoM = findViewById(R.id.tvTelefonoM);
        tvCorreoM = findViewById(R.id.tvCorreoM);
        db = new AdminSQLiteOpenHelper(this);

        button3 = findViewById(R.id.button3);
    }


}