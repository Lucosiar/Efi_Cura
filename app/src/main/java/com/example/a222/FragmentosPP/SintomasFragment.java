package com.example.a222.FragmentosPP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a222.Adaptadores.AdaptadorMedico;
import com.example.a222.Adaptadores.AdaptadorSintomas;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Sintomas;
import com.example.a222.FormularioCita_Medico.EditarMedicoActivity;
import com.example.a222.FormularioCita_Medico.EditarSintomasActivity;
import com.example.a222.FormularioCita_Medico.SintomasActivity;
import com.example.a222.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SintomasFragment extends Fragment {

    Context context;
    AdminSQLiteOpenHelper db;
    AdaptadorSintomas adaptadorSintomas;
    SharedPreferences s;
    FragmentActivity sintomas;
    RecyclerView recyclerSintomas;
    Button bAnadirSintomas;
    List<Sintomas> sintomasList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Síntomas");
        sintomas = getActivity();

        //Container del fragmento
        View view = inflater.inflate(R.layout.fragment_sintomas, container, false);

        bAnadirSintomas = view.findViewById(R.id.bAnadirSintomas);
        bAnadirSintomas.setOnClickListener(v -> {
            Intent nuevoSintoma = new Intent(context, SintomasActivity.class);
            startActivity(nuevoSintoma);
        });

        recyclerSintomas = view.findViewById(R.id.recyclerSintomas);
        recyclerSintomas.setLayoutManager(new LinearLayoutManager(context));

        sintomasList = new ArrayList<>();
        db = new AdminSQLiteOpenHelper(sintomas);

        PaginaPrincipal pagina = (PaginaPrincipal) getActivity();
        FloatingActionButton fab = pagina.floatingActionButton;
        fab.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        sintomasList.clear();
        consultarSintomas();
        AdaptadorSintomas adaptadorSintomas = new AdaptadorSintomas(context, sintomasList, sintoma -> {
            Intent a = new Intent(getActivity(), EditarSintomasActivity.class);
            a.putExtra("tipo", sintoma.getTipo());
            a.putExtra("id", sintoma.getId());
            startActivity(a);
        });
        recyclerSintomas.setAdapter(adaptadorSintomas);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void consultarSintomas(){
        SQLiteDatabase base = db.getReadableDatabase();
        s = sintomas.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        StringBuilder numeros = new StringBuilder();
        for(int i = 1; i<=31; i++){
            numeros.append("'").append(i).append("', ");
        }

        numeros.setLength((numeros.length()) - 2);

        String dias = ", 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'";
        String resultado = numeros + dias;

        Sintomas sintomas = null;
        Cursor cursor = base.rawQuery("Select * from sintomas where usuario = ? and (fecha = '*' or fecha in (" + resultado + "))", new String[]{nombreUsuario});

        while(cursor.moveToNext()){
            sintomas = new Sintomas();
            sintomas.setId(cursor.getInt(0));
            sintomas.setTipo(cursor.getString(1));
            sintomas.setHora(cursor.getString(2));
            sintomas.setFecha(cursor.getString(3));
            sintomas.setAlta(cursor.getInt(4));
            sintomas.setBaja(cursor.getInt(5));
            sintomas.setDolor(cursor.getInt(6));

            sintomasList.add(sintomas);

            String fecha = cursor.getString(3);
            String hora = cursor.getString(2);

            if(fecha.equals("*")){
                programarNotificacion(hora);
            }

        }
        Log.d(":::ERROR:", ("Número de síntomas: " + sintomasList.size()));
        if(adaptadorSintomas != null){
            adaptadorSintomas.notifyDataSetChanged();
        }

    }

    private void programarNotificacion(String hora) {
        // Aquí debes implementar la lógica para programar la notificación una hora antes de la hora del síntoma.
        // Puedes utilizar las APIs de notificaciones de Android, como NotificationManager y AlarmManager,
        // para programar y mostrar la notificación en el momento deseado.
        // Aquí solo se muestra un ejemplo básico de cómo programar una notificación.

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Obtener la hora y minutos del síntoma
        int horaSintoma = Integer.parseInt(hora.split(":")[0]);
        int minutoSintoma = Integer.parseInt(hora.split(":")[1]);
        // Calcular la hora y minutos para la notificación (1 hora antes del síntoma)
        int horaNotificacion = horaSintoma - 1;
        int minutoNotificacion = minutoSintoma;

        // Si la hora de la notificación es menor a 0, restar un día y ajustar la hora
        if (horaNotificacion < 0) {
            horaNotificacion += 24;
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        // Establecer la hora y minutos para la notificación
        calendar.set(Calendar.HOUR_OF_DAY, horaNotificacion);
        calendar.set(Calendar.MINUTE, minutoNotificacion);
        calendar.set(Calendar.SECOND, 0);

        // Aquí debes implementar la lógica para mostrar la notificación en el momento deseado.
        // Puedes utilizar la clase NotificationManager para mostrar la notificación.

        // Ejemplo de cómo mostrar una notificación básica
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_medicacion)
                .setContentTitle("Recordatorio de síntoma")
                .setContentText("Tienes un síntoma programado en una hora.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        int notificationId = 1; // ID de la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
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
}