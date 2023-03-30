package com.example.a222.FormularioCita_Medico;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Medico;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CitaActivity extends AppCompatActivity {


    //Activity para añadir y crear una cita nueva
    Button botonAnadirMedico, bDia, bHora, botonAnadirCita2;

    Spinner spinnerMedicos;
    ArrayList<String>listaMedicos;
    ArrayList<Medico>MedicoLista;
    AdminSQLiteOpenHelper db;
    Activity citas;

    String usu;
    String usuario;
    SharedPreferences preferences;

    TextView tvDia, tvHora, tvMedico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        inicializar();

        //Onclick de añadir médico
        botonAnadirMedico = findViewById(R.id.botonAnadirMedico);
        botonAnadirMedico.setOnClickListener(v -> {
            Intent i = new Intent(citas, MedicoActivity.class);
            startActivity(i);
        });
        //Metodo para consultar los medicos en la bd
        consultarMedicos();

        //Adaptador para mostrar los medicos en el spinner
        ArrayAdapter<CharSequence> adaptador =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaMedicos);
        spinnerMedicos.setAdapter(adaptador);

        spinnerMedicos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                tvMedico.setText("" + spinnerMedicos.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Boton para añadir la cita en la base de datos
        botonAnadirCita2 = findViewById(R.id.botonAnadirCita2);
        botonAnadirCita2.setOnClickListener(v -> {
            String nombre = tvMedico.getText().toString();
            String dia = tvDia.getText().toString();
            String hora = tvHora.getText().toString();

            if(!validar()){

            }else{
                preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
                usu = preferences.getString("nombre", "");
                usuario = consultarCorreo(usu);

                db.insertarCita(nombre, dia, hora, usuario);
                Toast.makeText(citas, "Cita registrada", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(citas, PaginaPrincipal.class);
                startActivity(i);

            }
        });

    }

    //Método para consultar los medicos en la base de datos
    private void consultarMedicos(){
        SQLiteDatabase base = db.getReadableDatabase();

        Medico medico = null;
        MedicoLista = new ArrayList<>();

        Cursor cursor = base.rawQuery("Select * from medicos", null);

        while(cursor.moveToNext()){
            medico = new Medico();
            medico.setNombre(cursor.getString(1));
            medico.setEspecialidad(cursor.getString(2));
            medico.setHospital(cursor.getString(3));

            MedicoLista.add(medico);
        }
        obtenerMedicos();
    }

    //Metodo para obtener los medicos en la base de datos
    private void obtenerMedicos(){
        listaMedicos = new ArrayList<>();
        listaMedicos.add("Seleccione: ");

        for(int i = 1; i<MedicoLista.size(); i++){
            listaMedicos.add(MedicoLista.get(i).getNombre() + " - " +
                   MedicoLista.get(i).getEspecialidad()); //+ " - " +
                    //MedicoLista.get(i).getEspecialidad() + " -"  +
                    //MedicoLista.get(i).getHospital() + " - " +
                   // MedicoLista.get(i).getNumero() + " - " +
                   // MedicoLista.get(i).getCorreo());
        }
    }

    //Metodo onclick para boton de seleccion de fecha
    public void click(View view){
        //Elegimos el dia
        if(view == bDia){
            final Calendar c = Calendar.getInstance();

            c.set(2022, 0, 1);

            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);


            DatePickerDialog datePickerDialog = new DatePickerDialog(citas, (view1, year, month, dayOfMonth) -> tvDia.setText(dayOfMonth + "/" + (month + 1) + "/" + year), dia, mes, ano);
            //Seleccionamos la fecha actual del dispositivo
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

            datePickerDialog.getDatePicker().init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar selectedDate = Calendar.getInstance();
                    //Cogemos el dia seleccionado
                    selectedDate.set(year, monthOfYear, dayOfMonth);
                    int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);
                    //En caso de que sea fin de semana, se cerrara la seleccion de la fecha
                    if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
                        Toast.makeText(citas, "No se puede seleccionar un fin de semana para una cita médica.", Toast.LENGTH_LONG).show();
                        datePickerDialog.dismiss();
                    }
                }
            });
            datePickerDialog.show();
        }

        //Elegimos la horas
        if(view == bHora){
            final Calendar c = Calendar.getInstance();
            int hora = c.get(Calendar.HOUR_OF_DAY);
            int minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(citas, (view12, hourOfDay, minute) -> tvHora.setText(hourOfDay + ":" + minute), hora, minutos, true);
            timePickerDialog.show();
        }
    }

    //validacion de los campos incompletos
    public boolean validar(){
        boolean ret = true;
        String errorDia = tvDia.getText().toString();
        String errorHora = tvHora.getText().toString();
        String errorNombre = tvMedico.getText().toString();

        if(errorNombre.isEmpty()){
            Toast.makeText(citas, "Falta el médico", Toast.LENGTH_SHORT).show();
            ret = false;
        }
        else if(errorDia.isEmpty()){
            tvDia.setError("Este campo no puede quedar vacío");
            ret = false;
        }else if(errorHora.isEmpty()){
            tvHora.setError("Este campo no puede quedar vacío");
            ret = false;
        }

        return ret;
    }

    public void inicializar(){
        //Activity bar
        this.setTitle("Añadir Cita");

        //Activity
        citas = this;

        //Base de datos
        db = new AdminSQLiteOpenHelper(citas);

        //Spinner de medicos
        spinnerMedicos = findViewById(R.id.spinnerMedicos);
        tvMedico = findViewById(R.id.tvMedico);

        //Vinculaciones para datepicker
        bDia = findViewById(R.id.bDia);
        bHora = findViewById(R.id.bHora);
        tvDia = findViewById(R.id.tvDia);
        tvHora = findViewById(R.id.tvHora);
    }

    //Sacar el correo electronico de la base de datos
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
}