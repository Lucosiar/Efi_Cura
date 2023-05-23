package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

import java.util.Calendar;

public class SintomasSemanalActivity extends AppCompatActivity {

    private final String [] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    ListView listaDias;
    TextView tvDiaSemana, tvHoraSintomaSemanal;
    Button bHoraSemanal, bAceptarHoraSemanal;
    SharedPreferences sa;
    SharedPreferences s;
    SharedPreferences.Editor editor;
    AdminSQLiteOpenHelper db;
    Activity sintomaSemanal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_semanal);

        inicializar();
        ArrayAdapter<String> adaptadorDias = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diasSemana);
        listaDias.setAdapter(adaptadorDias);

        listaDias.setOnItemClickListener((parent, view, i, l) -> {
            String diaElegido = "" + listaDias.getItemAtPosition(i);
            tvDiaSemana.setText(diaElegido);

            s = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            editor.putString("diaSemanaSintoma", tvDiaSemana.getText().toString());
            editor.apply();
        });

        bHoraSemanal.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hora = c.get(Calendar.HOUR_OF_DAY);
            int minutos = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(sintomaSemanal, ((view1, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                String mostrarTexto = hourOfDay + ":" + minutesST;
                tvHoraSintomaSemanal.setText(mostrarTexto);
            }), hora, minutos, true);
            time.show();
        });

        //Sacamos los datos de sharedpreference
        sa = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String usuario = sa.getString("nombre", "");

        s = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String frecuencia = s.getString("frecuenciaSintoma", "");
        String tipo = s.getString("tipoSintoma", "");
        editor = s.edit();

        bAceptarHoraSemanal.setOnClickListener(v -> {
            String horaSintomaString = tvHoraSintomaSemanal.getText().toString();

            String alta = ""; String baja = ""; String dolor = "";
            String usu = consultarCorreo(usuario);
            String fecha = tvDiaSemana.getText().toString();

            db.insertarSintomas(tipo, horaSintomaString, fecha, alta, baja, dolor, usu);
            Toast.makeText(sintomaSemanal, "Síntoma guardado", Toast.LENGTH_SHORT).show();
            borrarSP();
            Intent i = new Intent(sintomaSemanal, PaginaPrincipal.class);
            startActivity(i);
        });
    }

    private void borrarSP(){
        s = getSharedPreferences("datos", MODE_PRIVATE);
        editor = s.edit();
        editor.clear();
        editor.apply();
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
        listaDias = findViewById(R.id.listaDias);
        tvDiaSemana = findViewById(R.id.tvDiaSemana);
        tvHoraSintomaSemanal = findViewById(R.id.tvHoraSintomaSemanal);

        bHoraSemanal = findViewById(R.id.bHoraSemanal);
        bAceptarHoraSemanal = findViewById(R.id.bAceptarHoraSemanal);
        sintomaSemanal = this;
        db = new AdminSQLiteOpenHelper(sintomaSemanal);
    }
}