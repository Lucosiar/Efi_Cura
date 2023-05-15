package com.example.a222.FragmentosPP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.Adaptadores.AdaptadorCitas;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Cita;
import com.example.a222.FormularioCita_Medico.CitaActivity;
import com.example.a222.FormularioCita_Medico.EditarCitasActivity;
import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CitasFragment extends Fragment{

    //Fragmento que muestra el recyclerview con todas las citas;
    RecyclerView recyclerView;
    Button botonAnadirCita;
    Context context;
    FragmentActivity citas;
    AdminSQLiteOpenHelper db;
    SharedPreferences s;
    List<Cita> citaList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializar();

        //Container del fragmento
        View view = inflater.inflate(R.layout.fragment_citas, container, false);

        botonAnadirCita = view.findViewById(R.id.botonAnadirMedicoFragment);
        botonAnadirCita .setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CitaActivity.class);
            startActivity(i);
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        citaList = new ArrayList<>();

        AdaptadorCitas adaptadorCitas = new AdaptadorCitas(context, citaList, citas ->{
           Intent i = new Intent(getActivity(), EditarCitasActivity.class);
           //i.putExtra("nombreCita", citas.getNombreMedico());
           startActivity(i);
        });

        recyclerView.setAdapter(adaptadorCitas);

        consultarCitasFuturas(context);

        return view;
    }

    private void inicializar(){
        //Nombre de la toolbar
        getActivity().setTitle("Citas");
        citas = getActivity();
    }

    private void consultarCitasFuturas(Context context){
        db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fechaActual = sdf.format(new Date());

        s = citas.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        Cursor cursor = sql.rawQuery("Select * from citas where dia > ? and usuario = ? ORDER BY dia ASC", new String[]{fechaActual, nombreUsuario});

        citaList.clear();

        while(cursor.moveToNext()){
            Cita cita = new Cita();

            cita.setNombreMedico(cursor.getString(1));
            cita.setDia(cursor.getString(2));
            cita.setHora(cursor.getString(3));

            citaList.add(cita);
        }
        cursor.close();
        sql.close();
        Log.d(":::TAG:", "Numero de citas: " + citaList.size());
        ordenarCitas();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


   private void ordenarCitas(){
        Collections.sort(citaList, Comparator.comparing(Cita::getDia));
    }

/*    private void consultarCitas(){
        citaList.clear();
        SQLiteDatabase base = db.getReadableDatabase();

        preferences = citas.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        nombre = preferences.getString("nombre", "");
        nombreUsuario = consultarCorreo(nombre);

        Cita cita;

        Cursor cursor = base.rawQuery("Select * from citas where usuario = ?",new String[]{nombreUsuario});

        while(cursor.moveToNext()) {
            cita = new Cita();

            cita.setNombreMedico(cursor.getString(1));
            cita.setDia(cursor.getString(2));
            cita.setHora(cursor.getString(3));

            citaList.add(cita);
        }
        ordenarCitas();
        checkBoxMostrarTodos.setChecked(false);
    }*/
}