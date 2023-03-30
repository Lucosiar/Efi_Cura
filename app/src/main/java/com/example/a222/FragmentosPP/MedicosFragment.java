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

import com.example.a222.Adaptadores.AdaptadorMedico;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Medico;
import com.example.a222.FormularioCita_Medico.MedicoActivity;
import com.example.a222.R;

import java.util.ArrayList;
import java.util.List;

public class MedicosFragment extends Fragment {
    FragmentActivity medic;
    Button botonAnadirMedicoFragment;

    AdminSQLiteOpenHelper db;
    Context context;
    RecyclerView recyclerView;
    List<Medico> listaMedicos;

    SharedPreferences preferences;
    String nombre;
    String nombreUsu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Nombre toolbar
        getActivity().setTitle("Médicos");

        View view = inflater.inflate(R.layout.fragment_medicos, container, false);

        medic = getActivity();

        botonAnadirMedicoFragment = view.findViewById(R.id.botonAnadirMedicoFragment);
        botonAnadirMedicoFragment.setOnClickListener(v -> {
            Intent i = new Intent(medic, MedicoActivity.class);
            startActivity(i);
        });


        //Base de datos
        db = new AdminSQLiteOpenHelper(medic);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        listaMedicos = new ArrayList<>();

        consultarMedicos();

        AdaptadorMedico adaptadorMedico = new AdaptadorMedico(context, listaMedicos);
        recyclerView.setAdapter(adaptadorMedico);

        return view;
    }

    private void consultarMedicos(){
        SQLiteDatabase base = db.getReadableDatabase();

        preferences = medic.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        nombre = preferences.getString("nombre", "");
        nombreUsu = consultarCorreo(nombre);

        Medico medico = null;
        Cursor cursor = base.rawQuery("Select * from medicos where usuario = ?", new String[]{nombreUsu});

        while(cursor.moveToNext()){
            medico = new Medico();
            medico.setNombre(cursor.getString(0));
            medico.setEspecialidad(cursor.getString(1));
            medico.setHospital(cursor.getString(2));

            listaMedicos.add(medico);
        }
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