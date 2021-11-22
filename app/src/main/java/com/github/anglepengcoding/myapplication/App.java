package com.github.anglepengcoding.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by 刘红鹏 on 2021/11/8.
 * <p>https://github.com/AnglePengCoding</p>
 * <p>https://blog.csdn.net/LIU_HONGPENG</p>
 */

public class App extends Application {
    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }
}
