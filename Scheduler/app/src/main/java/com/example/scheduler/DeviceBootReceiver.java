package com.example.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import io.realm.Realm;
import io.realm.RealmResults;


public class DeviceBootReceiver extends BroadcastReceiver {

    private Realm realm;
    private RealmResults<Task> resultsTask;


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if(action != null) {

            // on device boot compelete, reset the alarm
            if (action.equals("android.intent.action.BOOT_COMPLETED")) {

                realm = Realm.getDefaultInstance();

                resultsTask = realm.where(Task.class).findAll();

                if (!resultsTask.isEmpty()) {

                    for (Task t_stored : resultsTask) {

                        if (t_stored.getStato() == 0) {

                            int idTask = t_stored.getId();

                            Intent notifyIntent = new Intent(context, NotificationAlarmReceiver.class);
                            notifyIntent.putExtra("id", idTask);
                            notifyIntent.putExtra("descTask", t_stored.getDescription());

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idTask, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, t_stored.getDateHour().getTime(), pendingIntent);
                        }
                    }
                }
            }
        }
    }
}