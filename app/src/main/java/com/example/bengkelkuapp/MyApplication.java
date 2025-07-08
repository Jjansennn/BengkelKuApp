package com.example.bengkelkuapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("bengkelku.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded() // Jangan pakai di production!
                .allowWritesOnUiThread(true)     // ✅ Tambahkan baris ini!
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

