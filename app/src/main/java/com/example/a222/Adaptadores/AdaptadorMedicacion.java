package com.example.a222.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.ClasesGetSet.Medicacion;
import com.example.a222.R;
import java.util.List;

public class AdaptadorMedicacion extends RecyclerView.Adapter<AdaptadorMedicacion.MedicacionViewHolder> {
    private final List<Medicacion> medicacionList;

    public AdaptadorMedicacion(Context mCtx, List<Medicacion>medicacionList) {
        //Adaptador para el recycler view de las medicacion
        this.medicacionList = medicacionList;
    }

    @Override
    public MedicacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_medicacion, parent, false);
        return new MedicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicacionViewHolder holder, int position) {
        Medicacion medicacion = medicacionList.get(position);

        holder.nomMedicacion.setText(medicacion.getNombre());
        holder.cantidadDiariaMedicacion.setText(medicacion.getCantidadDiaria());
        holder.formatoMedicacion.setText(medicacion.getFormato());
        holder.horaMedicacion.setText(medicacion.getToma1());
        holder.diaMedicacion.setText(medicacion.getFrecuencia());
    }

    @Override
    public int getItemCount() {
       return medicacionList.size();
    }

    public static class MedicacionViewHolder extends RecyclerView.ViewHolder{
        TextView nomMedicacion, cantidadDiariaMedicacion, formatoMedicacion, diaMedicacion, horaMedicacion;
        public MedicacionViewHolder(View view) {
            super(view);

            //Definimos el click listener para el view holder
            nomMedicacion = view.findViewById(R.id.nomMedicacion);
            cantidadDiariaMedicacion = view.findViewById(R.id.cantidadDiariaMedicacion);
            formatoMedicacion = view.findViewById(R.id.formatoMedicacion);
            horaMedicacion = view.findViewById(R.id.horaMedicacion);
            diaMedicacion = view.findViewById(R.id.diaMedicacion);
        }
    }
}
