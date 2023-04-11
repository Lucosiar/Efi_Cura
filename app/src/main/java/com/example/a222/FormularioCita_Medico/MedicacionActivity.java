package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.FormularioMedicacion.FormularioRecargasActivity;
import com.example.a222.FragmentosPP.HomeFragment;
import com.example.a222.FragmentosPP.MedicacionFragment;
import com.example.a222.R;

public class MedicacionActivity extends AppCompatActivity {
    //qUiero del xml quitar la toolbar del activity

    ImageButton ibAtrass, ibBorrar, ibEditar;
    TextView tvInstruccionComida, tvRecargaRecord, tvUltimaToma, tvMedicamento;
    Button botonRecarga;
    Intent iin;
    Bundle bun;
    String medicinaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacion);

        inicializar();

        //Recuperamos el nom del medicamento
        iin = getIntent();
        bun = iin.getExtras();

        if(bun != null){
            medicinaSeleccionada = (String) bun.get("nombre");
            tvMedicamento.setText(medicinaSeleccionada);
        }


        botonRecarga.setOnClickListener(v -> {
            Intent recargas = new Intent(this, FormularioRecargasActivity.class);
            recargas.putExtra("nombre", medicinaSeleccionada);
            startActivity(recargas);
        });

        ibAtrass.setOnClickListener(v ->{
            finish();
        });


        ibBorrar.setOnClickListener(v ->{
           AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
           dialogo1.setTitle("Borrar");
           dialogo1.setMessage("¿Realmente desea eliminar la medicación?");
           dialogo1.setCancelable(true);

           dialogo1.setPositiveButton("Confirmar", (dialog, which) -> aceptarBorrado());
           dialogo1.setNegativeButton("Calencelar", (dialog, which) -> cancelarBorrado());
           dialogo1.show();

        });

        ibEditar.setOnClickListener(v ->{
            Intent siguiente = new Intent(this, EditarMedicacionActivity.class);
            startActivity(siguiente);
        });


    }

    public void cancelarBorrado(){
        Toast.makeText(this, "Borrado cancelada", Toast.LENGTH_SHORT).show();
    }
    public void aceptarBorrado(){
        Toast.makeText(this, "Intento de borrar", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void inicializar(){
        ibAtrass = findViewById(R.id.ibAtrass);
        ibBorrar = findViewById(R.id.ibBorrar);
        ibEditar = findViewById(R.id.ibEditar);

        tvInstruccionComida = findViewById(R.id.tvInstruccionComida);
        tvRecargaRecord = findViewById(R.id.tvRecargaRecord);
        tvUltimaToma = findViewById(R.id.tvUltimaToma);
        tvMedicamento = findViewById(R.id.tvMedicamento);

        botonRecarga = findViewById(R.id.botonRecarga);
    }
}