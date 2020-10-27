package com.humanid.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Class containing the threads used for humanID.
 */
public class Executors {
    /**
     *Executors
     */
    private final static String TAG = Executors.class.getSimpleName();
    /**
     *Executors object, used in getInstance() to make sure one Executors object is present at a time
     */
    private static volatile Executors INSTANCE;
    /**
     *single thread Executor
     */
    private Executor diskIO;
    /**
     *fixed thread Executor
     */
    private Executor networkIO;
    /**
     *main thread Executor
     */
    private Executor mainThread;

    /**
     *Constructor. Object contains three different types of Executor objects.
     * @param diskIO set to this.diskIO
     * @param networkIO set to this.networkIO
     * @param mainThread set to this.mainThread
     */
    private Executors(@NonNull Executor diskIO, @NonNull Executor networkIO,
                      @NonNull Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    /**
     *if INSTANCE is null, a new Executors object is created and set to INSTANCE. INSTANCE is returned. This function makes sure only one instance of an object of type Executors exists at a time.
     * @return Executors: this.INSTANCE, or the current instance of the object of type Executors
     */
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

    /**
     *Returns this.diskIO
     * @return Executor: this.diskIO
     */
    public Executor diskIO() {
        return diskIO;
    }

    /**
     *Returns this.networkIO
     * @return Executor: this.networkIO
     */
    public Executor networkIO() {
        return networkIO;
    }

    /**
     *Returns this.mainThread
     * @return Executor: this.mainThread
     */
    public Executor mainThread() {
        return mainThread;
    }

    /**
     *Class that executes commands passed into it
     */
    private static class MainThreadExecutor implements Executor {
        /**
         *Handler object for handling commands
         */
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        /**
         *Uses mainThreadHandler variable to execute actions given by command parameter.
         * @param command the command to be run as a Runnable object
         */
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
