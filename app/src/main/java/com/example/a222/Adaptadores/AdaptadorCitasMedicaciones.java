package com.example.a222.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.ClasesGetSet.Cita;
import com.example.a222.ClasesGetSet.Medicacion;
import com.example.a222.R;

import java.util.List;

public class AdaptadorCitasMedicaciones extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int view_type_header = 0;
    private static final int view_type_cita = 1;
    private static final int view_type_medicacion = 2;

    private List <Object> items;

    public AdaptadorCitasMedicaciones(List<Object> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        if (item instanceof String) {
            return view_type_header;
        } else if (item instanceof Cita) {
            return view_type_cita;
        } else if (item instanceof Medicacion) {
            return view_type_medicacion;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType){
            case view_type_header:
                view = inflater.inflate(R.layout.item_header, parent, false);
                return new HeaderViewHolder(view);
            case view_type_cita:
                view = inflater.inflate(R.layout.item_cita, parent, false);
                return new CitaViewHolder(view);
            case view_type_medicacion:
                view = inflater.inflate(R.layout.item_medicacion, parent, false);
                return new MedicacionViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);
        if (item instanceof String) {
            String headerText = (String) item;
            ((HeaderViewHolder) holder).bind(headerText);
        } else if (item instanceof Cita) {
            Cita cita = (Cita) item;
            ((CitaViewHolder) holder).bind(cita);
        } else if (item instanceof Medicacion) {
            Medicacion medicacion = (Medicacion) item;
            ((MedicacionViewHolder) holder).bind(medicacion);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView headerTextView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.headerTextView);
        }

        void bind(String headerText) {
            headerTextView.setText(headerText);
        }
    }

    private static class CitaViewHolder extends RecyclerView.ViewHolder {
        private TextView medicoTextView;
        private TextView diaTextView;
        private TextView horaTextView;

        CitaViewHolder(View itemView) {
            super(itemView);
            medicoTextView = itemView.findViewById(R.id.medicoTextView);
            diaTextView = itemView.findViewById(R.id.diaTextView);
            horaTextView = itemView.findViewById(R.id.horaTextView);
        }

        void bind(Cita cita) {
            medicoTextView.setText(cita.getNombreMedico());
            diaTextView.setText(cita.getDia());
            horaTextView.setText(cita.getHora());
        }
    }

    private static class MedicacionViewHolder extends RecyclerView.ViewHolder {
        private TextView medicamentoTextView;
        private TextView horaTextView;

        MedicacionViewHolder(View itemView) {
            super(itemView);
            medicamentoTextView = itemView.findViewById(R.id.medicamentoTextView);
            horaTextView = itemView.findViewById(R.id.horaTextView);
        }

        void bind(Medicacion medicacion) {
            medicamentoTextView.setText(medicacion.getNombre());
            horaTextView.setText(medicacion.getToma1());
        }
    }
}
