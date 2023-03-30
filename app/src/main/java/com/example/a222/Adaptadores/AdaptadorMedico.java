package com.example.a222.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.ClasesGetSet.Medico;
import com.example.a222.R;

import java.util.List;

public class AdaptadorMedico extends RecyclerView.Adapter<AdaptadorMedico.MedicoViewHolder> {
    private final List<Medico> medicoList;

    public AdaptadorMedico(Context mCtx, List<Medico>medicoList){
        //Adaptador para el recycler view de los medicos
        this.medicoList = medicoList;
    }

    @Override
    public MedicoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_medicos, parent, false);
        return new MedicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicoViewHolder holder, int position){
        Medico medico = medicoList.get(position);

        holder.nomMedicoCitaElement.setText(medico.getNombre());
        holder.especialidadElement.setText(medico.getEspecialidad());
        holder.hospitalElement.setText(medico.getHospital());

    }

    @Override
    public int getItemCount() {
        return medicoList.size();
    }

    public static class MedicoViewHolder extends RecyclerView.ViewHolder{
        TextView nomMedicoCitaElement, especialidadElement, hospitalElement;

        public MedicoViewHolder(View view) {
            super(view);

            nomMedicoCitaElement = view.findViewById(R.id.nombreUsuario);
            especialidadElement = view.findViewById(R.id.correoUsuario);
            hospitalElement = view.findViewById(R.id.hospitalElement);


        }
    }
}
