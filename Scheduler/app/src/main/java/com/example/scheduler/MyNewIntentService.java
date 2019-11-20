package com.example.scheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class MyNewIntentService extends IntentService {

    private NotificationManager notificationManager;

    public MyNewIntentService() {

        super("MyNewIntentService");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Costruzione notifica

        /*
        Intent ongoingIntent = new Intent();       // ~ Non deve portare da nessuna parte ma deve modificare lo stato del task in ongoing
        PendingIntent ongoingPendingIntent = PendingIntent.getBroadcast(this, 0, ongoingIntent, 0);

        Intent postponeIntent = new Intent(this, .class);       // ~ Deve portare nel form di modifica di un task
        PendingIntent postponePendingIntent = PendingIntent.getBroadcast(this, 0, postponeIntent, 0);
        */

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Pianificatore d'attività");
        builder.setContentText("C'è un'attività da compiere!");
        builder.setSmallIcon(android.R.drawable.ic_dialog_email);
        builder.setAutoCancel(true);
        //builder.addAction(R.drawable.ic_launcher_background, "In corso", ongoingPendingIntent);
        //builder.addAction(R.drawable.ic_launcher_background, "Postponi", postponePendingIntent);


        // Pubblicazione notifica
        notificationManager.notify(0, builder.build());
    }

    public void cancellaNotifica(int id) {

        notificationManager.cancel(id);
    }
}