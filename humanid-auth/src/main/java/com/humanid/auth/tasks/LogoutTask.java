package com.humanid.auth.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humanid.HumanIDException;
import com.humanid.HumanIDSDK;
import com.humanid.auth.data.repositories.UserRepository;
import com.humanid.internal.DeviceIDManager;
import com.humanid.task.OnFailureListener;
import com.humanid.task.OnSuccessListener;
import com.humanid.task.Task;

import java.util.ArrayList;
import java.util.List;

public class LogoutTask extends Task<Void> {

    private final static String TAG = CheckLoginTask.class.getSimpleName();

    private boolean isSuccessful;
    private Void result;
    private Exception exception;

    private List<OnSuccessListener<? super Void>> successListeners = new ArrayList<>();
    private List<OnFailureListener> failureListeners = new ArrayList<>();

    public LogoutTask() {
        doLogout();
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Nullable
    @Override
    public Void getResult() {
        return result;
    }

    @Nullable
    @Override
    public Exception getException() {
        return exception;
    }

    @NonNull
    @Override
    public Task<Void> addOnSuccessListener(@NonNull OnSuccessListener<? super Void> var1) {
        successListeners.add(var1);
        return this;
    }

    @Override
    public void removeOnSuccessListener(@NonNull OnSuccessListener<? super Void> var1) {
        successListeners.remove(var1);
    }

    @NonNull
    @Override
    public Task<Void> addOnFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.add(var1);
        return this;
    }

    @Override
    public void removeFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.remove(var1);
    }

    private void doLogout() {
        try {
            Context context = HumanIDSDK.getInstance().getApplicationContext();
            UserRepository.getInstance(context).logout();
            DeviceIDManager.getInstance(context).clear();

            onSuccessful();
        } catch (Exception e) {
            onFailure(e);
        }
    }

    private void onSuccessful() {
        isSuccessful = true;

        for (OnSuccessListener<? super Void> sl: successListeners) {
            sl.onSuccess(result);
        }
    }

    private void onFailure(@NonNull Throwable e) {
        isSuccessful = false;

        exception = new HumanIDException(e);

        for (OnFailureListener fl: failureListeners) {
            fl.onFailure(exception);
        }
    }
}
