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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Medico;
import com.example.a222.FragmentosPP.PaginaPrincipal;
import com.example.a222.Notificaciones.WorkManagerNoti;
import com.example.a222.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CitaActivity extends AppCompatActivity {
    //Activity para añadir y crear una cita nueva
    Button botonAnadirMedico, bDia, bHora, botonAnadirCita2;
    Spinner spinnerMedicos;
    ArrayList<String>listaMedicos;
    ArrayList<Medico>MedicoLista;
    AdminSQLiteOpenHelper db;
    Activity citas;
    String usu, usuario;
    Context context;
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
                String mostrarTexto = "" + spinnerMedicos.getItemAtPosition(i);
                tvMedico.setText(mostrarTexto);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bDia.setOnClickListener(v -> {
                    final Calendar c = Calendar.getInstance();

                    int dia = c.get(Calendar.DAY_OF_MONTH);
                    int mes = c.get(Calendar.MONTH);
                    int ano = c.get(Calendar.YEAR);

                    c.set(ano, mes, dia);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(citas, (view1, year, month, dayOfMonth) ->
                            tvDia.setText(dayOfMonth + "/" + (month + 1) + "/" + year), dia, mes, ano);
                    //Seleccionamos la fecha actual del dispositivo
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                    datePickerDialog.getDatePicker().init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), (view12, year, monthOfYear, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        //Cogemos el dia seleccionado
                        selectedDate.set(year, monthOfYear, dayOfMonth);
                        int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);
                        //En caso de que sea fin de semana, se cerrara la seleccion de la fecha
                        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                            Toast.makeText(citas, "No se puede seleccionar un fin de semana para una cita médica.", Toast.LENGTH_LONG).show();
                            datePickerDialog.dismiss();
                        }
                    });
                    datePickerDialog.show();
        });

        bHora.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hora = c.get(Calendar.HOUR_OF_DAY);
            int minutos = c.get(Calendar.MINUTE);

            //TimePicker correcto
            TimePickerDialog time = new TimePickerDialog(citas, ((view1, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                String mostrarTexto = hourOfDay + ":" + minutesST;
                tvHora.setText(mostrarTexto);
            }), hora, minutos, true);
            time.show();
        });


        //Boton para añadir la cita en la base de datos
        botonAnadirCita2 = findViewById(R.id.botonAnadirCita2);
        botonAnadirCita2.setOnClickListener(v -> {
            String nombre = tvMedico.getText().toString();
            String dia = tvDia.getText().toString();
            String hora = tvHora.getText().toString();
/*

            if(hora.contains(":")){
                hora = hora.replace(":", "");
            }

            String citaCompleta = "2000/01/01" + " " + hora;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            try{
                Date date = sdf.parse(citaCompleta);
                calendar.setTime(date);
                Log.d(":::Mostrar:", ("Hora; " + citaCompleta));
                Log.d(":::Mostrar:", ("Date: " + date));

            }catch(ParseException e) {
                Log.e(":::ERROR:", ("Hora" + citaCompleta));
                e.printStackTrace();
            }

            String horaGuardada = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

            String tag = generateKey();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(System.currentTimeMillis());
            calendar2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora.substring(0, 2)));
            calendar2.set(Calendar.MINUTE, Integer.parseInt(hora.substring(2)));
            calendar2.set(Calendar.SECOND, 0);
            calendar2.set(Calendar.MILLISECOND, 0);
            calendar2.add(Calendar.HOUR_OF_DAY, -24);

            Long Alerttime = calendar.getTimeInMillis() - System.currentTimeMillis();

            Data data = guardarData();
            WorkManagerNoti.guardarNoti(Alerttime, data, tag);
*/

            if(validar()) {
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

    @Override
    public void onResume(){
        super.onResume();
        consultarMedicos();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaMedicos);
        spinnerMedicos.setAdapter(adaptador);
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

        for(int i = 0; i<MedicoLista.size(); i++){
            listaMedicos.add(MedicoLista.get(i).getNombre() + " - " +
                   MedicoLista.get(i).getEspecialidad());
        }
    }

    private String generateKey(){
        return UUID.randomUUID().toString();
    }

    private Data guardarData(){
        return new Data.Builder()
                .putString("cita", "Cita programada")
                .putString("detalle", "Mañana tiene una cita, revísela")
                .build();
    }

    //validacion de los campos incompletos
    private boolean validar(){
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
        context = this;

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