package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class Agregar1_10SintomaActivity extends AppCompatActivity {

    Button bGuardarSintoma;
    TextView tvTipoSintoma, tvDiaHoraHoy, tvNivelDolor;
    SeekBar seekBarSintoma;

    SharedPreferences s;
    String tipoSintoma, usuario;
    AdminSQLiteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_1_10_sintoma);

        inicializar();

        Intent intent = getIntent();
        tipoSintoma = intent.getStringExtra("tipo");

        s = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        usuario = s.getString("nombre", "");

        //hay que escribir el tipo de sintoma
        seekBarSintoma.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvNivelDolor.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {/*NADA*/}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*NADA*/}
        });

        bGuardarSintoma.setOnClickListener(v -> guardarSintoma());
    }

    private void guardarSintoma(){
        String fechaActual = obtenerFecha();
        String horaActual = obtenerHora();
        String alta = "";
        String baja = "";
        String dolor = String.valueOf(tvNivelDolor.getText());

        String usu = consultarCorreo(usuario);

        db.insertarSintomas(tipoSintoma, horaActual, fechaActual, alta, baja, dolor, usu);

        volver();
    }

    private void volver(){
        finish();
        Toast.makeText(this, "Síntoma registrado", Toast.LENGTH_SHORT).show();
    }
    private static String obtenerFecha(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);
    }

    private static String obtenerHora(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);
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
        bGuardarSintoma = findViewById(R.id.bGuardarSintoma);

        tvTipoSintoma = findViewById(R.id.tvTipoSintoma);
        tvNivelDolor = findViewById(R.id.tvNivelDolor);

        tvDiaHoraHoy = findViewById(R.id.tvDiaHoraHoy);
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("es", "ES"));
        String fechaFormateada = "hoy, " + dateFormat.format(fechaActual);
        tvDiaHoraHoy.setText(fechaFormateada);

        seekBarSintoma = findViewById(R.id.seekBarSintoma);
        seekBarSintoma.setProgress(0);
        seekBarSintoma.setMax(10);

        db = new AdminSQLiteOpenHelper(this);
    }
}