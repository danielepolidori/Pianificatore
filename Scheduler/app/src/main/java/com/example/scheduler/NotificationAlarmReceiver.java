package com.example.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotificationAlarmReceiver extends BroadcastReceiver {


    public NotificationAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentToIntentService = new Intent(context, NotificationIntentService.class);

        if (intent.hasExtra("cmd_notif")) {                                 // È stato invocato dal bottone premuto sulla notifica

            if (!intent.hasExtra("id"))
                System.out.println("ERRORE_DATI_INTENT");


            String comando = intent.getStringExtra("cmd_notif");
            int id_ret = intent.getIntExtra("id", -1);

            intentToIntentService.putExtra("cmd_notif", comando);
            intentToIntentService.putExtra("id", id_ret);
        }
        else{                                                                   // È stato chiamato per la creazione di una notifica

            if (!intent.hasExtra("id") && !intent.hasExtra("descTask"))
                System.out.println("ERRORE_DATI_INTENT");


            int id_ret = intent.getIntExtra("id", -1);
            String descTask_ret = intent.getStringExtra("descTask");

            intentToIntentService.putExtra("id", id_ret);
            intentToIntentService.putExtra("descTask", descTask_ret);
        }

        context.startService(intentToIntentService);
    }
}