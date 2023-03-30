package com.example.a222.Calendario;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.R;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private final List<MyCalendar> mCalendar;
    private int recyclecount=0;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tb_day;
        private final TextView tb_date;


        public MyViewHolder(View view) {
            super(view);
            tb_day = (TextView) view.findViewById(R.id.day_1);
            tb_date = (TextView) view.findViewById(R.id.date_1);
        }
    }


    public CalendarAdapter(List<MyCalendar> mCalendar) {
        this.mCalendar = mCalendar;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyCalendar calendar = mCalendar.get(position);

        holder.tb_day.setText(calendar.getDay());
        holder.tb_date.setText(calendar.getDate());
        int textColor = calendar.getTextColor();
        if(textColor != 0){
            holder.tb_date.setTextColor(Color.RED);
            holder.tb_day.setTextColor(Color.RED);
        }
        else{
            holder.tb_day.setTextColor(Color.BLACK);
            holder.tb_date.setTextColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        return mCalendar.size();
    }

    @Override
    public void onViewRecycled (MyViewHolder holder){

        recyclecount++;

    }
}

