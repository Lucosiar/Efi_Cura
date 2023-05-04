package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.R;

import java.io.IOException;

public class Formulario5Activity extends AppCompatActivity {

    ListView listFrecuencia;
    TextView tvMedicaMasCanti, tvResFrecu;
    Activity f5a;
    SharedPreferences s;
    SharedPreferences.Editor editor;

    private final String [] frecuencia = {"Una vez a la semana", "Cada 2 días", "2 días a la semana", "3 días a la semana",
        "Cada 28 horas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario5);

        inicializar();
        escribirValor();

        s = getSharedPreferences("datos", MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String formato = s.getString("forma", "");
        String cantidad = s.getString("cantidadDiaria", "");

        String mostrarSingular = nombre + ", " + cantidad + " " + formato + "s";
        String mostrarPlural = mostrarSingular + "s";
        //Mostrar por pantalla medicacion, cantidad y formato
        int canInt = Integer.parseInt(cantidad);
        if(canInt >= 2){
            tvMedicaMasCanti.setText(mostrarPlural);
        }else {
            tvMedicaMasCanti.setText(mostrarSingular);
        }

        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, frecuencia);
        listFrecuencia.setAdapter(adapter);

        listFrecuencia.setOnItemClickListener((adapterView, view, i, l) -> {
            String mostrar = "" + listFrecuencia.getItemAtPosition(i);
            //Guardar respuesta de la frecuencia en un tv
            tvResFrecu.setText(mostrar);

            switch (tvResFrecu.getText().toString()){
                case "Una vez a la semana":
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("dia", "Una vez a la semana");
                    editor.commit();
                    cambiarPantalla();
                    break;

                case "Cada 2 días":
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("dia", "Cada 2 días");
                    editor.commit();
                    cambiarPantalla();
                    break;

                case "2 días a la semana":
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("dia", "2 días a la semana");
                    editor.commit();
                    cambiarPantalla();
                    break;

                case "3 días a la semana":
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("dia", "3 días a la semana");
                    editor.commit();
                    cambiarPantalla();
                    break;

                case "Cada 28 horas":
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("dia", "Cada 28 horas");
                    editor.commit();
                    cambiarPantalla();
                    break;
            }
        });
    }

    private void cambiarPantalla(){
        Intent i = new Intent(f5a, FormularioElegirDiasActivity.class);
        startActivity(i);
        finish();
    }


    public void inicializar(){
        listFrecuencia = findViewById(R.id.listFrecuencia);
        tvMedicaMasCanti = findViewById(R.id.tvMedicaMasCanti);
        tvResFrecu = findViewById(R.id.tvResFrecu);
        f5a = this;
    }

    public void escribirValor(){
        //Sacamos los valores del sharedPreferences
        SharedPreferences s = getSharedPreferences("datos", MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String formato = s.getString("forma", "");
        String cantidad = s.getString("cantidadDiaria", "");

        String mostrarSingular = nombre + ", " + cantidad + " " + formato + "s";
        String mostrarPlural = mostrarSingular + "s";

        //Mostrar por pantalla medicacion, cantidad y formato
        int canInt = Integer.parseInt(cantidad);
        if(canInt >= 2){
            tvMedicaMasCanti.setText(mostrarPlural);
        }else {
            tvMedicaMasCanti.setText(mostrarSingular);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}