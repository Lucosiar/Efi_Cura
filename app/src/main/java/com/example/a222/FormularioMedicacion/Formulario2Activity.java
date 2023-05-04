package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.R;

public class Formulario2Activity extends AppCompatActivity {

    TextView medicacion, tvForma;
    ListView list;
    private final String [] formatos = {"Comprimido", "Cuchara", "Gota",
    "Gramo", "Inhalacion", "Parche", "Sobre", "Unidad"};

    Activity x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario2);

        //Activity bar
        this.setTitle("Añadir medicamento");

        tvForma = findViewById(R.id.tvForma);

        medicacion = findViewById(R.id.tvMedicacionMostrar);
        SharedPreferences s = getSharedPreferences("datos", MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        medicacion.setText(nombre);

        list = findViewById(R.id.list1);
        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formatos);
        list.setAdapter(adapter);

        x=this;
        list.setOnItemClickListener((adapterView, view, i, l) -> {

            String texto = "" + list.getItemAtPosition(i);
            tvForma.setText(texto);

            SharedPreferences s1 = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = s1.edit();
            ed.putString("forma", tvForma.getText().toString());
            ed.apply();

            Intent v = new Intent(x, Formulario3Activity.class);
            startActivity(v);
        });
    }

    //desplazamiento
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}