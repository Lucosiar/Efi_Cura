package com.example.a222.FormularioMedicacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.R;

public class FormularioFinalActivity extends AppCompatActivity {

    TextView tvInstrucciones, tvRecargas, tvTratamiento;
    CardView idCardView;
    TextView cvNombre, cvCantidadDiaria, cvFormato, cvFrecuencia, cvHora, cvCuando;
    Activity ffa;
    Button botonFinalizar;
    AdminSQLiteOpenHelper db;
    SharedPreferences preferences;
    String nombre, forma, cantidadDiaria, hora, frecu, comer, primerDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_final);

        inicializar();
        cambiarPantalla();
        sacarDatosSP();


    }


    public void sacarDatosSP(){
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        nombre = preferences.getString("nombre", "");
        forma = preferences.getString("forma", "");
        cantidadDiaria = preferences.getString("cantidadDiaria", "");
        hora = preferences.getString("hora1", "");
        frecu = preferences.getString("frecu", "");
        comer = preferences.getString("notaComida", "");
        primerDia = preferences.getString("fechaIni", "");


        int canti = Integer.parseInt(cantidadDiaria);

        cvNombre.setText(nombre);
        cvCantidadDiaria.setText(cantidadDiaria);
        if(canti >= 2){
            cvFormato.setText(forma + "s");
        }else{
            cvFormato.setText(forma);
        }
        cvHora.setText(hora);
        cvFrecuencia.setText(frecu);
        cvCuando.setText(comer);

        //MIrar porque no sale el check
        if(primerDia.isEmpty()){
            tvTratamiento.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
        }
    }


    //public void volcarSQLite(){}

    //public void borrarSP(){}

    private String consultarCorreo(String usuario){
        SQLiteDatabase base = db.getReadableDatabase();
        Cursor cursor = base.rawQuery("select correo from usuarios where usuario = ?", new String[]{usuario});

        String correo = "";
        int columIndex = cursor.getColumnIndex("correo");
        if(columIndex>=0 && cursor.moveToFirst()){
            correo = cursor.getString(columIndex);
        }
        cursor.close();
        return correo;
    }

    public void cambiarPantalla(){
        //Instrucciones
        tvInstrucciones.setOnClickListener(v -> {
           Intent is = new Intent(ffa, FormularioActivityComida.class);
            startActivity(is);
        });

        //Recargas
        tvRecargas.setOnClickListener(v ->{
            Intent rs = new Intent(ffa, FormularioRecargasActivity.class);
            startActivity(rs);
        });

        //Duracion tratamiento
        tvTratamiento.setOnClickListener(v -> {
            Intent tt = new Intent(ffa, FormularioInicioMActivity.class);
            startActivity(tt);
        });
    }

    public void inicializar(){
        //NO funciona
        //this.setTitle("Añadir medicamento");

        tvInstrucciones = findViewById(R.id.tvInstrucciones);
        tvRecargas = findViewById(R.id.tvRecarga);
        tvTratamiento = findViewById(R.id.tvTratamiento);

        idCardView = findViewById(R.id.idCardView);
        cvNombre = idCardView.findViewById(R.id.cvNombre);
        cvCantidadDiaria = idCardView.findViewById(R.id.cvCantidadDiaria);
        cvFormato = idCardView.findViewById(R.id.cvFormato);
        cvFrecuencia = idCardView.findViewById(R.id.cvFrecuencia);
        cvHora = idCardView.findViewById(R.id.cvHora);
        cvCuando = idCardView.findViewById(R.id.cvCuando);

        botonFinalizar = findViewById(R.id.botonFinalizar);
        ffa = this;
        db = new AdminSQLiteOpenHelper(ffa);

        if(cvCuando.getText().toString().isEmpty()){
            tvInstrucciones.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

    }
}