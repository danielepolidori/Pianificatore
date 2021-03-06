package com.example.scheduler;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;


public class NotificationIntentService extends IntentService {

    private Uri soundNotif;

    private static final String CHANNEL_ID = "Pianificatore";


    public NotificationIntentService() {

        super("NotificationIntentService");

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
                System.out.println("ERRORE_DATI_INTENT");


            // Costruzione notifica

            // Non porta da nessuna parte, ma modifica lo stato del task in ongoing
            Intent ongoingIntent = new Intent(this, NotificationAlarmReceiver.class);
            ongoingIntent.putExtra("cmd_notif", "ongoing_notif");
            ongoingIntent.putExtra("id", id_ret);
            PendingIntent ongoingPendingIntent = PendingIntent.getBroadcast(this, 0, ongoingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Porta nel form di modifica di un task
            Intent postponeIntent = new Intent(this, NotificationAlarmReceiver.class);
            postponeIntent.putExtra("cmd_notif", "postpone_notif");
            postponeIntent.putExtra("id", id_ret);
            PendingIntent postponePendingIntent = PendingIntent.getBroadcast(this, 1, postponeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setContentTitle(descTask_ret);
            builder.setContentText("È ora di svolgere l'attività!");
            builder.setSmallIcon(android.R.drawable.ic_dialog_email);
            builder.setSound(soundNotif);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setAutoCancel(true);
            builder.addAction(android.R.drawable.ic_media_play, "In corso", ongoingPendingIntent);
            builder.addAction(android.R.drawable.ic_menu_edit, "Posticipa", postponePendingIntent);


            // Pubblicazione notifica
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(id_ret, builder.build());
        }
    }
}