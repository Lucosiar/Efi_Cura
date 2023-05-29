package com.example.a222.FragmentosPP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.a222.Adaptadores.AdaptadorSintomas;
import com.example.a222.AdminSQLiteOpenHelper;
import com.example.a222.ClasesGetSet.Sintomas;
import com.example.a222.FormularioCita_Medico.EditarSintomasActivity;
import com.example.a222.FormularioCita_Medico.SintomasActivity;
import android.Manifest;
import com.example.a222.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SintomasFragment extends Fragment {

    Context context;
    AdminSQLiteOpenHelper db;
    AdaptadorSintomas adaptadorSintomas;
    SharedPreferences s;
    FragmentActivity sintomas;
    RecyclerView recyclerSintomas;
    Button bAnadirSintomas, bDescargarSintomas;
    List<Sintomas> sintomasList;

    private static final int REQUEST_CODE_PERMISSION = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Síntomas");
        sintomas = getActivity();

        //Container del fragmento
        View view = inflater.inflate(R.layout.fragment_sintomas, container, false);

        bAnadirSintomas = view.findViewById(R.id.bAnadirSintomas);
        bAnadirSintomas.setOnClickListener(v -> {
            Intent nuevoSintoma = new Intent(context, SintomasActivity.class);
            startActivity(nuevoSintoma);
        });

        recyclerSintomas = view.findViewById(R.id.recyclerSintomas);
        recyclerSintomas.setLayoutManager(new LinearLayoutManager(context));

        sintomasList = new ArrayList<>();
        db = new AdminSQLiteOpenHelper(sintomas);

        PaginaPrincipal pagina = (PaginaPrincipal) getActivity();
        FloatingActionButton fab = pagina.floatingActionButton;
        fab.setVisibility(View.GONE);

        bDescargarSintomas = view.findViewById(R.id.bDescargarSintomas);
        bDescargarSintomas.setOnClickListener(v -> {
            generarPDFSintomas();
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        sintomasList.clear();
        consultarSintomas();
        AdaptadorSintomas adaptadorSintomas = new AdaptadorSintomas(context, sintomasList, sintoma -> {
            Intent a = new Intent(getActivity(), EditarSintomasActivity.class);
            a.putExtra("tipo", sintoma.getTipo());
            a.putExtra("id", sintoma.getId());
            startActivity(a);
        });
        recyclerSintomas.setAdapter(adaptadorSintomas);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void generarPDFSintomas() {
        List<Sintomas> listaSintomas = sacarSintomasInversos();
        ordenarSintomas(listaSintomas);

        // Verificar si el permiso WRITE_EXTERNAL_STORAGE ya ha sido otorgado
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            // El permiso ya ha sido otorgado, continuar con el proceso de guardar el archivo
            guardarArchivoPDF();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso ha sido otorgado, continuar con el proceso de guardar el archivo
                guardarArchivoPDF();
            } else {
                // El permiso ha sido denegado, mostrar un mensaje de error o realizar alguna acción alternativa
                Toast.makeText(requireContext(), "Permiso denegado para guardar el archivo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void guardarArchivoPDF() {
        List<Sintomas> listaSintomas = sacarSintomasInversos();
        ordenarSintomas(listaSintomas);

        //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "sintomas.pdf");
        File file = new File(requireContext().getFilesDir(), "sintomas.pdf");

        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Escribir los registros en el PDF
            for (Sintomas sintoma : listaSintomas) {
                String texto = sintoma.getTipo() + " - " + sintoma.getFecha();

                // Crear un párrafo con el texto y establecer la alineación
                Paragraph paragraph = new Paragraph(texto);
                paragraph.setTextAlignment(TextAlignment.LEFT);

                // Agregar el párrafo al documento
                document.add(paragraph);
            }

            // Cerrar el documento iText
            document.close();

            // Mostrar un mensaje de éxito
            Toast.makeText(requireContext(), "PDF generado y guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Mostrar un mensaje de error en caso de fallo
            Toast.makeText(requireContext(), "Error al generar el PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void generarPDFSintomassda() {
        List<Sintomas> listaSintomas = sacarSintomasInversos();
        ordenarSintomas(listaSintomas);

        //File file = new File(requireContext().getFilesDir(), "sintomas.pdf");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "sintomas.pdf");

        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Escribir los registros en el PDF
            for (Sintomas sintoma : listaSintomas) {
                String texto = sintoma.getTipo() + " - " + sintoma.getFecha();

                // Crear un párrafo con el texto y establecer la alineación
                Paragraph paragraph = new Paragraph(texto);
                paragraph.setTextAlignment(TextAlignment.LEFT);

                // Agregar el párrafo al documento
                document.add(paragraph);
            }

            // Cerrar el documento iText
            document.close();

            // Mostrar un mensaje de éxito
            Toast.makeText(sintomas, "PDF generado y guardado correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Mostrar un mensaje de error en caso de fallo
            Toast.makeText(sintomas, "Error al generar el PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Sintomas> sacarSintomasInversos(){
        SQLiteDatabase sql = db.getReadableDatabase();

        s = sintomas.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String usu = consultarCorreo(nombre);

        Cursor cursor = sql.rawQuery("SELECT * FROM sintomas WHERE usuario = ? AND fecha NOT LIKE '*' AND fecha NOT BETWEEN '1' AND '31' AND fecha NOT IN ('Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo')",
                new String[]{usu});


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
        db.close();

        Log.d(":::ERROR:", "LIsta: " + sintomasList);
        return sintomasList;
    }

    private List<Sintomas> ordenarSintomas(List<Sintomas> listaDatos){
        Collections.sort(listaDatos, (o1, o2) -> {
            int tipoComparacion = o1.getTipo().compareTo(o2.getTipo());
            if(tipoComparacion != 0){
                return tipoComparacion;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date fecha1 = dateFormat.parse(o1.getFecha());
                Date fecha2 = dateFormat.parse(o2.getFecha());
                assert fecha1 != null;
                return fecha1.compareTo(fecha2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return 0;
        });
        return listaDatos;
    }

    private void consultarSintomas(){
        SQLiteDatabase base = db.getReadableDatabase();
        s = sintomas.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String nombre = s.getString("nombre", "");
        String nombreUsuario = consultarCorreo(nombre);

        StringBuilder numeros = new StringBuilder();
        for(int i = 1; i<=31; i++){
            numeros.append("'").append(i).append("', ");
        }

        numeros.setLength((numeros.length()) - 2);

        String dias = ", 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'";
        String resultado = numeros + dias;

        Sintomas sintomas = null;
        Cursor cursor = base.rawQuery("Select * from sintomas where usuario = ? and (fecha = '*' or fecha in (" + resultado + "))", new String[]{nombreUsuario});

        while(cursor.moveToNext()){
            sintomas = new Sintomas();
            sintomas.setId(cursor.getInt(0));
            sintomas.setTipo(cursor.getString(1));
            sintomas.setHora(cursor.getString(2));
            sintomas.setFecha(cursor.getString(3));
            sintomas.setAlta(cursor.getInt(4));
            sintomas.setBaja(cursor.getInt(5));
            sintomas.setDolor(cursor.getInt(6));

            sintomasList.add(sintomas);

            String fecha = cursor.getString(3);
            String hora = cursor.getString(2);

            if(fecha.equals("*")){
                programarNotificacion(hora);
            }
        }
        Log.d(":::ERROR:", ("Número de síntomas: " + sintomasList.size()));
        if(adaptadorSintomas != null){
            adaptadorSintomas.notifyDataSetChanged();
        }
    }

    private void programarNotificacion(String hora) {
        // Aquí debes implementar la lógica para programar la notificación una hora antes de la hora del síntoma.
        // Puedes utilizar las APIs de notificaciones de Android, como NotificationManager y AlarmManager,
        // para programar y mostrar la notificación en el momento deseado.
        // Aquí solo se muestra un ejemplo básico de cómo programar una notificación.

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Obtener la hora y minutos del síntoma
        int horaSintoma = Integer.parseInt(hora.split(":")[0]);
        int minutoSintoma = Integer.parseInt(hora.split(":")[1]);
        // Calcular la hora y minutos para la notificación (1 hora antes del síntoma)
        int horaNotificacion = horaSintoma - 1;
        int minutoNotificacion = minutoSintoma;

        // Si la hora de la notificación es menor a 0, restar un día y ajustar la hora
        if (horaNotificacion < 0) {
            horaNotificacion += 24;
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        // Establecer la hora y minutos para la notificación
        calendar.set(Calendar.HOUR_OF_DAY, horaNotificacion);
        calendar.set(Calendar.MINUTE, minutoNotificacion);
        calendar.set(Calendar.SECOND, 0);

        // Aquí debes implementar la lógica para mostrar la notificación en el momento deseado.
        // Puedes utilizar la clase NotificationManager para mostrar la notificación.

        // Ejemplo de cómo mostrar una notificación básica
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_medicacion)
                .setContentTitle("Recordatorio de síntoma")
                .setContentText("Tienes un síntoma programado en una hora.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        int notificationId = 1; // ID de la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
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
}