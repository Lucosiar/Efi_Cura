package com.example.a222.FormularioCita_Medico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

public class EditarCitasActivity extends AppCompatActivity {

    TextView tvNombreMedico, tvHospital, tvEspecialidad, tvHoraDia;
    EditText etNotas;
    Button bCancelarVolver, bGuardar;
    ImageButton bBorrar;
    String citaSeleccionada;
    Bundle bundle;
    Intent ii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_citas);

        inicializar();

        ii = getIntent();
        bundle = ii.getExtras();

        if(bundle != null){
            citaSeleccionada = (String) bundle.get("nombre");
            tvNombreMedico.setText(citaSeleccionada);
        }

        bBorrar.setOnClickListener(v -> {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Borrar");
            dialogo1.setMessage("¿Realmente desea borrar la cita?");
            dialogo1.setCancelable(true);

            dialogo1.setPositiveButton("Confirmar", ((dialog, which) -> aceptarBorrado()));
            dialogo1.setNegativeButton("Calcelar", (dialog, which) -> cancelarBorrado());
            dialogo1.show();
        });
    }

    private void cancelarBorrado(){
        Toast.makeText(this, "Borrado cancelado", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, PaginaPrincipal.class);
        startActivity(i);
    }
    private void aceptarBorrado(){
        eliminarCitas(citaSeleccionada);
        Toast.makeText(this, "Cita Borrada", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void eliminarCitas(String citaSeleccionada){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("citas", "nombreMedico = ?", new String[]{citaSeleccionada});
        db.close();
    }

    public void inicializar(){
        tvNombreMedico = findViewById(R.id.tvNombreMedico);
        tvHospital = findViewById(R.id.tvHospital);
        tvEspecialidad = findViewById(R.id.tvEspecialidad);
        tvHoraDia = findViewById(R.id.tvHoraDia);
        etNotas = findViewById(R.id.etNotas);
        bCancelarVolver = findViewById(R.id.bCancelarVolver);
        bGuardar = findViewById(R.id.bGuardar);
        bBorrar = findViewById(R.id.bBorrar);
    }
}