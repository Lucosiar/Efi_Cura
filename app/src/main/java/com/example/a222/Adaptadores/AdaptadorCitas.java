package com.example.a222.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.ClasesGetSet.Cita;
import com.example.a222.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdaptadorCitas extends RecyclerView.Adapter<AdaptadorCitas.CitasViewHolder>{
    private final List<Cita> citaList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Cita cita);
    }
    public AdaptadorCitas(Context context, List<Cita>citaList, OnItemClickListener listener){
        //Adaptador para el recycler view de las citas
        this.citaList = citaList;
        this.context = context;
        this.listener = listener;
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

        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(cita);
            }
        });
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
           tvNombreMedico = view.findViewById(R.id.idMedico);
           tvDia = view.findViewById(R.id.nombreMedico);
           tvHora = view.findViewById(R.id.HoraCitaElement);
        }
    }
}
