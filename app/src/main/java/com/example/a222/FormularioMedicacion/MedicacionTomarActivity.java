package com.example.a222.FormularioMedicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.R;

public class MedicacionTomarActivity extends AppCompatActivity {

    TextView tvMedicacionAhora, tvCantidadAhora, tvFormatoAhora, tvInstruccionAhora, tvHoraAhora, tvRecordatorio;
    Button bNoMedicacion, bTomarMedicacion, bVolverPagPrinci;

    int cantidadPastillas, cantidadDiaria;

    String usuario, usu;
    SharedPreferences s;
    SharedPreferences.Editor editor;
    String medicacion, recordatorio, hora1, hora2, hora3, hora4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacion_tomar);

        inicializar();

        s = getSharedPreferences("datos", Context.MODE_PRIVATE);
        usuario = s.getString("nombre", "");
        usu = consultarCorreo(usuario);


        Intent intent = getIntent();
        medicacion = intent.getStringExtra("nombreMedicacion");
        recordatorio = intent.getStringExtra("recordatorio");
        hora1 = intent.getStringExtra("hora1");
        hora2 = intent.getStringExtra("hora2");
        hora3 = intent.getStringExtra("hora3");
        hora4 = intent.getStringExtra("hora4");

        bVolverPagPrinci.setOnClickListener(v -> finish());

        bNoMedicacion.setOnClickListener(v -> {
            //tomado == false
        });

        bTomarMedicacion.setOnClickListener(v -> {
            sacarCantidad();
            sacarCantidadDiaria();
            cantidadPastillas = cantidadPastillas - cantidadDiaria;
            guardarCantidad(cantidadPastillas);
        });
    }

    private void guardarCantidad(int cantidadPastillas){
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase sql = db.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("cantidadCaja", cantidadPastillas);

        int numRows = sql.update("medicacion", values, null, null);

        if (numRows == 0) {
            sql.insert("medicacion", null, values);
        }
        sql.close();
    }

    private int sacarCantidad(){
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor cursor = sql.rawQuery("Select cantidadCaja from medicacion where usuario = ? and medicacion = ?", new String[]{usu, medicacion});

        cantidadPastillas = 0;

        if(cursor.moveToFirst()){
            cantidadPastillas = cursor.getInt(10);
        }

        cursor.close();
        sql.close();

        return cantidadPastillas;
    }

    private int sacarCantidadDiaria(){
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor cursor = sql.rawQuery("select cantidadDiaria from medicacion where usuario = ? and medicacion = ?", new String[]{usu, medicacion});

        cantidadDiaria = 0;

        if(cursor.moveToFirst()){
            cantidadDiaria = cursor.getInt(1);
        }

        cursor.close();
        sql.close();

        return cantidadDiaria;
    }

    private String consultarCorreo(String usuario){
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(this);
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
        bNoMedicacion = findViewById(R.id.bNoMedicacion);
        bTomarMedicacion = findViewById(R.id.bTomarMedicacion);
        bVolverPagPrinci = findViewById(R.id.bVolverPagPrinci);

        tvMedicacionAhora = findViewById(R.id.tvMedicacionAhora);
        tvCantidadAhora = findViewById(R.id.tvCantidadAhora);
        tvFormatoAhora = findViewById(R.id.tvFormatoAhora);
        tvInstruccionAhora = findViewById(R.id.tvInstruccionAhora);
        tvHoraAhora = findViewById(R.id.tvHoraAhora);
        tvRecordatorio = findViewById(R.id.tvRecordatorio);
    }
}