package com.example.a222.FormularioMedicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.a222.R;

public class FormularioDuracionActivity extends AppCompatActivity {

    NumberPicker tvNumberPicker;
    TextView tvMuestras;
    Button aceptarDias;

    Activity duracion;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_duracion);

        inicializar();

        tvNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) ->
                tvMuestras.setText(String.format("Quedan %s días para terminar el tratamiento", newVal)));



        aceptarDias.setOnClickListener(v -> {
            int valorSeleccionado = tvNumberPicker.getValue();
            String s = String.valueOf(valorSeleccionado);

            preferences = getSharedPreferences("datos", MODE_PRIVATE);
            editor = preferences.edit();

            editor.putString("duracionTratamiento", s);
            editor.apply();

            Intent a = new Intent(duracion, FormularioFinalActivity.class);
            startActivity(a);
        });
    }

    public void inicializar(){
        tvNumberPicker = findViewById(R.id.tvNumberPicker);
        tvNumberPicker.setMaxValue(100);
        tvNumberPicker.setMinValue(0);

        tvMuestras = findViewById(R.id.tvMuestras);
        aceptarDias = findViewById(R.id.aceptarDias);

        duracion = this;
    }
}