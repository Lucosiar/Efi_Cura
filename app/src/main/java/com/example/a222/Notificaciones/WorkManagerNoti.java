package com.example.a222.Notificaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.a222.FormularioCita_Medico.CitaActivity;
import com.example.a222.FragmentosPP.CitasFragment;
import com.example.a222.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WorkManagerNoti extends Worker {

    public WorkManagerNoti(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void guardarNoti(long duracion, Data data, String tag){
        OneTimeWorkRequest noti = new OneTimeWorkRequest.Builder(WorkManagerNoti.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS)
                .addTag(tag)
                .setInputData(data).build();
        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(noti);
    }

    @NonNull
    @Override
    public Result doWork() {
        String titulo = getInputData().getString("cita");
        String nombreApp = getInputData().getString("app");
        String detalle = getInputData().getString("detalle");
        //int id = (int) getInputData().getLong("idnoti", 0);

        oreo(titulo, detalle, nombreApp);

        return Result.success();
    }

    private void oreo(String t, String d, String app){
        String id = "message";
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), id);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setDescription("Notificacion FCM");
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }

        Intent intent = new Intent(getApplicationContext(), CitasFragment.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        builder.setAutoCancel(true).setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_medicacion)
                .setContentTitle(t)
                .setTicker("Nueva notificacion")
                .setContentText(d)
                .setSubText(app)
                .setContentIntent(pendingIntent)
                .setContentInfo("nuevo");

        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert  nm != null;
        nm.notify(idNotify, builder.build());

    }
}
