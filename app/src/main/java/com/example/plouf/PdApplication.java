package com.example.plouf;

import android.app.Application;
import android.util.Log;

public class PdApplication extends Application {
    PdApplication appInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public PdApplication getAppInstance() {
        return appInstance;
    }
}
