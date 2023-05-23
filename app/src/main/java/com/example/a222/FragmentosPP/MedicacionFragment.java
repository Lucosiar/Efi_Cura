package com.example.a222.FragmentosPP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.Adaptadores.AdaptadorMedicacion;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Medicacion;
import com.example.a222.FormularioCita_Medico.MedicacionActivity;
import com.example.a222.FormularioMedicacion.Formulario1Activity;
import com.example.a222.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicacionFragment extends Fragment {

    Button botonAnadirMedicacionFragment;

    List<Medicacion>medicacionList;
    RecyclerView recyclerView;

    Context context;
    FragmentActivity medicaciones;
    AdminSQLiteOpenHelper db;
    String notaComida;
    int cantidad;
    String formato;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inicializar();

        //Container del fragmento
        View view = inflater.inflate(R.layout.fragment_medicacion, container, false);

        //Boton inferior para añadir nuevos medicamentos
        botonAnadirMedicacionFragment = view.findViewById(R.id.botonAnadirMedicoFragment);
        botonAnadirMedicacionFragment.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), Formulario1Activity.class);
            startActivity(i);
        });

        //Base de datos de medicacion
        db = new AdminSQLiteOpenHelper(medicaciones);

        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        medicacionList = new ArrayList<>();

        PaginaPrincipal pagina = (PaginaPrincipal) getActivity();
        FloatingActionButton fab = pagina.floatingActionButton;
        fab.setVisibility(View.GONE);

        return view;
    }

    private void consultarMedicacion(){
        SQLiteDatabase base = db.getReadableDatabase();

        Medicacion medicacion;

        Cursor cursor = base.rawQuery("Select * from medicacion", null);

        while(cursor.moveToNext()){
            medicacion = new Medicacion();
            medicacion.setNombre(cursor.getString(0));
            medicacion.setCantidadDiaria((cursor.getString(1)));
            formato = cursor.getString(11);
            cantidad = Integer.parseInt(cursor.getString(1));
            if(cantidad >= 2){
                medicacion.setFormato(formato + "s");
            }else{
                medicacion.setFormato(formato);
            }
            medicacion.setToma1((cursor.getString(6)));
            medicacion.setNotaComida((cursor.getString(12)));


            if(cursor.getString(5).equals(("Cuando lo necesite"))){
                notaComida = "Cuando lo necesite";
                medicacion.setToma1(notaComida);
            }else{
                medicacion.setToma1((cursor.getString(5)));
            }
            medicacionList.add(medicacion);

        }
        cursor.close();
    }

    public void inicializar(){
        //Nombre toolbar
        if(getActivity() != null){
            getActivity().setTitle("Medicación");
            medicaciones = getActivity();
        }

        if(getContext() != null){
            context = getContext();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        medicacionList.clear();
        consultarMedicacion();
        AdaptadorMedicacion adaptadorMedicacion = new AdaptadorMedicacion(context, medicacionList, medica -> {
            Intent intent = new Intent(getActivity(), MedicacionActivity.class);
            intent.putExtra("nombre",  medica.getNombre());
            startActivity(intent);
        });
        recyclerView.setAdapter(adaptadorMedicacion);
    }
}