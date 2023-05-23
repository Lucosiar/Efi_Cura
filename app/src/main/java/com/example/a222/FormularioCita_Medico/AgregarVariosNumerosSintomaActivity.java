package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AgregarVariosNumerosSintomaActivity extends AppCompatActivity {

    TextView tvTipoSintomaVarios, tvDiaHoyVarios, tvMedida2, tvMedida, tvAlta, tvBaja;
    EditText etAlta, etBaja;
    Button bGuardarSintomaVarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_varios_numeros_sintoma);

        inicializar();

        bGuardarSintomaVarios.setOnClickListener(v -> {
            //Meter guardar sintoma
        });
    }

    public void inicializar(){
        bGuardarSintomaVarios = findViewById(R.id.bGuardarSintomaNumero);

        etAlta = findViewById(R.id.etNumero);
        etBaja = findViewById(R.id.etBaja);

        tvTipoSintomaVarios = findViewById(R.id.tvTipoSintomaNumero);
        tvMedida2 = findViewById(R.id.tvMedida2);
        tvMedida = findViewById(R.id.tvNumeroMedida);
        tvAlta = findViewById(R.id.tvNumero);
        tvBaja = findViewById(R.id.tvBaja);

        tvDiaHoyVarios = findViewById(R.id.tvDiaHoyNumero);
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("es", "ES"));
        String fechaFormateada = "hoy, " + dateFormat.format(fechaActual);
        tvDiaHoyVarios.setText(fechaFormateada);
    }
}