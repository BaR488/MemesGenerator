package com.example.boris.memesgenerator;

import android.app.Application;

import com.example.boris.memesgenerator.Helpers.SQLHelpers.HelperFactory;

/**
 * Created by boris on 16.07.2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
