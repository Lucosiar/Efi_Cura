package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.R;

import java.util.ArrayList;
import java.util.List;

public class SintomasActivity extends AppCompatActivity {

    Activity sintomas;
    TextView tvSintoma,tvFrecuencia;
    Button bSiguienteHora;
    ListView listFrecuencia, listTipos;
    EditText etBusqueda;
    private final String [] frecuencia = {"Recordatorio diario", "Recordatorio semanal", "Recordatorio mensual"};
    private final String [] tipoSintomas = {"Dolor", "Migrañas", "Secreción nasal", "Picor", "Insomnio",
    "Peso", "Ataques de pánico", "Ataques de ansiedad", "Humor", "Vertigo", "Pulsaciones", "Temperatura corporal",
    "Dolor de garganta", "Erupciones", "Dolor lumbar", "Dolor de cabeza",   "Dolor en el pecho",
            "Dolor abdominal","Presión arterial"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);

        inicializar();

        ArrayAdapter<String>adapterFrecuencia = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, frecuencia);
        listFrecuencia.setAdapter(adapterFrecuencia);

        listFrecuencia.setOnItemClickListener((adaperView, view, i, l) -> {
            String frecuencia = "" + listFrecuencia.getItemAtPosition(i);
            tvFrecuencia.setText(frecuencia);

            SharedPreferences s = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            editor.putString("frecuenciaSintoma", tvFrecuencia.getText().toString());
            editor.apply();
        });

        ArrayAdapter<String>adaptadorTipoSintomas = new ArrayAdapter<>(sintomas, android.R.layout.simple_list_item_1, tipoSintomas);
        listTipos.setAdapter(adaptadorTipoSintomas);

        etBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarSintoma(s.toString());}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listTipos.setOnItemClickListener((adapterView, view, i, l) -> {
            String sintomas = "" + listTipos.getItemAtPosition(i);
            tvSintoma.setText(sintomas);

            SharedPreferences s = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            editor.putString("tipoSintoma", tvSintoma.getText().toString());
            editor.apply();
        });


        bSiguienteHora.setOnClickListener(v -> {
            if(validar()){
                String frecuencia = tvFrecuencia.getText().toString();
                if(frecuencia.equals("Recordatorio diario") || frecuencia.equals("Recordatorio mensual")){
                    Intent i = new Intent(sintomas, HoraSintomasActivity.class);
                    startActivity(i);
                }else if(frecuencia.equals("Recordatorio semanal")){
                    Intent i = new Intent(sintomas, SintomasSemanalActivity.class);
                    startActivity(i);
                }
            }else{
                Toast.makeText(sintomas, "Campos requeridos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void filtrarSintoma(String buscador){
        List<String> sintomasFiltrados = new ArrayList<>();

        for(String tipo : tipoSintomas){
            if(tipo.toLowerCase().contains(buscador.toLowerCase())){
                sintomasFiltrados.add(tipo);
            }
        }

       ArrayAdapter<String> adaptadorSintomas = new ArrayAdapter<>(sintomas, android.R.layout.simple_list_item_1, sintomasFiltrados);
        listTipos.setAdapter(adaptadorSintomas);
    }

    private boolean validar(){
        boolean retu = true;
        String sintoma = tvSintoma.getText().toString();
        String frecuencia = tvFrecuencia.getText().toString();

        if(sintoma.isEmpty()){
            tvSintoma.setError("");
            retu =  false;
        }
        if(frecuencia.isEmpty()){
            tvFrecuencia.setError("");
            retu = false;
        }
        return retu;
    }


    public void inicializar(){
        bSiguienteHora = findViewById(R.id.bSiguienteHora);
        listFrecuencia = findViewById(R.id.listFrecuencia);
        listTipos = findViewById(R.id.listTipos);
        tvSintoma = findViewById(R.id.tvSintoma);
        etBusqueda = findViewById(R.id.etBusqueda);
        tvFrecuencia = findViewById(R.id.tvFrecuencia);
        this.setTitle("Añadir síntomas");
        sintomas = this;
    }

    //desplazamiento
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}