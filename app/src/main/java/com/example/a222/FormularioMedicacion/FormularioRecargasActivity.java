package com.example.a222.FormularioMedicacion;

import static java.lang.Integer.*;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.R;

public class FormularioRecargasActivity extends AppCompatActivity {

    TextView tvCuantasQuedan, tvNomMedicamento;
    Button bAcep, bCancel;
    EditText etCantidad;

    AdminSQLiteOpenHelper dbHelper;
    SQLiteDatabase db;
    String texto;
    int cantidadNueva;

    String medicina = "";
    String [] projeccion = {"cantidadCaja"};
    String seleccion = "nombre = ?";
    String [] seleccionARGS = {medicina};

    Intent iin;
    Bundle bun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_recargas);

        inicializar();

        dbHelper = new AdminSQLiteOpenHelper(this);
        db = dbHelper.getReadableDatabase();

        medicina = "Singulair";
        Cursor cursor = db.query("medicacion", projeccion, seleccion, seleccionARGS, null, null, null);

        //No funciona?
        if(cursor.moveToFirst()){
            int cantidadCaja = cursor.getInt(cursor.getColumnIndexOrThrow("cantidadCaja"));
            tvCuantasQuedan.setText("Te quedan " +  cantidadCaja + " pastillas");
            cursor.close();
        }

        if(bun != null){
            medicina = (String) bun.get("nombre");
            tvNomMedicamento.setText(medicina);
        }


        bCancel.setOnClickListener(v -> finish());

        //Actualizar cantidad de la caja
        bAcep.setOnClickListener(v -> {
            //Error esta linea
            texto = etCantidad.getText().toString();
            cantidadNueva = parseInt(texto);
            String sqlUpdate = "Update medicacion set cantidadCaja = " + cantidadNueva + " where nombre = '" + medicina + "'";
            db.execSQL(sqlUpdate);

            finish();
        });
    }

//Sacar la cantidad de pastillas que hay apra luego antes de actualizar la cantidad
    //que salga un dialogo en el que puedas decidir si sumar la medicacion a la que ya tenias
    //O si ese num es tu medicacion final
    public void sacarCantidadPastillas(){

    }

    private void inicializar(){
        tvCuantasQuedan = findViewById(R.id.tvCuantasQuedan);
        tvNomMedicamento = findViewById(R.id.tvNomMedicamento);
        bAcep = findViewById(R.id.bAcep);
        bCancel = findViewById(R.id.bCancel);
        etCantidad = findViewById(R.id.etCantidad);

        iin = getIntent();
        bun = iin.getExtras();
    }
}