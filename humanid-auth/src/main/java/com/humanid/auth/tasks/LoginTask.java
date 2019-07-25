package com.humanid.auth.tasks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humanid.AccessToken;
import com.humanid.AccessTokenManager;
import com.humanid.HumanIDException;
import com.humanid.auth.HumanIDUser;
import com.humanid.auth.data.repository.AuthRepository;
import com.humanid.task.OnFailureListener;
import com.humanid.task.OnSuccessListener;
import com.humanid.task.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginTask extends Task<AuthResult> {

    private final static String TAG = LoginTask.class.getSimpleName();

    private String existingUserHash;

    private boolean isSuccessful;
    private AuthResult result;
    private Exception exception;

    private List<OnSuccessListener<? super AuthResult>> successListeners = new ArrayList<>();
    private List<OnFailureListener> failureListeners = new ArrayList<>();

    public LoginTask(@NonNull String existingUserHash) {
        this.existingUserHash = existingUserHash;

        doLogin();
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Nullable
    @Override
    public AuthResult getResult() {
        return result;
    }

    @Nullable
    @Override
    public Exception getException() {
        return exception;
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> var1) {
        successListeners.add(var1);
        return this;
    }

    @Override
    public void removeOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> var1) {
        successListeners.remove(var1);
    }

    @NonNull
    @Override
    public Task<AuthResult> addOnFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.add(var1);
        return this;
    }

    @Override
    public void removeFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.remove(var1);
    }

    private void doLogin() {
        AuthRepository.getInstance()
                .login(existingUserHash)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<AccessToken>() {
                    @Override
                    public void onSuccess(AccessToken accessToken) {
                        onSuccessful(accessToken);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure(e);
                        dispose();
                    }
                });
    }

    private void onSuccessful(@NonNull final AccessToken accessToken) {
        isSuccessful = true;

        AccessTokenManager.getInstance().setCurrentAccessToken(accessToken);

        result = new AuthResult() {
            @Override
            public HumanIDUser getUser() {
                return new HumanIDUser(accessToken.getUserHash());
            }
        };

        for (OnSuccessListener<? super AuthResult> sl : successListeners) {
            sl.onSuccess(result);
        }
    }

    private void onFailure(@NonNull Throwable e) {
        isSuccessful = false;

        AccessTokenManager.getInstance().setCurrentAccessToken(null);

        exception = new HumanIDException(e);

        for (OnFailureListener fl : failureListeners) {
            fl.onFailure(exception);
        }
    }
}