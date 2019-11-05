package com.payboxtest;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private ExecutorService executor;

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newFixedThreadPool(5);
    }

    public Executor getExecutor() {
        return executor;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        executor.shutdown();
    }
}
