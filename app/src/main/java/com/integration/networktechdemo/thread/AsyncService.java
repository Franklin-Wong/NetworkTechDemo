package com.integration.networktechdemo.thread;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Wongerfeng on 2019/8/19.
 */
public class AsyncService extends IntentService {
    private static final String TAG = "AsyncService";


    public AsyncService() {
        super("com.integration.networktechdemo.thread");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AsyncService(String name) {
        super("com.integration.networktechdemo.thread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: start");
        ///在 onStartCommand 方法中休眠，
//        try {
//            Thread.sleep(30 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Log.i(TAG, "onStartCommand: end");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i(TAG, "onHandleIntent: start");

        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onHandleIntent: end");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
