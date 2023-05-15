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
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Medico medico);
    }

    public AdaptadorMedico(Context context, List<Medico>medicoList, OnItemClickListener listener){
        //Adaptador para el recycler view de los medicos
        this.medicoList = medicoList;
        this.context = context;
        this.listener = listener;
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

        String id = String.valueOf(medico.getId());
        holder.idMedico.setText(id);
        holder.nombreMedico.setText(medico.getNombre());
        holder.Especialidad_medico.setText(medico.getEspecialidad());

        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(medico);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicoList.size();
    }

    public static class MedicoViewHolder extends RecyclerView.ViewHolder{
        TextView idMedico, nombreMedico, Especialidad_medico;

        public MedicoViewHolder(View view) {
            super(view);

            idMedico = view.findViewById(R.id.idMedico);
            nombreMedico = view.findViewById(R.id.nombreMedico);
            Especialidad_medico = view.findViewById(R.id.Especialidad_medico);


        }
    }
}
