package com.example.a222.FormularioMedicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a222.R;

public class MedicacionTomarActivity extends AppCompatActivity {

    TextView tvMedicacionAhora, tvCantidadAhora, tvFormatoAhora, tvInstruccionAhora, tvHoraAhora, tvRecordatorio;
    Button bNoMedicacion, bTomarMedicacion, bVolverPagPrinci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacion_tomar);

        inicializar();

        bVolverPagPrinci.setOnClickListener(v -> finish());

        bNoMedicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tomado == false
            }
        });

        bTomarMedicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tomado == true
                // cantidadtotal - 1
            }
        });
    }

    public void inicializar(){
        bNoMedicacion = findViewById(R.id.bNoMedicacion);
        bTomarMedicacion = findViewById(R.id.bTomarMedicacion);
        bVolverPagPrinci = findViewById(R.id.bVolverPagPrinci);

        tvMedicacionAhora = findViewById(R.id.tvMedicacionAhora);
        tvCantidadAhora = findViewById(R.id.tvCantidadAhora);
        tvFormatoAhora = findViewById(R.id.tvFormatoAhora);
        tvInstruccionAhora = findViewById(R.id.tvInstruccionAhora);
        tvHoraAhora = findViewById(R.id.tvHoraAhora);
        tvRecordatorio = findViewById(R.id.tvRecordatorio);
    }
}