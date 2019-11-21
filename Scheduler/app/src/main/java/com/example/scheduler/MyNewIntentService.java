package com.example.scheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;

public class MyNewIntentService extends IntentService {

    public MyNewIntentService() {

        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int id_ret = intent.getIntExtra("id", -1);
        String descTask_ret = intent.getStringExtra("descTask");

        // Costruzione notifica

        /*
        Intent ongoingIntent = new Intent();       // ~ Non deve portare da nessuna parte ma deve modificare lo stato del task in ongoing
        PendingIntent ongoingPendingIntent = PendingIntent.getBroadcast(this, 0, ongoingIntent, 0);

        Intent postponeIntent = new Intent(this, .class);       // ~ Deve portare nel form di modifica di un task
        PendingIntent postponePendingIntent = PendingIntent.getBroadcast(this, 0, postponeIntent, 0);
        */

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(descTask_ret);
        builder.setContentText("E' ora di svolgere l'attività!");
        builder.setSmallIcon(android.R.drawable.ic_dialog_email);
        builder.setAutoCancel(true);
        //builder.addAction(R.drawable.ic_launcher_background, "In corso", ongoingPendingIntent);
        //builder.addAction(R.drawable.ic_launcher_background, "Postponi", postponePendingIntent);


        // Pubblicazione notifica
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id_ret, builder.build());
    }
}