package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.a222.FormularioMedicacion.FormularioActivityComida;
import com.example.a222.FormularioMedicacion.FormularioInicioMActivity;
import com.example.a222.FormularioMedicacion.FormularioRecargasActivity;
import com.example.a222.R;

public class EditarMedicacionActivity extends AppCompatActivity {

    EditText etNombreMedicamento;
    Switch sTomas, sRecarga;
    TextView tvTratamiento, tvInstrucciones, tvRecarga;

    Button bAceptarEdiccion, bCancelarEdiccion;
    Bundle bun;
    String medicinaSeleccionada;
    Activity editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_medicacion);

        inicializar();
        cambiarPantalla();


        bAceptarEdiccion.setOnClickListener(v -> {
            //HOLA
        });

        bCancelarEdiccion.setOnClickListener(v -> {
            finish();
        });
    }

/*    public void ponerCheck(){
        if(fechaIni.isEmpty()){
            tvTratamiento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_calendar_month, 0, 0, 0);
        }else{
            tvTratamiento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_calendar_month, 0, R.drawable.ic_baseline_check, 0);
        }

        if(cvCuando.getText().toString().isEmpty()){
            tvInstrucciones.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_info_24, 0, 0, 0);
        }else{
            tvInstrucciones.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_info_24, 0, R.drawable.ic_baseline_check, 0);
        }
    }*/

    private void cambiarPantalla(){
            //Instrucciones
            tvInstrucciones.setOnClickListener(v -> {
                Intent ins = new Intent(editar, FormularioActivityComida.class);
                startActivity(ins);
            });

            //Recargas
            tvRecarga.setOnClickListener(v ->{
                Intent rs = new Intent(editar, FormularioRecargasActivity.class);
                startActivity(rs);
            });

            //Duracion tratamiento
            tvTratamiento.setOnClickListener(v -> {
                Intent tt = new Intent(editar, FormularioInicioMActivity.class);
                startActivity(tt);
            });

    }

    public void inicializar(){
        etNombreMedicamento = findViewById(R.id.etNombreMedicamento);
        tvTratamiento = findViewById(R.id.tvTratamiento);
        tvInstrucciones = findViewById(R.id.tvInstrucciones);
        tvRecarga = findViewById(R.id.tvRecarga);
        sTomas = findViewById(R.id.sTomas);
        sRecarga = findViewById(R.id.sRecarga);
        bCancelarEdiccion = findViewById(R.id.bCancelarEdiccion);
        bAceptarEdiccion = findViewById(R.id.bAceptarEdiccion);

        if(bun != null){
            medicinaSeleccionada = (String) bun.get("nombre");
            etNombreMedicamento.setText(medicinaSeleccionada);
        }

        editar = this;

    }
}