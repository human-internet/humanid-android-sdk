package com.humanid.auth.tasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humanid.HumanIDException;
import com.humanid.HumanIDSDK;
import com.humanid.Resource;
import com.humanid.auth.HumanIDUser;
import com.humanid.auth.data.model.User;
import com.humanid.auth.data.repositories.UserRepository;
import com.humanid.internal.DeviceIDManager;
import com.humanid.task.OnFailureListener;
import com.humanid.task.OnSuccessListener;
import com.humanid.task.Task;

import java.util.ArrayList;
import java.util.List;

public class LoginTask extends Task<HumanIDUser> {

    private final static String TAG = LoginTask.class.getSimpleName();

    private String existingUserHash;

    private boolean isSuccessful;
    private HumanIDUser result;
    private Exception exception;

    private List<OnSuccessListener<? super HumanIDUser>> successListeners = new ArrayList<>();
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
    public HumanIDUser getResult() {
        return result;
    }

    @Nullable
    @Override
    public Exception getException() {
        return exception;
    }

    @NonNull
    @Override
    public Task<HumanIDUser> addOnSuccessListener(@NonNull OnSuccessListener<? super HumanIDUser> var1) {
        successListeners.add(var1);
        return this;
    }

    @Override
    public void removeOnSuccessListener(@NonNull OnSuccessListener<? super HumanIDUser> var1) {
        successListeners.remove(var1);
    }

    @NonNull
    @Override
    public Task<HumanIDUser> addOnFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.add(var1);
        return this;
    }

    @Override
    public void removeFailureListener(@NonNull OnFailureListener var1) {
        failureListeners.remove(var1);
    }

    private void doLogin() {
        Context context = HumanIDSDK.getInstance().getApplicationContext();
        LiveData<Resource<User>> login = UserRepository
                .getInstance(context)
                .login(existingUserHash,
                        DeviceIDManager.getInstance(context).getDeviceID(),
                        DeviceIDManager.getInstance(context).getNotificationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationSecret());

        Observer<Resource<User>> observer = new Observer<Resource<User>>() {
            @Override
            public void onChanged(@Nullable Resource<User> resource) {
                if (resource == null) return;

                switch (resource.status) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        login.removeObserver(this);

                        User user = resource.data;
                        if (user != null) {
                            onSuccessful(resource.data);
                        } else {
                            onFailure(new Exception("Empty user"));
                        }
                        break;
                    case ERROR:
                        login.removeObserver(this);

                        onFailure(new Exception(resource.message));
                        break;
                }
            }
        };

        login.observeForever(observer);
    }

    private void onSuccessful(@NonNull final User user) {
        isSuccessful = true;

        result = new HumanIDUser(user.getUserHash());

        for (OnSuccessListener<? super HumanIDUser> sl : successListeners) {
            sl.onSuccess(result);
        }
    }

    private void onFailure(@NonNull Throwable e) {
        isSuccessful = false;

        exception = new HumanIDException(e);

        for (OnFailureListener fl : failureListeners) {
            fl.onFailure(exception);
        }
    }
}