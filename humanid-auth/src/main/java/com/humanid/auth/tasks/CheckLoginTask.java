package com.humanid.auth.tasks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humanid.HumanIDException;
import com.humanid.data.repositories.auth.AuthRepository;
import com.humanid.task.OnFailureListener;
import com.humanid.task.OnSuccessListener;
import com.humanid.task.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CheckLoginTask extends Task<CheckLoginResult> {

    private final static String TAG = CheckLoginTask.class.getSimpleName();

    private boolean isSuccessful;
    private CheckLoginResult result;
    private Exception exception;

    private List<OnSuccessListener<? super CheckLoginResult>> successListeners = new ArrayList<>();
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
    public CheckLoginResult getResult() {
        return result;
    }

    @Nullable
    @Override
    public Exception getException() {
        return exception;
    }

    @NonNull
    @Override
    public Task<CheckLoginResult> addOnSuccessListener(@NonNull OnSuccessListener<? super CheckLoginResult> var1) {
        successListeners.add(var1);
        return this;
    }

    @Override
    public void removeOnSuccessListener(@NonNull OnSuccessListener<? super CheckLoginResult> var1) {
        successListeners.remove(var1);
    }

    @NonNull
    @Override
    public Task<CheckLoginResult> addOnFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.add(var1);
        return this;
    }

    @Override
    public void removeFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.remove(var1);
    }

    private void doCheckLogin() {
        AuthRepository.getInstance()
                .checkLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isLoggedIn) {
                        onSuccessful(isLoggedIn);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure(e);
                        dispose();
                    }
                });
    }

    private void onSuccessful(final boolean isLoggedIn) {
        isSuccessful = true;

        result = new CheckLoginResult() {
            @Override
            public boolean authorized() {
                return isLoggedIn;
            }
        };

        for (OnSuccessListener<? super CheckLoginResult> sl: successListeners) {
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
