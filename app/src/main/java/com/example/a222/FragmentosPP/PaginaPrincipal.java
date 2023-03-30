package com.example.a222.FragmentosPP;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.Ajustes_AcercaDe.AjustesActivity;
import com.example.a222.Ajustes_AcercaDe.CompartirActivity;
import com.example.a222.Calendario.CalendarAdapter;
import com.example.a222.Calendario.MyCalendar;
import com.example.a222.Calendario.RecyclerTouchListener;
import com.example.a222.Calendario.myCalendarData;
import com.example.a222.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PaginaPrincipal extends AppCompatActivity {

    private final List<MyCalendar> calendarList= new ArrayList<>();
    private RecyclerView recyclerView;
    private CalendarAdapter mAdapter;

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        this.setTitle("Efi-Cura");

        //menu
        //Boton de navegación
        bottomNavigationView = findViewById(R.id.bottonNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Inicio:
                    reemplazar(new HomeFragment());
                    break;
                case R.id.Citas:
                    reemplazar(new CitasFragment());
                    break;
                case R.id.Medicación:
                    reemplazar(new MedicacionFragment());
                    break;
            }
            return true;
        });


        //Calendario

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new CalendarAdapter(calendarList);

        recyclerView.setHasFixedSize(true);


        // horizontal RecyclerView

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

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
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                TextView childTextView = (view.findViewById(R.id.day_1));

                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(getApplicationContext());
                childTextView.startAnimation(startRotateAnimation);
                childTextView.setTextColor(Color.CYAN);
                Toast.makeText(getApplicationContext(), calendar.getDay() +"/" + calendar.getMonth() + "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);

                TextView childTextView = (view.findViewById(R.id.day_1));
                childTextView.setTextColor(Color.GREEN);

                Toast.makeText(getApplicationContext(), calendar.getDate()+"/" + calendar.getDay()+"/" +calendar.getMonth()+"", Toast.LENGTH_SHORT).show();

            }
        }));

        prepareCalendarData();
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

    //Reemplazar fragmentos
    private void reemplazar(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    //Menu de arriba con las opciones y los 3 puntos
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_arriba, menu);
        return super.onCreateOptionsMenu(menu);

    }

    //Top Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.MedicosA:
                reemplazar(new MedicosFragment());
                break;
            case R.id.MedicaciónA:
                reemplazar(new MedicacionFragment());
                break;
            case R.id.CitasA:
                reemplazar(new CitasFragment());
                break;
            case R.id.Ajustes:
                Intent i = new Intent(this, AjustesActivity.class);
                startActivity(i);
                break;
            case R.id.Compartir:
                Intent ux = new Intent(this, CompartirActivity.class);
                startActivity(ux);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}