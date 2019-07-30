package com.humanid;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private final static String TAG = AppExecutors.class.getSimpleName();

    private static volatile AppExecutors INSTANCE;

    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;

    private AppExecutors(@NonNull Executor diskIO, @NonNull Executor networkIO,
                         @NonNull Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static AppExecutors getInstance() {
        if (INSTANCE == null) {
            synchronized (AppExecutors.class) {
                if (INSTANCE == null) {
                    Executor diskIO = Executors.newSingleThreadExecutor();
                    Executor networkIO = Executors.newFixedThreadPool(3);
                    Executor mainThread = new MainThreadExecutor();

                    INSTANCE = new AppExecutors(diskIO, networkIO, mainThread);
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
