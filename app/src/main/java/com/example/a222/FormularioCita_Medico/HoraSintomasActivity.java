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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.FragmentosPP.SintomasFragment;
import com.example.a222.R;

import java.util.Calendar;

public class HoraSintomasActivity extends AppCompatActivity {

    TextView tvHoraSintoma, tvDiaMes, tvExplicacion;
    Button bHoraSintoma, botonAceptar;
    NumberPicker tvNumberPicker;
    Activity horaSintomas;
    SharedPreferences s;
    SharedPreferences sa;
    SharedPreferences.Editor editor;
    AdminSQLiteOpenHelper db;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hora_sintomas);

        inicializar();

        //Sacamos los datos que ya estan en el shared
        sa = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String usuario = sa.getString("nombre", "");
        s = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String frecuencia = s.getString("frecuenciaSintoma", "");
        String tipo = s.getString("tipoSintoma", "");
        editor = s.edit();

        //Hacemos desapareces lo relevante al recordatorio mensual
        if(frecuencia.equals("Recordatorio diario")){
            tvDiaMes.setVisibility(View.GONE);
            tvNumberPicker.setVisibility(View.GONE);
            tvExplicacion.setVisibility(View.GONE);
            fecha = "*";
        }

        bHoraSintoma.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hora = c.get(Calendar.HOUR_OF_DAY);
            int minutos = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(horaSintomas, ((view1, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                String mostrarTexto = hourOfDay + ":" + minutesST;
                tvHoraSintoma.setText(mostrarTexto);
            }), hora, minutos, true);
            time.show();

        });

        botonAceptar.setOnClickListener(v -> {
            //hora en la que te pregunta el sintoma
            String horaSintomaString = tvHoraSintoma.getText().toString();

            String alta = ""; String baja = ""; String dolor = "";
            String usu = consultarCorreo(usuario);

            if(frecuencia.equals("Recordatorio mensual")){
                int diaMes = tvNumberPicker.getValue();
                editor = s.edit();
                editor.putInt("diaMesSintoma", diaMes);
                editor.apply();
                fecha = String.valueOf(diaMes);
            }

            db.insertarSintomas(tipo, horaSintomaString, fecha, alta, baja, dolor, usu);
            Toast.makeText(horaSintomas, "Síntoma guardado", Toast.LENGTH_SHORT).show();
            borrarSP();
            Intent i = new Intent(horaSintomas, PaginaPrincipal.class);
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
        bHoraSintoma = findViewById(R.id.bHoraSintoma);
        botonAceptar = findViewById(R.id.botonAceptar);
        tvNumberPicker = findViewById(R.id.tvNumberPicker);
        tvDiaMes = findViewById(R.id.tvDiaMes);
        tvExplicacion = findViewById(R.id.tvExplicacion);
        tvHoraSintoma = findViewById(R.id.tvHoraSintoma);

        tvNumberPicker.setMaxValue(31);
        tvNumberPicker.setMinValue(0);

        horaSintomas = this;
        db = new AdminSQLiteOpenHelper(horaSintomas);
    }
}