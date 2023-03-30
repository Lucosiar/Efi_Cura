package com.example.a222.FragmentosPP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.a222.Adaptadores.AdaptadorCitas;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Cita;
import com.example.a222.FormularioCita_Medico.CitaActivity;
import com.example.a222.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CitasFragment extends Fragment{

    //Fragmento que muestra el recyclerview con todas las citas

    List<Cita> citaList;
    RecyclerView recyclerView;

    SharedPreferences preferences;
    String nombre;
    String nombreUsuario;

    Button botonAnadirCita;
    Context context;
    FragmentActivity citas;
    AdminSQLiteOpenHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializar();

        //Container del fragmento
        View view = inflater.inflate(R.layout.fragment_citas, container, false);

        //Boton inferior para añadir una cita nueva
        botonAnadirCita = view.findViewById(R.id.botonAnadirMedicoFragment);

        //new View.OnClickListener() == v->
        botonAnadirCita .setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CitaActivity.class);
            startActivity(i);
        });

        //Instanciar base de datos
        db = new AdminSQLiteOpenHelper(citas);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        citaList = new ArrayList<>();

        consultarCitas();

        AdaptadorCitas adaptadorCitas = new AdaptadorCitas(context, citaList);
        recyclerView.setAdapter(adaptadorCitas);

        return view;
    }

    private void inicializar(){
        //Nombre de la toolbar
        getActivity().setTitle("Citas");
        citas = getActivity();
    }

    private void ordenarCitas(){
        Collections.sort(citaList, Comparator.comparing(Cita::getDia));
    }

    private void consultarCitas(){
        SQLiteDatabase base = db.getReadableDatabase();

        preferences = citas.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        nombre = preferences.getString("nombre", "");
        nombreUsuario = consultarCorreo(nombre);

        Cita cita;

        Cursor cursor = base.rawQuery("Select * from citas where usuario = ?",new String[]{nombreUsuario});

        while(cursor.moveToNext()) {
            cita = new Cita();
            cita.setNombreMedico(cursor.getString(0));
            cita.setDia(cursor.getString(1));
            cita.setHora(cursor.getString(2));

            citaList.add(cita);
        }
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
}