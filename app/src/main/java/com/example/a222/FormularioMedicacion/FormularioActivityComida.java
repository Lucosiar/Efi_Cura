package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.R;

public class FormularioActivityComida extends AppCompatActivity {

    ListView listComida;
    TextView tvRespuestaComida;
    Activity fc;

    private final String [] opciones = {"Antes de comer", "A la hora de comer", "Despues de comer", "No importa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_comida);

        inicializar();

        //Adaptador del spinner
        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opciones);
        listComida.setAdapter(adapter);

        //SharedPreferences
        SharedPreferences preferences = getSharedPreferences("datos", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //on click
        listComida.setOnItemClickListener((adapterView, view, i, l) -> {

            tvRespuestaComida.setText("" + listComida.getItemAtPosition(i));
            String respuesta = tvRespuestaComida.getText().toString();

            editor.putString("notaComida", respuesta);
            editor.commit();

            switch (respuesta){
                case "Antes de comer":  case "Despues de comer": case "No importa": case "A la hora de comer":
                    Intent ii = new Intent(fc, FormularioFinalActivity.class);
                    startActivity(ii);
                    break;
            }
        });

    }


    public void inicializar(){
        listComida = findViewById(R.id.listComida);
        tvRespuestaComida = findViewById(R.id.tvRespuestaComida);
        fc = this;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}