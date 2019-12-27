package com.example.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentToIntentService = new Intent(context, MyNewIntentService.class);

        if (intent.hasExtra("cmd_notif")) {                             // È stato invocato dal bottone premuto sulla notifica

            String comando = intent.getStringExtra("cmd_notif");
            int id_ret = intent.getIntExtra("id", -1);

            intentToIntentService.putExtra("cmd_notif", comando);
            intentToIntentService.putExtra("id", id_ret);
        }
        else{                                                             // È stato chiamato dalla MainActivity per la creazione di una notifica

            int id_ret = intent.getIntExtra("id", -1);
            String descTask_ret = intent.getStringExtra("descTask");

            intentToIntentService.putExtra("id", id_ret);
            intentToIntentService.putExtra("descTask", descTask_ret);
        }

        context.startService(intentToIntentService);
    }
}