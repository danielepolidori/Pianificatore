package com.example.scheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

public class MyNewIntentService extends IntentService {

    private Uri soundNotif;

    public MyNewIntentService() {

        super("MyNewIntentService");

        soundNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.hasExtra("cmd_notif")) {

            if (!intent.hasExtra("id"))
                System.out.println("ERRORE_DATI_INTENT");

            String comando = intent.getStringExtra("cmd_notif");
            int id_ret = intent.getIntExtra("id", -1);

            Intent notifIntent = new Intent(this, MainActivity.class);
            notifIntent.putExtra("cmd_notif", comando);
            notifIntent.putExtra("id", id_ret);
            notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(notifIntent);
        }
        else {

            int id_ret = intent.getIntExtra("id", -1);
            String descTask_ret = intent.getStringExtra("descTask");

            if (!(intent.hasExtra("id") && intent.hasExtra("descTask")))
                System.out.println("ERRORE: Dati non passati nell'intent.");


            // Costruzione notifica

            //Intent ongoingIntent = new Intent();       // ~ Non deve portare da nessuna parte ma deve modificare lo stato del task in ongoing
            //PendingIntent ongoingPendingIntent = PendingIntent.getBroadcast(this, 0, ongoingIntent, 0);

            // Porta nel form di modifica di un task
            Intent postponeIntent = new Intent(this, MyReceiver.class);
            postponeIntent.putExtra("cmd_notif", "postpone_notif");
            postponeIntent.putExtra("id", id_ret);
            PendingIntent postponePendingIntent = PendingIntent.getBroadcast(this, 0, postponeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle(descTask_ret);
            builder.setContentText("E' ora di svolgere l'attività!");
            builder.setSmallIcon(android.R.drawable.ic_dialog_email);
            builder.setAutoCancel(true);
            builder.setSound(soundNotif);
            //builder.addAction(R.drawable.ic_launcher_background, "In corso", ongoingPendingIntent);
            builder.addAction(R.drawable.ic_launcher_background, "Posponi", postponePendingIntent);


            // Pubblicazione notifica
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(id_ret, builder.build());
        }
    }
}