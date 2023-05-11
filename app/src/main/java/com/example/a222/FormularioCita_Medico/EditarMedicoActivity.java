package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;


public class EditarMedicoActivity extends AppCompatActivity {
    TextView tvMostrarNombreMedico, tvMostrarEspecialidad, tvMostrarHospital,
            tvMostrarTelefono, tvMostrarCorreo;
    ImageButton bAtrasMedico, bBorrarMedico, bEditarMedico;

    String medicoSeleccionado = "";
    String especialidadMedico = "";
    String noNumeroAsignado = "No tiene ningún número asignado.";
    String noCorreoAsignado = "No tienen ningún correo asignado";

    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_medico);

        inicializar();

        bAtrasMedico.setOnClickListener(v -> finish());

        bBorrarMedico.setOnClickListener(v -> {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Borrar Medico");
            dialogo.setMessage("¿Realmente desea borrar el médico?");
            dialogo.setCancelable(true);

            dialogo.setPositiveButton("Confirmar", (dialog, which) -> aceptarBorrado());
            dialogo.setNegativeButton("Calcelar", (dialog, which) -> cancelarBorrado());
            dialogo.show();
        });

        bEditarMedico.setOnClickListener(v ->{
            Intent editar = new Intent(this, MedicoActivity.class);
            startActivity(editar);
        });

    }

    private void cancelarBorrado(){
        Toast.makeText(this, "Borrado cancelado", Toast.LENGTH_SHORT).show();
        Intent cancelar = new Intent(this, PaginaPrincipal.class);
        startActivity(cancelar);
    }

    private void aceptarBorrado(){
        eliminarMedico(medicoSeleccionado, especialidadMedico);
        Toast.makeText(this, "Medico eliminado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void eliminarMedico(String medicoSeleccionado, String especialidadMedico){
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getWritableDatabase();
        Cursor cursor = sql.rawQuery("Select id from medicos where nombre = ? and especialidad = ?", new String[]{medicoSeleccionado, especialidadMedico});

        if(cursor.moveToFirst()){
            int idMedico = cursor.getInt(0);
            sql.delete("medicos", "id = ?", new String[]{String.valueOf(idMedico)});
            Toast.makeText(this, "Medico eliminado correctamente", Toast.LENGTH_SHORT).show();

            cursor.close();
        }else{
            Toast.makeText(this, "No se ha encontrado el medico", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void obtenerDatos(String medicoSeleccionado, String especialidadMedico){
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getWritableDatabase();
        Cursor cursor = sql.rawQuery("Select * from medicos where nombre = ? and especialidad = ?", new String[]{medicoSeleccionado, especialidadMedico});

        if(cursor.moveToFirst()){
            //int idMedico = cursor.getInt(0);
            String nombreMedico = cursor.getString(1);
            String especialidad = cursor.getString(2);
            String hospital = cursor.getString(3);
            String numero = cursor.getString(4);
            String correo = cursor.getString(5);

            tvMostrarNombreMedico.setText(nombreMedico);
            tvMostrarEspecialidad.setText(especialidad);
            tvMostrarHospital.setText(hospital);

            if(numero.equals("")){
                tvMostrarTelefono.setText(noNumeroAsignado);
            }else{
                tvMostrarTelefono.setText(numero);
            }
            if(correo.equals("")){
                tvMostrarCorreo.setText(noCorreoAsignado);
            }else{
                tvMostrarCorreo.setText(correo);
            }
        }else{
            Toast.makeText(this, "No se ha encontrado el medico", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }

    public void inicializar(){
        tvMostrarNombreMedico = findViewById(R.id.tvMostrarNombreMedico);
        tvMostrarEspecialidad = findViewById(R.id.tvMostrarEspecialidad);
        tvMostrarHospital = findViewById(R.id.tvMostrarHospital);
        tvMostrarTelefono = findViewById(R.id.tvMostrarTelefono);
        tvMostrarCorreo = findViewById(R.id.tvMostrarCorreo);
        bBorrarMedico = findViewById(R.id.bBorrarMedico);
        bAtrasMedico = findViewById(R.id.bAtrasMedico);
        bEditarMedico = findViewById(R.id.bEditarMedico);

        context = this;

        intent = getIntent();
        medicoSeleccionado = intent.getStringExtra("medico");
        especialidadMedico = intent.getStringExtra("especialidad");
        tvMostrarNombreMedico.setText(medicoSeleccionado);
        tvMostrarEspecialidad.setText(especialidadMedico);

        obtenerDatos(medicoSeleccionado, especialidadMedico);
    }
}