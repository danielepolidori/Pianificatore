package com.example.scheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {



        // VECCHIO
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1);


            /*
        // NUOVO
        Intent actionIntent = new Intent(context, MainActivity.class);

        PendingIntent pending =
                PendingIntent.getActivity(
                        context,
                        0,
                        actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        //.setContentTitle("Orario: "+new Date().toString())
                        .setContentIntent(pending)
                        .setContentText("Allarme scattato...");


        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());


        //------------------------------------------

        // Costruzione
        NotificationCompat.Builder n = new NotificationCompat.Builder(context.getApplicationContext())
                .setContentTitle("Pianificatore d'attività")
                .setContentText("C'è un'attività da compiere in questo momento!")
                .setSmallIcon(android.R.drawable.ic_dialog_email);

        // Pubblicazione
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());

        */
    }
}