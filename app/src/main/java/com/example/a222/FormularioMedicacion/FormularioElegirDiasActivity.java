package com.example.a222.FormularioMedicacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a222.R;

import java.util.Calendar;

public class FormularioElegirDiasActivity extends AppCompatActivity {

    private static final String KEY_SELECTED_DAYS = "selectedDays";
    private boolean[] checkdDays = new boolean[7];

    SharedPreferences s;
    SharedPreferences.Editor editor;
    String frecuenciaSemanal;
    Activity formulario;

    Button bDiaElegir, bHoraElegir, bSeguir;
    TextView tvDiaElegir, tvHoraElegir, tvEnunciado, tvFrecuenciaElegir;

    String texto = "Elija los días que vas a tomar la medicación";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_elegir_dias);

        inicializar();

        s = getSharedPreferences("datos", Context.MODE_PRIVATE);
        frecuenciaSemanal = s.getString("dia", "");
        tvFrecuenciaElegir.setText(frecuenciaSemanal);

        editor = s.edit();
        if(frecuenciaSemanal.equals("Una vez a la semana")){
            editor.putString("diasTomas", "Una vez a la semana");
            editor.apply();

        }else if(frecuenciaSemanal.equals("Cada 2 días")){
            editor.putString("diasTomas", "Cada 2 días");
            editor.apply();

        }else if(frecuenciaSemanal.equals("2 días a la semana")){
            editor.putString("diasTomas", "2 días a la semana");
            editor.apply();

        }

        bDiaElegir.setOnClickListener(v -> {
            showDialog();
        });

        bHoraElegir.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hora = c.get(Calendar.HOUR_OF_DAY);
            int minutos = c.get(Calendar.MINUTE);

            TimePickerDialog time = new TimePickerDialog(formulario, ((view1, hourOfDay, minute) -> {
                String minutesST = minute < 10 ? "0" + minute : String.valueOf(minute);
                String mostrarTexto = hourOfDay + ":" + minutesST;
                tvHoraElegir.setText(mostrarTexto);
            }), hora, minutos, true);
            time.show();
        });

        bSeguir.setOnClickListener(v -> {
            Intent siguiente  = new Intent(formulario, FormularioFinalActivity.class);
            startActivity(siguiente);
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione los días que va a tomar la medicación");

        String[] daysOfWeek = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        builder.setMultiChoiceItems(daysOfWeek, checkdDays, (dialog, which, isChecked) -> checkdDays[which] = isChecked);

        builder.setPositiveButton("Guardar", (dialog, which) -> saveSelectedDays());

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();

        if(frecuenciaSemanal.equals("Una vez a la semana")){
            dialog.setOnShowListener(dialogInterface -> {
                ListView listView = dialog.getListView();
                listView.setItemChecked(0, true);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            });
        }else if(frecuenciaSemanal.equals("Cada 2 días")){
            dialog.setOnShowListener(dialogInterface -> {
                ListView listView = dialog.getListView();
                listView.setItemChecked(0, true);
                listView.setItemChecked(3, true);
                listView.setItemChecked(6, true);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            });
        }else if(frecuenciaSemanal.equals("2 días a la semana")){
            dialog.setOnShowListener(dialogInterface -> {
                ListView listView = dialog.getListView();
                listView.setItemChecked(1, true);
                listView.setItemChecked(4, true);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            });
        }
        dialog.show();
    }

    private void saveSelectedDays() {
        StringBuilder selectedDays = new StringBuilder();
        for (int i = 0; i < checkdDays.length; i++) {
            if (checkdDays[i]) {
                selectedDays.append(i).append(",");
            }
        }

        String selectedDaysString = selectedDays.toString();

        if(frecuenciaSemanal.equals("Una vez a la semana") && selectedDaysString.split(",").length > 1){
            Toast.makeText(this, "Solo puede seleccionar un día a la semana", Toast.LENGTH_SHORT).show();
        }
        else if(frecuenciaSemanal.equals("Cada 2 días") && selectedDaysString.split(",").length > 3){
            Toast.makeText(this, "Solo puede seleccionar tres días a la semana", Toast.LENGTH_SHORT).show();
        }
        else if(frecuenciaSemanal.equals("2 días a la semana") && selectedDaysString.split(",").length > 2){
            Toast.makeText(this, "Solo puede seleccionar dos días a la semana", Toast.LENGTH_SHORT).show();
        }
        else if(frecuenciaSemanal.equals("Cada 28 horas") && selectedDaysString.split(",").length > 6){
            Toast.makeText(this, "Solo puede seleccionar tres días a la semana", Toast.LENGTH_SHORT).show();
        }else{
            editor = s.edit();
            editor.putString(KEY_SELECTED_DAYS, selectedDays.toString());
            editor.apply();
        }
    }

    private void loadSelectedDays() {
        String selectedDays = s.getString(KEY_SELECTED_DAYS, "");
        if (!selectedDays.isEmpty()) {
            String[] selectedDaysArray = selectedDays.split(",");
            for (String day : selectedDaysArray) {
                int index = Integer.parseInt(day);
                checkdDays[index] = true;
            }
        }
    }

    public void inicializar(){
        bDiaElegir = findViewById(R.id.bDiaElegir);
        bHoraElegir = findViewById(R.id.bHoraElegir);
        bSeguir = findViewById(R.id.bSeguir);

        tvDiaElegir = findViewById(R.id.tvDiaElegir);
        tvHoraElegir = findViewById(R.id.tvHoraElegir);
        tvEnunciado = findViewById(R.id.tvEnunciado);
        tvFrecuenciaElegir = findViewById(R.id.tvFrecuenciaElegir);

        tvEnunciado.setText(texto);
        formulario = this;
    }
}