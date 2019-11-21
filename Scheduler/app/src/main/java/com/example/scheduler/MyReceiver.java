package com.example.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        int id_ret = intent.getIntExtra("id", -1);
        String descTask_ret = intent.getStringExtra("descTask");

        Intent intent1 = new Intent(context, MyNewIntentService.class);
        intent1.putExtra("id", id_ret);
        intent1.putExtra("descTask", descTask_ret);

        context.startService(intent1);
    }
}