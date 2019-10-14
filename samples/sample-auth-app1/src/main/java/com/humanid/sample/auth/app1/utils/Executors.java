package com.humanid.sample.auth.app1.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

public class Executors {

    private final static String TAG = Executors.class.getSimpleName();

    private static volatile Executors INSTANCE;

    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;

    private Executors(@NonNull Executor diskIO, @NonNull Executor networkIO,
                      @NonNull Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static Executors getInstance() {
        if (INSTANCE == null) {
            synchronized (Executors.class) {
                if (INSTANCE == null) {
                    Executor diskIO = java.util.concurrent.Executors.newSingleThreadExecutor();
                    Executor networkIO = java.util.concurrent.Executors.newFixedThreadPool(3);
                    Executor mainThread = new MainThreadExecutor();

                    INSTANCE = new Executors(diskIO, networkIO, mainThread);
                }
            }
        }
        return INSTANCE;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
