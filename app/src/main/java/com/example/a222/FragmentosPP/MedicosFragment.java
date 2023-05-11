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
import com.example.a222.FormularioCita_Medico.EditarMedicoActivity;
import com.example.a222.FormularioCita_Medico.MedicoActivity;
import com.example.a222.R;

import java.util.ArrayList;
import java.util.List;

public class MedicosFragment extends Fragment {
    FragmentActivity medico;
    Button botonAnadirMedicoFragment;

    AdminSQLiteOpenHelper db;
    Context context;
    RecyclerView recyclerView;
    List<Medico> medicoList;

    SharedPreferences preferences;
    String nombre;
    String nombreUsu;
    AdaptadorMedico adaptadorMedico;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializar();

        View view = inflater.inflate(R.layout.fragment_medicos, container, false);

        medico = getActivity();

        botonAnadirMedicoFragment = view.findViewById(R.id.botonAnadirMedicoFragment);
        botonAnadirMedicoFragment.setOnClickListener(v -> {
            Intent i = new Intent(medico, MedicoActivity.class);
            startActivity(i);
        });


        //Base de datos
        db = new AdminSQLiteOpenHelper(medico);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        medicoList = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        medicoList.clear();
        consultarMedicos();
        AdaptadorMedico adaptadorMedico = new AdaptadorMedico(context, medicoList, medic -> {
            Intent a = new Intent(getActivity(), EditarMedicoActivity.class);
            a.putExtra("medico", medic.getNombre());
            a.putExtra("especialidad", medic.getEspecialidad());
            startActivity(a);
        });
        recyclerView.setAdapter(adaptadorMedico);
    }

    private void consultarMedicos(){
        SQLiteDatabase base = db.getReadableDatabase();

        preferences = medico.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        nombre = preferences.getString("nombre", "");
        nombreUsu = consultarCorreo(nombre);

        Medico medico = null;
        Cursor cursor = base.rawQuery("Select * from medicos where usuario = ?", new String[]{nombreUsu});

        while(cursor.moveToNext()){
            medico = new Medico();
            medico.setId(cursor.getInt(0));
            medico.setNombre(cursor.getString(1));
            medico.setEspecialidad(cursor.getString(2));
            medico.setHospital(cursor.getString(3));
            medico.setNumero(cursor.getString(4));
            medico.setCorreo(cursor.getString(5));

            medicoList.add(medico);
        }
        if(adaptadorMedico != null){
            adaptadorMedico.notifyDataSetChanged();
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

    public void inicializar(){
        if(getContext() != null){
            context = getContext();
        }

        //Nombre toolbar
        if(getActivity() != null){
            getActivity().setTitle("Médicos");
            medico = getActivity();
        }
    }
}