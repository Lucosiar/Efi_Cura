package com.example.a222.FragmentosPP;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.Adaptadores.AdaptadorCitasMedicaciones;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.Calendario.CalendarAdapter;
import com.example.a222.Calendario.MyCalendar;
import com.example.a222.Calendario.RecyclerTouchListener;
import com.example.a222.Calendario.myCalendarData;
import com.example.a222.ClasesGetSet.Cita;
import com.example.a222.ClasesGetSet.Medicacion;
import com.example.a222.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private final List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;
    RecyclerView recyclerViewId;
    Context context;
    AdminSQLiteOpenHelper db;
    SharedPreferences s;
    FragmentActivity home;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_homee, container, false);

        recyclerViewId = view.findViewById(R.id.recyclerViewId);
        home = getActivity();

        if (getActivity() != null) {getActivity().setTitle("Inicio");}

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

    private void mostrarTodo(){
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fechaActual = sdf.format(today.getTime());

        List <Cita> citasHoyList = consultarCitas(fechaActual);
        List <Medicacion> medicacionHoyList = consultarMedicaciones(fechaActual);

        List <Object> items = new ArrayList<>();

        if(!citasHoyList.isEmpty()){
            items.add("Citas de hoy");
            items.addAll(citasHoyList);
        }

        if(!medicacionHoyList.isEmpty()){
            items.add("Medicaciones de hoy");
            items.addAll(medicacionHoyList);
        }

        recyclerViewId.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewId.setAdapter(new AdaptadorCitasMedicaciones(items));

    }

    private List<Medicacion> consultarMedicaciones(String fechaActual){
        List<Medicacion> medicacionList = new ArrayList<>();

        db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getReadableDatabase();


        s = home.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        Cursor cursor = sql.rawQuery("Select * from medicaciones where usuario = ?", new String[]{nombreUsuario});

        while (cursor.moveToNext()) {
            Medicacion medicacion = new Medicacion();

            medicacion.setNombre(cursor.getString(1));
            medicacion.setToma1(cursor.getString(2));

            medicacionList.add(medicacion);
        }

        cursor.close();
        sql.close();

        return medicacionList;

    }

    private List<Cita> consultarCitas(String fechaActual){
        List<Cita> citaList = new ArrayList<>();

        db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getReadableDatabase();


        s = home.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        Cursor cursor = sql.rawQuery("Select * from citas where fecha = ? and usuario = ?", new String[]{fechaActual, nombreUsuario});

        while (cursor.moveToNext()) {
            Cita cita = new Cita();

            cita.setNombreMedico(cursor.getString(1));
            cita.setDia(cursor.getString(2));

            citaList.add(cita);
        }

        cursor.close();
        sql.close();

        return citaList;
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

    private String consultarCorreo(String usuario){
        SQLiteDatabase base = db.getReadableDatabase();
        Cursor cursor = base.rawQuery("select correo from usuarios where usuario = ?", new String[]{usuario});

        String correo = "";
        int columIndex = cursor.getColumnIndex("correo");
        if(columIndex>=0 && cursor.moveToFirst()){
            correo = cursor.getString(columIndex);
        }
        cursor.close();
        return correo;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}

