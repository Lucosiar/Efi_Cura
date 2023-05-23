package com.example.a222.FragmentosPP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a222.Ajustes_AcercaDe.AjustesActivity;
import com.example.a222.Ajustes_AcercaDe.CompartirActivity;
import com.example.a222.FormularioCita_Medico.CitaActivity;
import com.example.a222.FormularioCita_Medico.SintomasActivity;
import com.example.a222.FormularioMedicacion.Formulario1Activity;
import com.example.a222.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PaginaPrincipal extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Context context;
    Activity activity;

    HomeFragment homeFragment = new HomeFragment();

    FloatingActionButton floatingActionButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        this.setTitle("Efi-Cura");

        //menu
        //Boton de navegación
        bottomNavigationView = findViewById(R.id.bottonNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        context = this;
        activity = this;

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, floatingActionButton);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.opcionMedicacion:
                        Intent medicacion = new Intent(activity, Formulario1Activity.class);
                        startActivity(medicacion);
                        return true;
                    case R.id.opcionSintomas:
                        Intent sintomas = new Intent(activity, SintomasActivity.class);
                        startActivity(sintomas);
                        return true;
                    case R.id.opcionCitas:
                        Intent citas = new Intent(activity, CitaActivity.class);
                        startActivity(citas);
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.Inicio) {
                reemplazar(new HomeFragment());
                return true;
            } else if (id == R.id.Citas) {
                reemplazar(new CitasFragment());
                return true;
            } else if (id == R.id.Medicación) {
                reemplazar(new MedicacionFragment());
                return true;
            }else if(id == R.id.Sintomas){
                reemplazar(new SintomasFragment());
                return true;
            }
            return false;
        });
    }

    //Reemplazar fragmentos
    private void reemplazar(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    //Menu de arriba con las opciones y los 3 puntos
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_arriba, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Top Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.MedicosA) {
            reemplazar(new MedicosFragment());
            return true;
        } else if (id == R.id.MedicaciónA) {
            reemplazar(new MedicacionFragment());
            return true;
        } else if (id == R.id.CitasA) {
            reemplazar(new CitasFragment());
            return true;
        }else if(id == R.id.SintomasA){
            reemplazar(new SintomasFragment());
            return true;
        }else if (id == R.id.Ajustes) {
            Intent i = new Intent(this, AjustesActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.Compartir) {
            Intent ux = new Intent(this, CompartirActivity.class);
            startActivity(ux);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}