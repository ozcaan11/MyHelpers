package semiworld.org.myhelpers;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

/**
 * Created on 17.06.2017.
 */

public class AppStart extends Application {
    @Override public void onCreate() {
        super.onCreate();

        Configuration conf = new Configuration.Builder(getApplicationContext())
                .setDatabaseName("MyHelpersDb")
                .setDatabaseVersion(1)
                .addModelClass(User.class)
                .create();

        ActiveAndroid.initialize(conf);
    }
}
