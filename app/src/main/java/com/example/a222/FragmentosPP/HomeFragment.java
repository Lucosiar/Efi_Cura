package com.example.a222.FragmentosPP;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.Calendario.CalendarAdapter;
import com.example.a222.Calendario.MyCalendar;
import com.example.a222.Calendario.RecyclerTouchListener;
import com.example.a222.Calendario.myCalendarData;
import com.example.a222.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private final List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;
    TextView tvHora1Toma, tvHora2Toma, tvHora3Toma, tvHora4Toma;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_homee, container, false);

        if (getActivity() != null) {getActivity().setTitle("Inicio");}

        tvHora4Toma = view.findViewById(R.id.tvHora4Toma);
        tvHora3Toma = view.findViewById(R.id.tvHora3Toma);
        tvHora2Toma = view.findViewById(R.id.tvHora2Toma);
        tvHora1Toma = view.findViewById(R.id.tvHora1Toma);

        //Calendario
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new CalendarAdapter(calendarList);
        recyclerView.setHasFixedSize(true);

        // horizontal RecyclerView
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                TextView childTextView = (view.findViewById(R.id.day_1));

                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(getContext());
                childTextView.startAnimation(startRotateAnimation);
                childTextView.setTextColor(Color.CYAN);
                Toast.makeText(getContext(), calendar.getDate()+"/" + calendar.getDay()+"/" +calendar.getMonth()+"", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);

                TextView childTextView = (view.findViewById(R.id.day_1));
                childTextView.setTextColor(Color.GREEN);

                Toast.makeText(getContext(), calendar.getDate()+"/" + calendar.getDay()+"/" +calendar.getMonth()+"", Toast.LENGTH_SHORT).show();

            }
        }));

        prepareCalendarData();
        return view;
    }


    private void prepareCalendarData() {
        Calendar today = Calendar.getInstance();
        myCalendarData m_calendar = new myCalendarData(-1);

        for (int i = 0; i < 30; i++) {

            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(),
                    String.valueOf(m_calendar.getDay()),
                    String.valueOf(m_calendar.getMonth()),
                    String.valueOf(m_calendar.getYear()), i);

           if(m_calendar.getDay() == today.get(Calendar.DAY_OF_MONTH)
                    && m_calendar.getMonth() == today.get(Calendar.MONTH)){
                calendar.setTextColor(Color.WHITE);
            }

            m_calendar.getNextWeekDay(1);
            calendarList.add(calendar);
        }
        int size = calendarList.size();
        mAdapter.notifyItemRangeChanged(size - 30, size);
    }

}

