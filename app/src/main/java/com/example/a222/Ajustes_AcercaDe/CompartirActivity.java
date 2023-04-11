package com.example.a222.Ajustes_AcercaDe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a222.R;

public class CompartirActivity extends AppCompatActivity {

    ImageButton iconLinkedin, iconInstagram, iconGithub, iconTwitter, ibAtras;
    ImageView ibDescarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);

        inicializar();
    }


    public void clickar(View view){
        Intent intent = null;
        int id = view.getId();

        if (id == R.id.iconLinkedin) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/lucia-cosio-artime-b02783222"));
        } else if (id == R.id.iconInstragram) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/3cosi3/"));
        } else if (id == R.id.iconGithub) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Lucosiar"));
        } else if (id == R.id.iconTwitter) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        }
        startActivity(intent);
    }


    public void inicializar(){
        //Titulo
        this.setTitle("Efi-Cura");

        iconLinkedin = findViewById(R.id.iconLinkedin);
        iconInstagram = findViewById(R.id.iconInstragram);
        iconGithub = findViewById(R.id.iconGithub);
        iconTwitter = findViewById(R.id.iconTwitter);
        ibAtras = findViewById(R.id.ibAtras);
        ibDescarga = findViewById(R.id.ibDescarga);
    }
}