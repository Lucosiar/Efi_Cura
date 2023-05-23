package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

public class EditarSintomasActivity extends AppCompatActivity {

    AdminSQLiteOpenHelper db;
    int idSintoma;
    Button bVolverPrincipal;
    ImageButton bBorrarSintoma;
    TextView tvNombreSintoma, tvFrecuenciaSintoma, tvMostrarHoraSintoma, tvCantidadDeRegistros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sintomas);

        inicializar();

        Intent intent = getIntent();
        String tipoSintoma = intent.getStringExtra("tipo");
        idSintoma = intent.getIntExtra("id", 0);

        bVolverPrincipal.setOnClickListener(v -> finish());

        bBorrarSintoma.setOnClickListener(v -> {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Borrar síntoma");
            dialogo.setMessage("¿Realmente desea borrar el síntoma? No se le volverá a preguntar por él");
            dialogo.setCancelable(true);

            dialogo.setPositiveButton("Confirmar", (dialog, which) -> aceptarBorrado());
            dialogo.setNegativeButton("Calcelar", (dialog, which) -> cancelarBorrado());
            dialogo.show();
        });

        consultarSintomaPorId(idSintoma);
        obtenerCantidadRegistro(idSintoma);
    }

    private void cancelarBorrado(){
        Toast.makeText(this, "Borrado cancelado", Toast.LENGTH_SHORT).show();
        Intent cancelar = new Intent(this, PaginaPrincipal.class);
        startActivity(cancelar);
    }

    private void aceptarBorrado(){
        borrarSintoma();
        Toast.makeText(this, "Síntoma eliminado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void borrarSintoma(){
        SQLiteDatabase sql = db.getWritableDatabase();
        String id = String.valueOf(idSintoma);

        Cursor cursor = sql.rawQuery("select * from sintomas where id = ?", new String[]{id});

        if(cursor.moveToFirst()){
            int idSinto = cursor.getInt(0);
            sql.delete("sintomas", "id=?", new String[]{String.valueOf(idSinto)});
            cursor.close();
        }else{
            Toast.makeText(this, "No se ha encontrado el síntoma", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void consultarSintomaPorId(int id){
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from sintomas where id = ?", new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){
            String nombreSintoma = cursor.getString(1);
            String frecuenciaSintoma = cursor.getString(3);
            String horaSintoma = cursor.getString(2);

            tvNombreSintoma.setText(nombreSintoma);

            if(frecuenciaSintoma.equals("*")){
                tvFrecuenciaSintoma.setText("Diario");
            }else if(isNumeric(frecuenciaSintoma)){
                int frecuencia = Integer.parseInt(frecuenciaSintoma);
                if(frecuencia >= 1 && frecuencia <= 31){
                    tvFrecuenciaSintoma.setText("Mensual: día " + frecuencia);
                }
            }else if(isDayOfWeek(frecuenciaSintoma)){
                tvFrecuenciaSintoma.setText("Semanal: " + frecuenciaSintoma);
            }else{
                tvFrecuenciaSintoma.setText(frecuenciaSintoma);
            }
            tvMostrarHoraSintoma.setText(horaSintoma);
        }
        cursor.close();
        db.close();
    }

    private boolean isNumeric(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isDayOfWeek(String str) {
        String[] daysOfWeek = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        for (String day : daysOfWeek) {
            if (str.equalsIgnoreCase(day)) {
                return true;
            }
        }
        return false;
    }

    private void obtenerCantidadRegistro(int id){
        SQLiteDatabase sql = db.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select count(*) from sintomas where id = ?", new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){
            String cantidadRegistros = cursor.getString(0);
            int menos1 = Integer.parseInt(cantidadRegistros);
            cantidadRegistros = String.valueOf(menos1 - 1);
            tvCantidadDeRegistros.setText(cantidadRegistros);
        }
        cursor.close();
        db.close();
    }

    public void inicializar(){
        bVolverPrincipal = findViewById(R.id.bVolverPrincipal);
        bBorrarSintoma = findViewById(R.id.bBorrarSintoma);

        tvNombreSintoma = findViewById(R.id.tvNombreSintoma);
        tvFrecuenciaSintoma = findViewById(R.id.tvFrecuenciaSintoma);
        tvMostrarHoraSintoma = findViewById(R.id.tvMostrarHoraSintoma);
        tvCantidadDeRegistros = findViewById(R.id.tvCantidadDeRegistros);

        db = new AdminSQLiteOpenHelper(this);
    }
}