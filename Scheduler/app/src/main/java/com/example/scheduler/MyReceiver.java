package com.example.scheduler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

/*

        // 0
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1);



        // 1
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



        // 2

        // Costruzione
        NotificationCompat.Builder n = new NotificationCompat.Builder(context.getApplicationContext())
                .setContentTitle("Pianificatore d'attività")
                .setContentText("C'è un'attività da compiere in questo momento!")
                .setSmallIcon(android.R.drawable.ic_dialog_email);

        // Pubblicazione
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());

 */

        // 3


        // Costruzione
        NotificationCompat.Builder n = new NotificationCompat.Builder(context.getApplicationContext())
                .setContentTitle("Pianificatore d'attività")
                .setContentText("C'è un'attività da compiere in questo momento!")
                .setSmallIcon(android.R.drawable.ic_dialog_email);

        // Pubblicazione
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());


        Toast toastNotifica = Toast.makeText(context.getApplicationContext(), "Notifica creata.", Toast.LENGTH_LONG);
        toastNotifica.show();


    }
}