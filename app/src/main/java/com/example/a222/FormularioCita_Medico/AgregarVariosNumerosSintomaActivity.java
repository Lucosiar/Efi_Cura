package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AgregarVariosNumerosSintomaActivity extends AppCompatActivity {

    TextView tvTipoSintomaVarios, tvDiaHoyVarios, tvMedida2, tvMedida;
    EditText etAlta, etBaja;
    Button bGuardarSintomaVarios;

    SharedPreferences s;
    String tipoSintoma, usuario;
    AdminSQLiteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_varios_numeros_sintoma);

        inicializar();

        s = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        usuario = s.getString("nombre", "");

        bGuardarSintomaVarios.setOnClickListener(v -> guardarSintoma());
    }

    private void guardarSintoma(){
        String fechaActual = obtenerFecha();
        String horaActual = obtenerHora();
        String dolor = "";
        String alta = String.valueOf(etAlta.getText());
        String baja = String.valueOf(etBaja.getText());
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
        bGuardarSintomaVarios = findViewById(R.id.bGuardarSintomaNumero);

        etAlta = findViewById(R.id.etNumero);
        etBaja = findViewById(R.id.etBaja);

        Intent intent = getIntent();
        tipoSintoma = intent.getStringExtra("tipo");

        tvTipoSintomaVarios = findViewById(R.id.tvTipoSintomaNumero);
        tvTipoSintomaVarios.setText(tipoSintoma);
        tvMedida2 = findViewById(R.id.tvMedida2);
        tvMedida = findViewById(R.id.tvNumeroMedida);
        tvMedida2.setText("Diastólica (baja)");
        tvMedida.setText("Sistólica (alta)");

        tvDiaHoyVarios = findViewById(R.id.tvDiaHoyNumero);
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("es", "ES"));
        String fechaFormateada = "hoy, " + dateFormat.format(fechaActual);
        tvDiaHoyVarios.setText(fechaFormateada);

        db = new AdminSQLiteOpenHelper(this);
    }
}