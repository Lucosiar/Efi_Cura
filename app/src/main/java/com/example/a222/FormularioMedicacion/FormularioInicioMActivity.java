package com.example.a222.FormularioMedicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormularioInicioMActivity extends AppCompatActivity {

    Activity inicio;
    TextView tvMostrarDiaComienzo;
    Button selDiaComienzo;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_inicio_mactivity);

        inicializar();

        calendar = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
        String fechaActual = formato.format(calendar.getTime());
        tvMostrarDiaComienzo.setText(fechaActual);


        selDiaComienzo.setOnClickListener(v -> {
            calendar = Calendar.getInstance();

            calendar.set(2022,0,1);

            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(inicio, (view, year, month, dayOfMonth) ->
                tvMostrarDiaComienzo.setText(dayOfMonth + "/" + (month + 1) + "/" + year), ano, mes, dia);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


            datePickerDialog.getDatePicker()
                    .init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
            });
            datePickerDialog.show();
        });
    }


    public void inicializar(){
        tvMostrarDiaComienzo = findViewById(R.id.tvMostrarDiaComienzo);
        selDiaComienzo = findViewById(R.id.selDiaComienzo);

        inicio = this;
    }
}