package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FormularioMedicacion.FormularioRecargasActivity;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

public class MedicacionActivity extends AppCompatActivity {
    //qUiero del xml quitar la toolbar del activity

    ImageButton ibAtrass, ibBorrar, ibEditar;
    TextView tvInstruccionComida, tvRecargaRecordatorio, tvUltimaToma, tvMedicamento;
    Button botonRecarga;
    Intent iin;
    Bundle bun;
    String medicinaSeleccionada;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicacion);

        inicializar();

        //Recuperamos el nom del medicamento
        iin = getIntent();
        bun = iin.getExtras();
        recargar();



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
           dialogo1.setNegativeButton("Calcelar", (dialog, which) -> cancelarBorrado());
           dialogo1.show();

        });

        ibEditar.setOnClickListener(v ->{
            Intent siguiente = new Intent(this, EditarMedicacionActivity.class);
            siguiente.putExtra("nombre",  tvMedicamento.getText());
            startActivity(siguiente);
        });


    }

    private void cancelarBorrado(){
        Toast.makeText(this, "Borrado cancelado", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, PaginaPrincipal.class);
        startActivity(i);
    }
    private void aceptarBorrado(){
        eliminarMedicacion(medicinaSeleccionada);
        Toast.makeText(this, "Medicacion borrada", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void inicializar(){
        ibAtrass = findViewById(R.id.ibAtrass);
        ibBorrar = findViewById(R.id.ibBorrar);
        ibEditar = findViewById(R.id.ibEditar);

        tvInstruccionComida = findViewById(R.id.tvInstruccionComida);
        tvRecargaRecordatorio = findViewById(R.id.tvRecargaRecordatorio);
        tvUltimaToma = findViewById(R.id.tvUltimaToma);
        tvMedicamento = findViewById(R.id.tvMedicamento);

        botonRecarga = findViewById(R.id.botonRecarga);

        context = this;
    }

    private void recargar(){

        if(bun != null){
            medicinaSeleccionada = (String) bun.get("nombre");
            tvMedicamento.setText(medicinaSeleccionada);
        }

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from medicacion where nombre = ?", new String[]{medicinaSeleccionada});
        if(cursor != null && cursor.moveToFirst()){
            String cantidadActual = cursor.getString(10);
            int cantidad = Integer.parseInt(cantidadActual);
            if(cantidad == 0){
                String texto = "Las recargas no están activadas o te quedan 0 pastillas.";
                tvRecargaRecordatorio.setText(texto);
            }else {
                String texto = "Te quedan " + cantidadActual + " pastillas";
                tvRecargaRecordatorio.setText(texto);
            }
        }


    }

    private void eliminarMedicacion(String medicinaSeleccionada){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("medicacion", "nombre = ?", new String[]{medicinaSeleccionada});
        db.close();
    }
}