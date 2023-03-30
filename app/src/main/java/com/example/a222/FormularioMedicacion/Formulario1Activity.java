package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

public class Formulario1Activity extends AppCompatActivity {

    TextView medicacion, saltar;
    Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        inicializar();

        //hacer que al clicar en el tv cambie a la pantalla principal
        saltar.setOnClickListener(v -> {
            Intent i = new Intent(fa, PaginaPrincipal.class);
            startActivity(i);
        });
    }


    //continuamos con el formulario
    public void siguiente(View view) {
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("nombre", medicacion.getText().toString());
        editor.commit();
        if (validar() == false) {
            Toast.makeText(Formulario1Activity.this, "Escriba un medicamento", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, Formulario2Activity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    //Validar que los campos esten rellenos
    public boolean validar() {
        boolean ret = true;
        String errorMedicamento = medicacion.getText().toString();
        //Warning en cada campo.
        if (errorMedicamento.isEmpty()) {
            medicacion.setError("Este campo no puede quedar vacío");
            ret = false;
        }
        return ret;
    }

    private void inicializar() {
        //Activity bar
        this.setTitle("Añadir medicamento");

        saltar = findViewById(R.id.saltar);
        medicacion = findViewById(R.id.tvMedicacion);
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        medicacion.setText(preferences.getString("nombre", ""));

        //inicializamos el activity
        fa = this;

        //subrayar la linea
        saltar.setPaintFlags(saltar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

}