package com.example.a222.FragmentosPP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a222.Adaptadores.AdaptadorCitas;
import com.example.a222.Adaptadores.AdaptadorMedicacion;
import com.example.a222.Adaptadores.AdaptadorSintomas;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.Calendario.CalendarAdapter;
import com.example.a222.Calendario.MyCalendar;
import com.example.a222.Calendario.RecyclerTouchListener;
import com.example.a222.Calendario.myCalendarData;
import com.example.a222.ClasesGetSet.Cita;
import com.example.a222.ClasesGetSet.Medicacion;
import com.example.a222.ClasesGetSet.Sintomas;
import com.example.a222.FormularioCita_Medico.Agregar1_10SintomaActivity;
import com.example.a222.FormularioCita_Medico.AgregarNumeroSintomaActivity;
import com.example.a222.FormularioCita_Medico.AgregarVariosNumerosSintomaActivity;
import com.example.a222.FormularioCita_Medico.CitaActivity;
import com.example.a222.FormularioCita_Medico.EditarCitasActivity;
import com.example.a222.FormularioCita_Medico.EditarSintomasActivity;
import com.example.a222.FormularioCita_Medico.MedicacionActivity;
import com.example.a222.FormularioCita_Medico.SintomasActivity;
import com.example.a222.FormularioMedicacion.Formulario1Activity;
import com.example.a222.FormularioMedicacion.MedicacionTomarActivity;
import com.example.a222.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private final List<MyCalendar> calendarList= new ArrayList<>();
    private CalendarAdapter mAdapter;
    RecyclerView  recyclerMedicacion, recyclerSintoma, recyclerCita;
    Context context;
    AdminSQLiteOpenHelper db;
    SharedPreferences s;
    FragmentActivity home;
    TextView tvMedicacionHome, tvSintomaHome, tvCitaHome;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_homee, container, false);

        tvMedicacionHome = view.findViewById(R.id.tvMedicacionHome);
        tvSintomaHome = view.findViewById(R.id.tvSintomaHome);
        tvCitaHome = view.findViewById(R.id.tvCitaHome);

        recyclerMedicacion = view.findViewById(R.id.recyclerMedicacion);
        recyclerMedicacion.setLayoutManager(new LinearLayoutManager(context));
        recyclerSintoma = view.findViewById(R.id.recyclerSintoma);
        recyclerSintoma.setLayoutManager(new LinearLayoutManager(context));
        recyclerCita = view.findViewById(R.id.recyclerCita);
        recyclerCita.setLayoutManager(new LinearLayoutManager(context));

        home = getActivity();
        context = view.getContext();

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

        //Boton flotante para añadir varios
        PaginaPrincipal pagina = (PaginaPrincipal) getActivity();
        FloatingActionButton fab = pagina.floatingActionButton;
        fab.setVisibility(View.VISIBLE);

        prepareCalendarData();
        mostrarTodo();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        mostrarTodo();
    }


    private void mostrarTodo(){
        List <Cita> citasHoyList = consultarCitas();
        List <Medicacion> medicacionHoyList = consultarMedicaciones();
        List <Sintomas> sintomasHoyList = consultarSintomas();

        Log.d(":::ERROR:", "Citas: " + citasHoyList + "/n");
        Log.d(":::ERROR:", "Medicaciones: " + medicacionHoyList + "/n");
        Log.d(":::ERROR:", "Sintomas: " + sintomasHoyList + "/n");

        if(!medicacionHoyList.isEmpty()){
            AdaptadorMedicacion adaptadorMedicacion = new AdaptadorMedicacion(context, medicacionHoyList, medica -> {
                Intent medicacion = new Intent(getActivity(), MedicacionTomarActivity.class);
                medicacion.putExtra("nombreMedicacion", medica.getNombre());
                startActivity(medicacion);
            });
            recyclerMedicacion.setAdapter(adaptadorMedicacion);
        }else{
            tvMedicacionHome.setVisibility(View.GONE);
            recyclerMedicacion.setVisibility(View.GONE);
        }

        if(!sintomasHoyList.isEmpty()){
            AdaptadorSintomas adaptadorSintomas = new AdaptadorSintomas(context, sintomasHoyList, sinto -> {
                String tipo = sinto.getTipo();

                switch (tipo){
                    case "Dolor":
                    case "Migrañas":
                    case "Secreción nasal":
                    case "Picor":
                    case "Insomnio":
                    case "Ataques de pánico":
                    case "Ataques de ansiedad":
                    case "Humor":
                    case "Vertigo":
                    case "Dolor de garganta":
                    case "Erupciones":
                    case "Dolor lumbar":
                    case "Dolor de cabeza":
                    case "Dolor en el pecho":
                    case "Dolor abdominal":
                        Intent intent1 = new Intent(getActivity(), Agregar1_10SintomaActivity.class);
                        intent1.putExtra("tipo", sinto.getTipo());
                        intent1.putExtra("id", sinto.getId());
                        startActivity(intent1);
                        break;

                    case "Peso":
                    case "Pulsaciones":
                    case "Temperatura corporal":
                        Intent intent2 = new Intent(getActivity(), AgregarNumeroSintomaActivity.class);
                        intent2.putExtra("tipo", sinto.getTipo());
                        intent2.putExtra("id", sinto.getId());
                        startActivity(intent2);
                        break;

                    case "Presión arterial":
                        Intent intent3 = new Intent(getActivity(), AgregarVariosNumerosSintomaActivity.class);
                        intent3.putExtra("tipo", sinto.getTipo());
                        intent3.putExtra("id", sinto.getId());
                        startActivity(intent3);
                        break;

                    default:
                        Toast.makeText(context, "No se puede acceder al síntoma", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
            recyclerSintoma.setAdapter(adaptadorSintomas);
        }else{
            tvSintomaHome.setVisibility(View.GONE);
            recyclerSintoma.setVisibility(View.GONE);
        }

        if(!citasHoyList.isEmpty()){
            AdaptadorCitas adaptadorCitas = new AdaptadorCitas(context, citasHoyList, ci -> {
                Intent cita = new Intent(getActivity(), EditarCitasActivity.class);
                cita.putExtra("nombreCita", ci.getNombreMedico());
                cita.putExtra("horaCita", ci.getHora());
                cita.putExtra("fechaCita", ci.getDia());
                cita.putExtra("idCita", ci.getId());
                startActivity(cita);
            });
            recyclerCita.setAdapter(adaptadorCitas);
        }else{
            tvCitaHome.setVisibility(View.GONE);
            recyclerCita.setVisibility(View.GONE);
        }
    }

    private List<Sintomas> consultarSintomas(){
        List<Sintomas>sintomasList = new ArrayList<>();

        db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getReadableDatabase();

        //Scamos el usuario
        s = home.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        //Sacamos el dia de la semana en String
        String diaSemanaStringHoy = diaSemanaString();

        //Sacamos el dia de la semana en numero
        String diaSemanaNumero = diaSemanaNumero();

        Cursor cursor = sql.rawQuery("select * from sintomas where (fecha = ? or fecha = '*' or fecha = ?) and usuario = ?", new String[]{diaSemanaNumero, diaSemanaStringHoy, nombreUsuario});

        while(cursor.moveToNext()){
            Sintomas sintomas = new Sintomas();

            sintomas.setId(cursor.getInt(0));
            sintomas.setTipo(cursor.getString(1));
            sintomas.setHora(cursor.getString(2));
            sintomas.setFecha(cursor.getString(3));
            sintomas.setAlta(cursor.getInt(4));
            sintomas.setBaja(cursor.getInt(5));
            sintomas.setDolor(cursor.getInt(6));
            sintomas.setUsuario(cursor.getString(7));

            sintomasList.add(sintomas);
        }
        cursor.close();
        sql.close();

        return sintomasList;
    }

    //Terminar
    private List<Medicacion> consultarMedicaciones(){
        List<Medicacion> medicacionList = new ArrayList<>();

        db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getReadableDatabase();

        s = home.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        String fechaActual= "";

        //ARREGLAR CONSULTA
        //QUE BUSQUE POR *, DIA DE SEMANA O DIA MES ¿?
        Cursor cursor = sql.rawQuery("Select * from medicacion where usuario = ? and (diasTomas = ? or diasTomas = '*')", new String[]{nombreUsuario, fechaActual});

        while (cursor.moveToNext()) {
            Medicacion medicacion = new Medicacion();

            medicacion.setNombre(cursor.getString(0));
            medicacion.setCantidadDiaria(cursor.getString(1));
            medicacion.setFormato(cursor.getString(11));
            medicacion.setToma1(cursor.getString(6));
            String hora1 = (cursor.getString(6));
            String hora2 = cursor.getString(7);
            String hora3 = cursor.getString(8);
            String hora4 = cursor.getString(9);

            //si tomado = true; cantidadTotal -1
            //desaparece de la lista.
            if(hora4.equals("--") && hora3.equals("--") && hora2.equals("--")){

            }

            medicacionList.add(medicacion);
        }
        cursor.close();
        sql.close();

        return medicacionList;
    }

    private List<Cita> consultarCitas(){
        List<Cita> citaList = new ArrayList<>();

        db = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase sql = db.getReadableDatabase();

        //Obtenemos el correo del usuario
        s = home.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        String fechaActual = diaSemanaEntero();
        Log.d(":::ERROR:", "FECHA: " + fechaActual);
        Cursor cursor = sql.rawQuery("Select * from citas where dia = ? and usuario = ?", new String[]{fechaActual, nombreUsuario});

        while (cursor.moveToNext()) {
            Cita cita = new Cita();

            cita.setId(cursor.getInt(0));
            cita.setNombreMedico(cursor.getString(1));
            cita.setDia(cursor.getString(2));
            cita.setHora(cursor.getString(3));

            citaList.add(cita);
        }

        cursor.close();
        sql.close();

        return citaList;
    }

    private String diaSemanaString(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", (new Locale("es", "ES")));
        String diaSemana = sdf.format(calendar.getTime());
        diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);
        return (diaSemana);
    }

    private String diaSemanaNumero(){
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(dia);
    }

    private String diaSemanaEntero(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
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

