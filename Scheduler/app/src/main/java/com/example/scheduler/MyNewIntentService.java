package com.example.scheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNewIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() {

        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        /*  VECCHIO

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Pianificatore d'attività");
        builder.setContentText("C'è un'attività da compiere in questo momento!");
        //builder.setSmallIcon(R.drawable.ic_launcher_foreground);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);    //to be able to launch your activity from the notification

        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

        */


        // NUOVO

        // Costruzione
        Notification.Builder n = new Notification.Builder(this)
                .setContentTitle("Pianificatore d'attività")
                .setContentText("C'è un'attività da compiere in questo momento!")
                .setSmallIcon(android.R.drawable.ic_dialog_email);

        // Pubblicazione
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());




    }
}