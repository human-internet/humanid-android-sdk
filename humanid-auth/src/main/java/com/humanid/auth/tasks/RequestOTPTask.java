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

public class RequestOTPTask extends Task<RequestOTPResult> {

    private final static String TAG = RequestOTPTask.class.getSimpleName();

    private String countryCode;
    private String phone;

    private boolean isSuccessful;
    private RequestOTPResult result;
    private Exception exception;

    private List<OnSuccessListener<? super RequestOTPResult>> successListeners = new ArrayList<>();
    private List<OnFailureListener> failureListeners = new ArrayList<>();

    public RequestOTPTask(@NonNull String countryCode, @NonNull String phone) {
        this.countryCode = countryCode;
        this.phone = phone;

        doRequestOTP();
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Nullable
    @Override
    public RequestOTPResult getResult() {
        return result;
    }

    @Nullable
    @Override
    public Exception getException() {
        return exception;
    }

    @NonNull
    @Override
    public Task<RequestOTPResult> addOnSuccessListener(@NonNull OnSuccessListener<? super RequestOTPResult> var1) {
        successListeners.add(var1);
        return this;
    }

    @Override
    public void removeOnSuccessListener(@NonNull OnSuccessListener<? super RequestOTPResult> var1) {
        successListeners.remove(var1);
    }

    @NonNull
    @Override
    public Task<RequestOTPResult> addOnFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.add(var1);
        return this;
    }

    @Override
    public void removeFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.remove(var1);
    }

    private void doRequestOTP() {
        AuthRepository.getInstance()
                .requestOTP(countryCode, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean codeSent) {
                        onSuccessful();
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure(e);
                        dispose();
                    }
                });
    }

    private void onSuccessful() {
        isSuccessful = true;

        result = new RequestOTPResult() {
            @Override
            public void onCodeSent() { }
        };

        for (OnSuccessListener<? super RequestOTPResult> sl: successListeners) {
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
