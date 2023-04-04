package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;


import com.example.a222.R;

import java.util.Calendar;

public class FormularioHora extends AppCompatActivity {

    TextView tvPreguntaHora, tvMedicacionHora, tvCantidadHora, tvMostrarHora;
    Button selHora, buttonSiguiente;
    private int hora, min;
    Activity horas;
    SharedPreferences s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_hora);

        inicializar();

        mostrarPantalla();

        selHora.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            hora = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(horas, ((view, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                tvMostrarHora.setText(hourOfDay + ":" + minutesST);
            }), hora, min, true);
            time.show();
        });



        buttonSiguiente.setOnClickListener(v -> {
            int i = 1;
            String hora = tvMostrarHora.getText().toString();

            s = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            editor.putString(("hora" + i), hora);
            editor.commit();
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

        //Mostrar por pantalla medicacion, cantidad y formato
        int canInt = Integer.parseInt(cantidad);
        tvMedicacionHora.setText(nombre);
        if(canInt >= 2){
            tvCantidadHora.setText("Tomar " + cantidad + " " + formato + "s");
        }else{
            tvCantidadHora.setText("Tomar " + cantidad + " " + formato);
        }
    }

    public void inicializar(){
        tvMedicacionHora = findViewById(R.id.tvMedicacionHora);
        tvPreguntaHora = findViewById(R.id.tvPreguntaHora);
        tvCantidadHora = findViewById(R.id.tvCantidadHora);
        selHora = findViewById(R.id.selDiaComienzo);
        tvMostrarHora = findViewById(R.id.tvMostrarDiaComienzo);
        buttonSiguiente = findViewById(R.id.buttonSiguiente);
        horas = this;
        tvMostrarHora.setText("15:00");
    }
}