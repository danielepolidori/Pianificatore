package com.example.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if(action != null) {

            // on device boot compelete, reset the alarm
            if (action.equals("android.intent.action.BOOT_COMPLETED")) {

                Intent notifyIntent = new Intent(context, NotificationAlarmReceiver.class);
                //notifyIntent.putExtra("id", idTask);
                //notifyIntent.putExtra("descTask", myTaskSet.getTask(idTask).getDescription());
                notifyIntent.putExtra("id", 10500);
                notifyIntent.putExtra("descTask", "DEVICE BOOT NOTIF");

                //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idTask, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 10500, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            }
        }
    }
}