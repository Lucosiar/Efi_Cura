package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.R;

public class Formulario3Activity extends AppCompatActivity {

    TextView tvMedicacion, tvFormato, tvMostrar, tvCantidad, tvRespuesta;
    ListView list;
    private final String [] opciones = {"Si", "No"};
    Activity siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario3);

        //Activity bar
        this.setTitle("Añadir medicamento");

        //metodo
        iniciarVariables();

        //Lista de opciones
        list = findViewById(R.id.list1);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opciones);
        list.setAdapter(adapter);


        list.setOnItemClickListener((adapterView, view, i, l) -> {
            //Guardo la respuesta del list view
            //Si el usu le da a el si, se guarda un String = "Si" en el tvRespuesta
            tvRespuesta.setText("" + list.getItemAtPosition(i));

            if(!validar()){
                Toast.makeText(Formulario3Activity.this, "Complete el campo.", Toast.LENGTH_SHORT).show();
            }else {
                //guardar cantidad en shared
                SharedPreferences preferences = getSharedPreferences("datos", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("cantidadDiaria", tvCantidad.getText().toString());
                editor.commit();

                switch ((tvRespuesta.getText().toString())) {
                    case "Si":
                        Intent s = new Intent(siguiente, Formulario4Activity.class);
                        startActivity(s);
                        break;
                    case "No":
                        Intent v = new Intent(siguiente, Formulario5Activity.class);
                        startActivity(v);
                        break;
                }
            }
        });


        //Mostrar medicacion y formato
        SharedPreferences s = getSharedPreferences("datos", MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String format = s.getString("forma", "");
        tvMedicacion.setText(nombre);
        tvFormato.setText(format);


        switch(tvFormato.getText().toString()){
            case "Gota":
                tvMostrar.setText("Gota(s)");
                break;
            case "Comprimido":
                tvMostrar.setText("Comprimido(s)");
                break;
            case "Cuchara":
                tvMostrar.setText("Cuchara(s)");
                break;
            case "Gramo":
                tvMostrar.setText("Gramo(s)");
                break;
            case "Inhalacion":
                tvMostrar.setText("Inhalacion(es)");
                break;
            case "Parche":
                tvMostrar.setText("Parche(s)");
                break;
            case "Sobre":
                tvMostrar.setText("Sobre(s)");
                break;
            case "Unidad":
                tvMostrar.setText("Unidad(es)");
                break;
        }
    }

    //Validacion del campo vacio
    public boolean validar(){
        boolean ret = true;
        String errorCantidad = tvCantidad.getText().toString();
        if(errorCantidad.isEmpty()){
            tvCantidad.setError("Complete el campo");
            ret = false;
        }
        return ret;
    }

    public void iniciarVariables(){
        tvMedicacion = findViewById(R.id.tvMedicacion3);
        tvFormato= findViewById(R.id.tvFormato3);
        tvMostrar = findViewById(R.id.tvMostrar);
        tvCantidad = findViewById(R.id.tvCantidad);
        tvRespuesta = findViewById(R.id.tvRespuesta);
        siguiente = this;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}