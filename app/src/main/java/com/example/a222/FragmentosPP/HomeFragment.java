package com.example.a222.FragmentosPP;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView recyclerView;
    private CalendarAdapter mAdapter;
    TextView tvHora1Toma;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState){

        getActivity().setTitle("Inicio");
        View layout = inflater.inflate(R.layout.fragment_homee, container, false);
       // layout.findViewById(R.id.imagenView).setBackgroundResource(getArguments().getInt(ARG_ICON));

        //Calendario
        recyclerView = layout.findViewById(R.id.recycler_view);

        mAdapter = new CalendarAdapter(calendarList);

        recyclerView.setHasFixedSize(true);


        // horizontal RecyclerView
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.addItemDecoration(new DividerItemDecoration(layout.getContext(), LinearLayoutManager.HORIZONTAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int totalItemCount = mLayoutManager.getChildCount();
                        for (int i = 0; i < totalItemCount; i++){
                            View childView = recyclerView.getChildAt(i);
                            TextView childTextView = (TextView) (childView.findViewById(R.id.day_1));
                            String childTextViewText = (String) (childTextView.getText());
/*Este codigo cambia el dia de domingo a rojo pero no lo necesitamos
                            if (childTextViewText.equals("Sun"))
                                childTextView.setTextColor(Color.RED);
                            else
                                childTextView.setTextColor(Color.BLACK);*/
                        }
                    }
                });
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
                Toast.makeText(getContext(), calendar.getDay() +"/" + calendar.getMonth() + "", Toast.LENGTH_SHORT).show();

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
        return layout;
    }

    //Calendario
    private int getCurrentItem(){
        return ((LinearLayoutManager)recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
    }

    private void setCurrentItem(int position, int incr){
        position=position+incr;

        if (position <0)
            position=0;

        recyclerView.smoothScrollToPosition(position);
    }

    private void prepareCalendarData() {
        Calendar today = Calendar.getInstance();
        myCalendarData m_calendar = new myCalendarData(-2);

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
        mAdapter.notifyDataSetChanged();
    }




}

