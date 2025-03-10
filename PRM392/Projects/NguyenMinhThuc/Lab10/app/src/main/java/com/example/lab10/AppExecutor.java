package com.example.lab10;

import android.os.Looper;

import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import   android.os.Handler;
public class AppExecutor {
    private static final Object LOCK = new Object();
    private static AppExecutor sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutor(Executor diskIO, Executor networkIO, Executor mainThread){
        this.diskIO = diskIO;
        this.networkIO =networkIO;
        this.mainThread = mainThread;
    }
    public static AppExecutor getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return  sInstance;
    }
    public Executor DiskIO(){
        return diskIO;
    }

    public Executor MainThread() {
        return mainThread;
    }
    public Executor networkIO(){
        return  networkIO;
    }
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command){
            mainThreadHandler.post(command);
        }
    }
}
