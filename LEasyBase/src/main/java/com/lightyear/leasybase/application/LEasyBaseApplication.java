package com.lightyear.leasybase.application;

import android.app.Application;

import com.lightyear.leasybase.exception.ExceptionCrashHandle;

public class LEasyBaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionCrashHandle.getInstance().init(this);
    }
}
