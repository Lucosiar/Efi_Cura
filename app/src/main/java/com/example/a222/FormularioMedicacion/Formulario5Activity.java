package com.example.a222.FormularioMedicacion;

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

    private final String [] frecuencia = {"Una vez a la semana", "Cada 2 días", "2 días a la semana", "3 días a la semana",
        "Cada 28 horas", "Según sea necesario"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario5);

        inicializar();
        escribirValor();

        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, frecuencia);
        listFrecuencia.setAdapter(adapter);

        listFrecuencia.setOnItemClickListener((adapterView, view, i, l) -> {
            //Guardar respuesta de la frecuencia en un tv
            tvResFrecu.setText("" + listFrecuencia.getItemAtPosition(i));

            switch (tvResFrecu.getText().toString()){
                case "Una vez a la semana":
                    break;
                case "Cada 2 días":
                    break;
                case "2 días a la semana":
                    break;
                case "3 días a la semana":
                    break;
                case "Cada 28 horas":
                    break;
                case "Según sea necesario":
                    break;

            }

        });

    }


    public void inicializar(){
        listFrecuencia = findViewById(R.id.listFrecuencia);
        tvMedicaMasCanti = findViewById(R.id.tvMedicaMasCanti);
        tvResFrecu = findViewById(R.id.tvResFrecu);
    }

    public void escribirValor(){
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
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}