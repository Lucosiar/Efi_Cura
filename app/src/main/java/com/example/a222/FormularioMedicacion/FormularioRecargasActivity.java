package com.example.a222.FormularioMedicacion;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.R;

public class FormularioRecargasActivity extends AppCompatActivity {

    TextView tvCuantasQuedan, tvNomMedicamento;
    Button bAcep, bCancel;
    EditText etCantidad;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    AdminSQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    String texto;
    int cantidadNueva;
    String cantidadActual;
    String mostrarCantidadActual;
    int cantidadTotal;
    int cantidadEnte;

    Cursor cursor;

    Boolean pasarPreferences = false;
    String medicina = "";
    AlertDialog alertDialog;

    Context context;
    Intent iin;
    Bundle bun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_recargas);

        inicializar();

        //Sacar nombre del medicamento
        if(bun != null){
            medicina = (String) bun.get("nombre");
            tvNomMedicamento.setText(medicina);
        }

        try {
            dbHelper = new AdminSQLiteOpenHelper(this);
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("Select * from medicacion where nombre = ?", new String[]{medicina});

            if(cursor != null && cursor.moveToFirst()) {
                pasarPreferences = false;
                cantidadActual = cursor.getString(10);
                if(cantidadActual == null){
                    cantidadActual = "0";
                }
                cantidadEnte = parseInt(cantidadActual);
                mostrarCantidadActual = "Te quedan " + cantidadActual + " pastillas";
                tvCuantasQuedan.setText(mostrarCantidadActual);
            }else{
                pasarPreferences = true;
                preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("cantidadTotal", cantidadActual);
                editor.commit();
            }
        }catch(SQLException e){
            Log.e(":::ERROR:TAG", "Error al abrir la base o sacar datos", e);
        }


        bCancel.setOnClickListener(v -> finish());

        //Actualizar cantidad de la caja
        bAcep.setOnClickListener(v -> {
            AlertDialog.Builder builder =  new AlertDialog.Builder(this);
            builder.setTitle("Cantidad Pastillas");
            String preguntaNull = "¿Desea agregar la medicación? Ahora mismo no tiene ninguna.";
            String pregunta = "¿Desea agregar estas pastillas a las " + cantidadActual + " que le quedan?";
            //Si la cantidad es nula se muestra un mensaje o otro
            if(cantidadActual == null){
                builder.setMessage(preguntaNull);
            }else{
                builder.setMessage(pregunta);
            }
            if(!pasarPreferences){
                texto = etCantidad.getText().toString();
                cantidadNueva = parseInt(texto);
                builder.setPositiveButton("Si", (dialog, which) -> {
                    cantidadTotal = cantidadNueva + cantidadEnte;
                    String sqlUpdate = "Update medicacion set cantidadCaja = " + cantidadTotal + " where nombre = '" + medicina + "'";
                    db.execSQL(sqlUpdate);
                    Toast.makeText(context, "Cantidad guardada.", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }else{
                pasarPreferences = false;
                preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("cantidadTotal", cantidadActual);
                editor.commit();
                Toast.makeText(context, "Cantidad guardada.", Toast.LENGTH_SHORT).show();
                finish();
            }


            builder.setNegativeButton("No", (dialog, which) -> {
                //Error esta linea
                String sqlUpdate = "Update medicacion set cantidadCaja = " + cantidadNueva + " where nombre = '" + medicina + "'";
                db.execSQL(sqlUpdate);
                Toast.makeText(context, mostrarCantidadActual, Toast.LENGTH_SHORT).show();
                finish();

            });
            alertDialog = builder.create();
            alertDialog.show();

        });
    }

    private void inicializar(){
        tvCuantasQuedan = findViewById(R.id.tvCuantasQuedan);
        tvNomMedicamento = findViewById(R.id.tvNomMedicamento);
        bAcep = findViewById(R.id.bAcep);
        bCancel = findViewById(R.id.bCancel);
        etCantidad = findViewById(R.id.etCantidad);

        iin = getIntent();
        bun = iin.getExtras();

        context = this;
    }
}