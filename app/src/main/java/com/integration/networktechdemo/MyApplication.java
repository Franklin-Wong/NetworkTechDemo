package com.integration.networktechdemo;

import android.app.Application;

/**
 * Created by Wongerfeng on 2019/8/14.
 */
public class MyApplication extends Application {

    private static MyApplication mApp;

    public static MyApplication getInstance() {

        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }


}
