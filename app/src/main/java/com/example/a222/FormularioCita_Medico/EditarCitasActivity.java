package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a222.R;

public class EditarCitasActivity extends AppCompatActivity {

    TextView tvNombreMedico, tvHospital, tvEspecialidad, tvHoraDia;
    EditText etNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_citas);

        inicializar();


    }

    public void inicializar(){
        tvNombreMedico = findViewById(R.id.tvNombreMedico);
        tvHospital = findViewById(R.id.tvHospital);
        tvEspecialidad = findViewById(R.id.tvEspecialidad);
        tvHoraDia = findViewById(R.id.tvHoraDia);
        etNotas = findViewById(R.id.etNotas);
    }
}