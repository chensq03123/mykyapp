package com.hustunique.Utils;

import android.app.Application;

/**
 * Created by chensq-ubuntu on 10/25/14.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Dbhelper.path=this.getFilesDir().toString();
    }
}
