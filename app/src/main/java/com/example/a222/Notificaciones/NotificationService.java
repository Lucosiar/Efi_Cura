package com.example.a222.Notificaciones;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.a222.R;

public class NotificationService extends IntentService {

    private static final int notification_id = 1;
    private static final String channel_id = "SintomasChannel";

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            showNotification();
        }
    }

    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_medicacion)
                .setContentTitle("Recordatorio de síntoma")
                .setContentText("Tienes un síntoma programado para hoy")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notification_id, builder.build());

    }
}
