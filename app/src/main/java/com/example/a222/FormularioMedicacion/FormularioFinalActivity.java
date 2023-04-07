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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;


public class FormularioFinalActivity extends AppCompatActivity {

    TextView tvInstrucciones, tvRecargas, tvTratamiento;
    CardView idCardView;
    TextView cvNombre, cvCantidadDiaria, cvFormato, cvFrecuencia, cvHora, cvCuando;
    Activity ffa;
    Button botonFinalizar;
    AdminSQLiteOpenHelper db;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String nombre, forma, cantidadDiaria, hora, frecu, notaComida, primerDia, fechaIni, fechaFin, usuario;
    int duracion;
    String duracionS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_final);

        inicializar();
        cambiarPantalla();
        sacarDatosSP();
        ponerCheck();

        botonFinalizar.setOnClickListener(v -> {
            guardarSQL();
            borrarSP();

            Intent as = new Intent(ffa, PaginaPrincipal.class);
            startActivity(as);
        });

    }


    public void sacarDatosSP(){
        //Sacamos los datos del sp
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        nombre = preferences.getString("nombre", "");
        forma = preferences.getString("forma", "");
        cantidadDiaria = preferences.getString("cantidadDiaria", "");
        hora = preferences.getString("hora1", "");
        frecu = preferences.getString("frecu", "");
        notaComida = preferences.getString("notaComida", "");
        primerDia = preferences.getString("fechaIni", "");
        //Sacamos los valores de la fecha Inicial  y la duracion
        fechaIni = preferences.getString("fechaIni", "");
        duracion = preferences.getInt("duracion", 0);
        fechaFin = preferences.getString("fechaFin", "SINFCH");

        //Parseamos la cantidad para poder poner bien el formato
        int canti = Integer.parseInt(cantidadDiaria);

        //Mostramos el nombre del medicamento en el cardview
        //Todos los atributos que empiecen por cv son del cardview
        cvNombre.setText(nombre);

        cvCantidadDiaria.setText(cantidadDiaria);
        if(canti >= 2){
            cvFormato.setText(forma + "s");
        }else{
            cvFormato.setText(forma);
        }
        cvHora.setText(hora);
        cvFrecuencia.setText(frecu);
        cvCuando.setText(notaComida);





    }

    public void guardarSQL(){
        //Sacamos el usuario del sp para guardarlo
        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        usuario = preferences.getString("nombre", "");
        usuario = consultarCorreo(usuario);

        duracionS = String.valueOf(duracion);

        //Insertamos todos los valores en la base de datos
        db.insertarMedicacion(nombre, cantidadDiaria, fechaIni, fechaFin, duracionS, hora, cantidadDiaria, forma, notaComida, frecu, usuario);
        Toast.makeText(ffa, "La medicación ha sido guarda", Toast.LENGTH_SHORT).show();
    }



    public void borrarSP(){
        preferences = getSharedPreferences("datos", MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

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

    public void ponerCheck(){
        if(primerDia.isEmpty()){
            tvTratamiento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_calendar_month, 0, 0, 0);
        }else{
            tvTratamiento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_calendar_month, 0, R.drawable.ic_baseline_check, 0);
        }

        if(cvCuando.getText().toString().isEmpty()){
            tvInstrucciones.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_info_24, 0, 0, 0);
        }else{
            tvInstrucciones.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_info_24, 0, R.drawable.ic_baseline_check, 0);
        }
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
    }
}