package com.example.a222.FragmentosPP;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a222.Ajustes_AcercaDe.AjustesActivity;
import com.example.a222.Ajustes_AcercaDe.CompartirActivity;
import com.example.a222.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class PaginaPrincipal extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        this.setTitle("Efi-Cura");

        //menu
        //Boton de navegación
        bottomNavigationView = findViewById(R.id.bottonNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

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
        } else if (id == R.id.Ajustes) {
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