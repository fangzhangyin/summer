package myapp;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class myAppactivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        String key =  "1234567890abcxyz1234567890abcxyz1234567890abcxyz1234567890abcxyz";
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("test.realm")
                .encryptionKey(key.getBytes()).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
        Log.i("MyApplication","init realm success");
    }
}
