package com.example.a222.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Medico;
import com.example.a222.ClasesGetSet.Sintomas;
import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdaptadorSintomas extends RecyclerView.Adapter<AdaptadorSintomas.SintomasViewHolder> {
    private final List<Sintomas> sintomasList;
    private final Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Sintomas sintomas);
    }

    public AdaptadorSintomas(Context context, List<Sintomas> sintomasList, OnItemClickListener listener) {
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

        /*String id = String.valueOf(sints.getId());
        holder.idSintoma.setText(id);*/
        holder.Sintoma.setText(sints.getTipo());
        holder.horaSintoma.setText(sints.getHora());
        holder.iconTick.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onItemClick(sints);
            }
        });

        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();

        boolean tieneRegistroHoy = tieneRegistroHoy((sints.getTipo()), fechaActual);

        if (tieneRegistroHoy) {
            holder.iconTick.setVisibility(View.VISIBLE);
        } else {
            holder.iconTick.setVisibility(View.GONE);
        }
    }

    private boolean tieneRegistroHoy(String tipo, Date fecha) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaString = sdf.format(fecha);

        String query = "SELECT * FROM sintomas WHERE tipo = ? AND fecha = ?";
        String[] selectionArgs = {(tipo), fechaString};
        Cursor cursor = sql.rawQuery(query, selectionArgs);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count > 0;
    }

    @Override
    public int getItemCount() {return sintomasList.size();}

    public static class SintomasViewHolder extends RecyclerView.ViewHolder{

        TextView Sintoma, horaSintoma;
        ImageView iconTick;
        public SintomasViewHolder(View itemView) {
            super(itemView);
            //idSintoma = itemView.findViewById(R.id.idSintoma);
            Sintoma = itemView.findViewById(R.id.Sintoma);
            horaSintoma = itemView.findViewById(R.id.horaSintoma);
            iconTick = itemView.findViewById(R.id.iconTick);
        }
    }
}
