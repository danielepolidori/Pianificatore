package com.example.scheduler;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        // All'avvio dell'app viene cancellato tutto lo storage presente sul telefono
        //Realm.deleteRealm(realmConfiguration);
    }
}
