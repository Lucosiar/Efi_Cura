package com.example.a222.FormularioMedicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormularioInicioMActivity extends AppCompatActivity {

    Activity inicio;
    TextView tvMostrarDiaComienzo, tvRespu;
    Button selDiaComienzo, buttonSiguiente;
    Calendar calendar;

    ListView listaTiempo;
    private final String [] tiempos = {"5 días", "1 semana", "10 días", "30 días", "Elegir días", "Tratamiento en curso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_inicio_mactivity);

        inicializar();

        calendar = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual = formato.format(calendar.getTime());
        tvMostrarDiaComienzo.setText(fechaActual);

        //Seleccionador de día para comienzo de la medicación
        selDiaComienzo.setOnClickListener(v -> {
            calendar = Calendar.getInstance();

            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);

            calendar.set(ano,mes,dia);

            DatePickerDialog datePickerDialog = new DatePickerDialog(inicio, (view, year, month, dayOfMonth) ->
                tvMostrarDiaComienzo.setText(dayOfMonth + "/" + (month + 1) + "/" + year), ano, mes, dia);

            //Escoger el dia actual
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

            datePickerDialog.getDatePicker()
                    .init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
            });
            datePickerDialog.show();
        });

        //Adaptador de la lista
        ArrayAdapter<String>adapter = new ArrayAdapter<>(inicio, android.R.layout.simple_list_item_1, tiempos);
        listaTiempo.setAdapter(adapter);

        //
        listaTiempo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int i, long l) {
                tvRespu.setText("" + listaTiempo.getItemAtPosition(i));

                switch ((tvRespu.getText().toString())){
                    case "5 días":
                        //Sumar 5 dias al tvMostrarDiaComienzo
                        break;
                    case "1 semana":
                        //sumar 7 días
                        break;
                    case "10 días":
                        //Sumar 10
                        break;
                    case "30 días":
                        break;
                    case "Elegir días":
                        Intent is = new Intent(inicio, FormularioDuracionActivity.class);
                        startActivity(is);
                        break;
                    case "Tratamiento en curso":
                        //No se hace nada, fecha final == null
                        break;
                }

            }
        });
    }


    public void inicializar(){
        tvMostrarDiaComienzo = findViewById(R.id.tvMostrarDiaComienzo);
        selDiaComienzo = findViewById(R.id.selDiaComienzo);
        buttonSiguiente = findViewById(R.id.buttonSiguiente);
        listaTiempo = findViewById(R.id.listaTiempo);
        tvRespu = findViewById(R.id.tvRespu);

        inicio = this;
    }
}