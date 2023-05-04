package com.example.a222.FormularioMedicacion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.R;

public class Formulario4Activity extends AppCompatActivity {

    TextView tvPregunta, tvResFrecu, tvMedicaMasCanti;
    ListView listFrecuencia;
    Activity f4a;
    SharedPreferences s;
    SharedPreferences.Editor editor;

    private final String [] frecuencia = {"Una vez al día", "Dos veces al día", "Tres veces al día",
            "Cuatro veces al día"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario4);

        inicializarVariables();

        //Sacamos los valores del sharedPreferences
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
            //Guardo la frecuencia con la que toma el medicamento en una varible
            tvResFrecu.setText(mostrar);

            switch (tvResFrecu.getText().toString()){
                case "Una vez al día":
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("frecu", "Una vez al día");
                    editor.commit();
                    cambiarPantalla();
                    break;

                case "Dos veces al día": //Cada 12 horas
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("frecu", "Dos veces al día");
                    editor.commit();
                    cambiarPantalla();
                    break;

                case "Tres veces al día": //Cada 8 horas
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("frecu", "Tres veces al día");
                    editor.commit();
                    cambiarPantalla();
                    break;

                case "Cuatro veces al día": //4 veces al día es lo mismo que cada 6 horas
                    s = getSharedPreferences("datos", MODE_PRIVATE);
                    editor = s.edit();
                    editor.putString("frecu", "Cuatro veces al día");
                    editor.commit();
                    cambiarPantalla();
                    break;
            }
        });
    }

    private void cambiarPantalla(){
        Intent a = new Intent(f4a, FormularioHora.class);
        startActivity(a);
        finish();
    }
    //Cambiar cuando terminee
    public void inicializarVariables(){
        tvPregunta = findViewById(R.id.tvPregunta);
        listFrecuencia = findViewById(R.id.listFrecuencia);
        tvResFrecu = findViewById(R.id.tvResFrecu);
        tvMedicaMasCanti = findViewById(R.id.tvMedicaMasCanti);
        f4a = this;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}