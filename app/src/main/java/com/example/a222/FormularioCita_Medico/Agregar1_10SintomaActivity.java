package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Agregar1_10SintomaActivity extends AppCompatActivity {

    Button bGuardarSintoma;
    TextView tvTipoSintoma, tvDiaHoraHoy, tvNivelDolor;
    SeekBar seekBarSintoma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_1_10_sintoma);


        //hay que escribir el tipo de sintoma

        seekBarSintoma.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvNivelDolor.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {/*NADA*/}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*NADA*/}
        });
        bGuardarSintoma.setOnClickListener(v -> {
            //Guardar datos del sintoma
        });
    }

    public void inicializar(){
        bGuardarSintoma = findViewById(R.id.bGuardarSintoma);

        tvTipoSintoma = findViewById(R.id.tvTipoSintoma);
        tvNivelDolor = findViewById(R.id.tvNivelDolor);

        tvDiaHoraHoy = findViewById(R.id.tvDiaHoraHoy);
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("es", "ES"));
        String fechaFormateada = "hoy, " + dateFormat.format(fechaActual);
        tvDiaHoraHoy.setText(fechaFormateada);


        seekBarSintoma = findViewById(R.id.seekBarSintoma);
    }
}