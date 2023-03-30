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

    private final String [] frecuencia = {"Una vez al día", "Dos veces al día", "Tres veces al día",
            "Cuatro veces al día", "Cada 6 horas", "Cuando sea necesario"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario4);

        inicializarVariables();

        //Sacamos los valores del sharedPreferences
        SharedPreferences s = getSharedPreferences("datos", MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String formato = s.getString("forma", "");
        String cantidad = s.getString("cantidadDiaria", "");

        //Mostrar por pantalla medicacion, cantidad y formato
        int canInt = Integer.parseInt(cantidad);
        if(canInt >= 2){
            tvMedicaMasCanti.setText(nombre + ", " + cantidad + " " + formato + "s");
        }else {
            tvMedicaMasCanti.setText(nombre + ", " + cantidad + " " + formato);
        }



        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, frecuencia);
        listFrecuencia.setAdapter(adapter);

        listFrecuencia.setOnItemClickListener((adapterView, view, i, l) -> {
            //Guardo la frecuencia con la que toma el medicamento en una varible
            tvResFrecu.setText("" + listFrecuencia.getItemAtPosition(i));

            switch (tvResFrecu.getText().toString()){
                case "Una vez al día":

                        Intent a = new Intent(f4a, FormularioHora.class);
                        startActivity(a);
                    break;

                case "Dos veces al día":

                    break;

                case "Tres veces al día":

                    break;

                case "Cuatro veces al día":

                    break;

                case "Cada 6 horas":

                    break;

                case "Cuando sea necesario":
                    Intent csn = new Intent(f4a, FormularioFinalActivity.class);
                    startActivity(csn);
                    break;

            }


        });

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