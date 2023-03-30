package com.example.a222.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.ClasesGetSet.Cita;
import com.example.a222.R;

import java.util.List;

public class AdaptadorCitas extends RecyclerView.Adapter<AdaptadorCitas.CitasViewHolder>{
    private final List<Cita> citaList;

    public AdaptadorCitas(Context mCtx, List<Cita>citaList){
        //Adaptador para el recycler view de las citas
        this.citaList = citaList;
    }

    @Override
    public CitasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_citas, parent, false);
        return new CitasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CitasViewHolder holder, int position) {
        Cita cita = citaList.get(position);

        holder.tvNombreMedico.setText(cita.getNombreMedico());
        holder.tvDia.setText(cita.getDia());
        holder.tvHora.setText(cita.getHora());
    }

    @Override
    public int getItemCount() {
        return citaList.size();
    }


    public static class CitasViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombreMedico, tvDia, tvHora;

        public CitasViewHolder(View view) {
            super(view);

            //Definimos el click listener para el viewHolder View
           tvNombreMedico = view.findViewById(R.id.nombreUsuario);
           tvDia = view.findViewById(R.id.correoUsuario);
           tvHora = view.findViewById(R.id.HoraCitaElement);
        }


    }
}
