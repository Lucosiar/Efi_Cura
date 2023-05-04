package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;


import com.example.a222.R;

import java.util.Calendar;

public class FormularioHora extends AppCompatActivity {

    TextView tvPreguntaHora, tvPregunta3, tvPregunta2, tvPregunta4,  tvMedicacionHora,
            tvCantidadHora, tvHora1, tvHora2, tvHora3, tvHora4;
    Button selHora1, selHora2, selHora3, selHora4, buttonSiguiente;
    private int hora, min;
    Activity horas;
    SharedPreferences s;
    LinearLayout tomas2, tomas3, tomas4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_hora);

        inicializar();

        mostrarPantalla();

        selHora1.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            hora = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(horas, ((view, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                tvHora1.setText(hourOfDay + ":" + minutesST);
            }), hora, min, true);
            time.show();
        });

        selHora2.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            hora = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(horas, ((view, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                tvHora2.setText(hourOfDay + ":" + minutesST);
            }), hora, min, true);
            time.show();
        });

        selHora3.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            hora = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(horas, ((view, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                tvHora3.setText(hourOfDay + ":" + minutesST);
            }), hora, min, true);
            time.show();
        });

        selHora4.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            hora = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(horas, ((view, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                tvHora4.setText(hourOfDay + ":" + minutesST);
            }), hora, min, true);
            time.show();
        });

        buttonSiguiente.setOnClickListener(v -> {
            String hora1 = tvHora1.getText().toString();
            String hora2 = tvHora2.getText().toString();
            String hora3 = tvHora3.getText().toString();
            String hora4 = tvHora4.getText().toString();

            s = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            if(hora2.equals("--") && hora3.equals("--") && hora4.equals("--")){
                editor.putString(("hora1"), hora1);
            }if(hora3.equals("--") && hora4.equals("--")){
                editor.putString(("hora1"), hora1);
                editor.putString(("hora2"), hora2);
            }if(hora4.equals("--")){
                editor.putString(("hora1"), hora1);
                editor.putString(("hora2"), hora2);
                editor.putString(("hora3"), hora3);
            }else{
                editor.putString(("hora1"), hora1);
                editor.putString(("hora2"), hora2);
                editor.putString(("hora3"), hora3);
                editor.putString(("hora4"), hora4);
            }
            editor.apply();
            Intent fa = new Intent(horas, FormularioFinalActivity.class);
            startActivity(fa);
        });
    }


    public void mostrarPantalla(){
        //Sacamos los valores del sharedPreferences
        s = getSharedPreferences("datos", MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String formato = s.getString("forma", "");
        String cantidad = s.getString("cantidadDiaria", "");
        String frecu = s.getString("frecu", "");

        String varias_pastillas = "Tomar " + cantidad + " " + formato + "s";
        String una_pastilla = "Tomar " + cantidad + " " + formato;

        //Mostrar por pantalla medicacion, cantidad y formato
        int canInt = Integer.parseInt(cantidad);
        tvMedicacionHora.setText(nombre);
        if(canInt >= 2){
            tvCantidadHora.setText(varias_pastillas);
        }else{
            tvCantidadHora.setText(una_pastilla);
        }

        if(frecu.equals("Una vez al día")){
            tvPregunta2.setVisibility(TextView.GONE);
            tvPregunta3.setVisibility(TextView.GONE);
            tvPregunta4.setVisibility(TextView.GONE);
            tomas2.setVisibility(TextView.GONE);
            tomas3.setVisibility(TextView.GONE);
            tomas4.setVisibility(TextView.GONE);
            tvHora2.setText("--");
            tvHora3.setText("--");
            tvHora4.setText("--");
        }if(frecu.equals("Dos veces al día")){
            tvPregunta3.setVisibility(TextView.GONE);
            tvPregunta4.setVisibility(TextView.GONE);
            tomas3.setVisibility(TextView.GONE);
            tomas4.setVisibility(TextView.GONE);
            tvHora3.setText("--");
            tvHora4.setText("--");
        }if(frecu.equals("Tres veces al día")){
            tvPregunta4.setVisibility(TextView.GONE);
            tomas4.setVisibility(TextView.GONE);
            tvHora4.setText("--");
        }
    }

    public void inicializar(){
        tvMedicacionHora = findViewById(R.id.tvMedicacionHora);
        tvPreguntaHora = findViewById(R.id.tvPreguntaHora);
        tvPregunta3 = findViewById(R.id.tvPregunta3);
        tvPregunta2 = findViewById(R.id.tvPregunta2);
        tvPregunta4 = findViewById(R.id.tvPregunta4);
        tvCantidadHora = findViewById(R.id.tvCantidadHora);
        selHora1 = findViewById(R.id.selHora1);
        selHora2 = findViewById(R.id.selHora2);
        selHora3 = findViewById(R.id.selHora3);
        selHora4 = findViewById(R.id.selHora4);
        tvHora1 = findViewById(R.id.tvHora1);
        tvHora2 = findViewById(R.id.tvHora2);
        tvHora3 = findViewById(R.id.tvHora3);
        tvHora4 = findViewById(R.id.tvHora4);
        buttonSiguiente = findViewById(R.id.buttonSiguiente);
        horas = this;

        tvHora1.setText("6:00");
        tvHora2.setText("12:00");
        tvHora3.setText("18:00");
        tvHora4.setText("00:00");

        tomas2 = findViewById(R.id.tomas2);
        tomas3 = findViewById(R.id.tomas3);
        tomas4 = findViewById(R.id.tomas4);
    }
}