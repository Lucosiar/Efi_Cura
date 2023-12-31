package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.Adaptadores.AdaptadorCitas;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class AgregarNumeroSintomaActivity extends AppCompatActivity {

    TextView tvTipoSintomaNumero, tvDiaHoyNumero, tvNumeroMedida;
    EditText etNumero;
    Button bGuardarSintomaNumero;

    String tipoSintoma, usuario;
    Boolean alerta;
    AdminSQLiteOpenHelper db;
    SharedPreferences s;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_numero_sintoma);

        Intent intent = getIntent();
        tipoSintoma = intent.getStringExtra("tipo");

        s = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        usuario = s.getString("nombre", "");
        alerta = s.getBoolean("sAlertaSintomas", false);


        inicializar();

        bGuardarSintomaNumero.setOnClickListener(v -> {
            if(alerta) {
                aceptar();
            }
            guardarSintoma();
        });
    }

    private void guardarSintoma(){
        String fechaActual = obtenerFecha();
        String horaActual = obtenerHora();
        String alta = "";
        String baja = "";
        String dolor = String.valueOf(etNumero.getText());

        String usu = consultarCorreo(usuario);

        db.insertarSintomas(tipoSintoma, horaActual, fechaActual, alta, baja, dolor, usu);
        volver();

    }

    private void aceptar(){
        if(tipoSintoma.equals("Pulsaciones")){
            int dolor = Integer.parseInt(String.valueOf(etNumero.getText()));
            if(dolor >= 105 || dolor <=60){
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText("No mostrar estas notificaciones");

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(checkBox);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                dialogo.setTitle("Sus pulsaciones son más altas que los valores normales.");
                dialogo.setMessage("Si no se encuentra bien, debería visitar a un especialista");
                dialogo.setCancelable(true);
                dialogo.setView(layout);

                dialogo.setPositiveButton("Aceptar", (dialog, which) -> {
                    boolean noNotificar = checkBox.isChecked();

                    if(noNotificar){
                        editor = s.edit();
                        editor.putBoolean("sAlertaSintomas", false);
                        editor.apply();
                    }
                    guardarSintoma();
                });

                dialogo.setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                });

                dialogo.show();
            }
        }
        else if(tipoSintoma.equals("Temperatura corporal")){
            int dolor = Integer.parseInt(String.valueOf(etNumero.getText()));
            if(dolor >= 39){
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText("No mostrar estas notificaciones");

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(checkBox);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                dialogo.setTitle("Su temperatura está por encima de los valores normales.");
                dialogo.setMessage("Si no se encuentra bien, debería visitar a un especialista");
                dialogo.setCancelable(true);
                dialogo.setView(layout);

                dialogo.setPositiveButton("Aceptar", (dialog, which) -> {
                    boolean noNotificar = checkBox.isChecked();

                    if(noNotificar){
                        editor = s.edit();
                        editor.putBoolean("sAlertaSintomas", false);
                        editor.apply();
                    }
                    guardarSintoma();
                });

                dialogo.setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                });

                dialogo.show();
            }
        }
    }

    private void volver(){
        finish();
        Toast.makeText(this, "Síntoma registrado", Toast.LENGTH_SHORT).show();
    }
    private static String obtenerFecha(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);
    }

    private static String obtenerHora(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date);
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

    public void inicializar(){
        etNumero = findViewById(R.id.etNumero);

        bGuardarSintomaNumero = findViewById(R.id.bGuardarSintomaNumero);

        tvTipoSintomaNumero = findViewById(R.id.tvTipoSintomaNumero);
        tvNumeroMedida = findViewById(R.id.tvNumeroMedida);

        tvTipoSintomaNumero.setText(tipoSintoma);

        tvDiaHoyNumero = findViewById(R.id.tvDiaHoyNumero);
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("es", "ES"));
        String fechaFormateada = "Hoy, " + dateFormat.format(fechaActual);
        tvDiaHoyNumero.setText(fechaFormateada);
        //Peso":
        // "Pulsaciones":
        // "Temperatura corporal"
        if(tipoSintoma.equals("Pulsaciones")){
            tvNumeroMedida.setText("ppm");
        }else if(tipoSintoma.equals("Peso")){
            tvNumeroMedida.setText("kg");
        }else if(tipoSintoma.equals("Temperatura corporal")){
            tvNumeroMedida.setText("º");
        }

        db = new AdminSQLiteOpenHelper(this);

    }
}