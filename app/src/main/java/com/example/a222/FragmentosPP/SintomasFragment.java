package com.example.a222.FragmentosPP;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a222.Adaptadores.AdaptadorCitas;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.FormularioCita_Medico.CitaActivity;
import com.example.a222.FormularioCita_Medico.EditarCitasActivity;
import com.example.a222.FormularioCita_Medico.SintomasActivity;
import com.example.a222.R;

import java.util.ArrayList;

public class SintomasFragment extends Fragment {

    Context context;
    AdminSQLiteOpenHelper db;
    RecyclerView recyclerSintomas;
    Button bAnadirSintomas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Container del fragmento
        View view = inflater.inflate(R.layout.fragment_sintomas, container, false);

        bAnadirSintomas = view.findViewById(R.id.bAnadirSintomas);
        bAnadirSintomas.setOnClickListener(v -> {
            Intent nuevoSintoma = new Intent(context, SintomasActivity.class);
            startActivity(nuevoSintoma);
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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