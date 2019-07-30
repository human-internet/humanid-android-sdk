package com.humanid.auth.tasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humanid.HumanIDException;
import com.humanid.HumanIDSDK;
import com.humanid.Resource;
import com.humanid.auth.data.repositories.UserRepository;
import com.humanid.task.OnFailureListener;
import com.humanid.task.OnSuccessListener;
import com.humanid.task.Task;

import java.util.ArrayList;
import java.util.List;

public class CheckLoginTask extends Task<String> {

    private final static String TAG = CheckLoginTask.class.getSimpleName();

    private boolean isSuccessful;
    private String result;
    private Exception exception;

    private List<OnSuccessListener<? super String>> successListeners = new ArrayList<>();
    private List<OnFailureListener> failureListeners = new ArrayList<>();

    public CheckLoginTask() {
        doCheckLogin();
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Nullable
    @Override
    public String getResult() {
        return result;
    }

    @Nullable
    @Override
    public Exception getException() {
        return exception;
    }

    @NonNull
    @Override
    public Task<String> addOnSuccessListener(@NonNull OnSuccessListener<? super String> var1) {
        successListeners.add(var1);
        return this;
    }

    @Override
    public void removeOnSuccessListener(@NonNull OnSuccessListener<? super String> var1) {
        successListeners.remove(var1);
    }

    @NonNull
    @Override
    public Task<String> addOnFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.add(var1);
        return this;
    }

    @Override
    public void removeFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.remove(var1);
    }

    private void doCheckLogin() {
        LiveData<Resource<String>> checkLogin = UserRepository
                .getInstance(HumanIDSDK.getInstance().getApplicationContext())
                .checkLogin();

        Observer<Resource<String>> observer = new Observer<Resource<String>>() {
            @Override
            public void onChanged(@Nullable Resource<String> resource) {
                if (resource == null) return;

                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        checkLogin.removeObserver(this);

                        onSuccessful(resource.data);
                        break;
                    case ERROR:
                        checkLogin.removeObserver(this);

                        onFailure(new Exception(resource.message));
                        break;
                }
            }
        };

        checkLogin.observeForever(observer);
    }

    private void onSuccessful(@Nullable String message) {
        isSuccessful = true;

        result = message;

        for (OnSuccessListener<? super String> sl: successListeners) {
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
