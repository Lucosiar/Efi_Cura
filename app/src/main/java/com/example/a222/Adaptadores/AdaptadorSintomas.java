package com.example.a222.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.ClasesGetSet.Medico;
import com.example.a222.ClasesGetSet.Sintomas;
import com.example.a222.R;

import java.util.List;

public class AdaptadorSintomas extends RecyclerView.Adapter<AdaptadorSintomas.SintomasViewHolder> {
    private final List<Sintomas> sintomasList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Sintomas sintomas);
    }

    public AdaptadorSintomas(Context context, List<Sintomas> sintomasList) {
        this.sintomasList = sintomasList;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public AdaptadorSintomas.SintomasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_sintomas, parent, false);
        return new SintomasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SintomasViewHolder holder, int position) {
        Sintomas sints = sintomasList.get(position);

        int id = sints.getId();
        holder.idSintoma.setText(id);
        holder.Sintoma.setText(sints.getTipo());
        holder.horaSintoma.setText(sints.getHora());

        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(sints);
            }
        });
    }

    @Override
    public int getItemCount() {return sintomasList.size();}

    public static class SintomasViewHolder extends RecyclerView.ViewHolder{

        TextView idSintoma, Sintoma, horaSintoma;
        public SintomasViewHolder(View itemView) {
            super(itemView);
            idSintoma = itemView.findViewById(R.id.idSintoma);
            Sintoma = itemView.findViewById(R.id.Sintoma);
            horaSintoma = itemView.findViewById(R.id.horaSintoma);
        }
    }
}
