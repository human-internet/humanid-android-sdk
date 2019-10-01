package com.humanid.auth;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.humanid.HumanIDSDK;
import com.humanid.auth.data.model.User;
import com.humanid.auth.data.repository.UserRepository;
import com.humanid.auth.internal.DeviceIDManager;
import com.humanid.data.model.Resource;

public class HumanIDAuth {

    private final static String TAG = HumanIDAuth.class.getSimpleName();

    private static volatile HumanIDAuth INSTANCE;

    private final Context applicationContext;
    private HumanIDUser humanIDUser;

    private HumanIDAuth(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    public static HumanIDAuth getInstance() {
        if (INSTANCE == null) {
            synchronized (HumanIDAuth.class) {
                if (INSTANCE == null) {
                    Context applicationContext = HumanIDSDK.getInstance().getApplicationContext();

                    INSTANCE = new HumanIDAuth(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    @Nullable
    public HumanIDUser getCurrentUser() {
        loadCurrentUser();
        return humanIDUser;
    }

    private void loadCurrentUser() {
        User user = UserRepository.getInstance(applicationContext).getCurrentUser();
        if (user == null) return;
        String userHash = user.getUserHash();
        if (TextUtils.isEmpty(userHash)) return;
        humanIDUser = new HumanIDUser(userHash);
    }

    @NonNull
    public Task<Void> requestOTP(@NonNull String countryCode, @NonNull String phone) {
        TaskCompletionSource<Void> task = new TaskCompletionSource<>();
        LiveData<Resource<String>> source = UserRepository.getInstance(applicationContext)
                .requestOTP(
                        countryCode, phone,
                        HumanIDSDK.getInstance().getOptions().getApplicationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationSecret()
                );
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();
        result.addSource(source, resource -> {
            if (resource == null) return;
            switch (resource.status) {
                case SUCCESS:
                    result.removeSource(source);
                    task.setResult(null);
                    break;
                case ERROR:
                    result.removeSource(source);
                    task.setException(new Exception(resource.message));
                    break;
            }
        });

        return task.getTask();
    }

    @NonNull
    public Task<HumanIDUser> register(@NonNull String countryCode, @NonNull String phone,
                                      @NonNull String verificationCode) {
        TaskCompletionSource<HumanIDUser> newTask = new TaskCompletionSource<>();
        LiveData<Resource<User>> source = UserRepository.getInstance(applicationContext)
                .register(
                        countryCode, phone, verificationCode,
                        DeviceIDManager.getInstance(applicationContext).getDeviceID(),
                        DeviceIDManager.getInstance(applicationContext).getNotificationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationSecret()
                );
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();
        result.addSource(source, resource -> {
            if (resource == null) return;
            switch (resource.status) {
                case SUCCESS:
                    result.removeSource(source);
                    if (resource.data != null) {
                        newTask.setResult(new HumanIDUser(resource.data.getUserHash()));
                    } else {
                        newTask.setResult(null);
                    }
                    break;
                case ERROR:
                    result.removeSource(source);
                    newTask.setException(new Exception(resource.message));
                    break;
            }
        });

        return newTask.getTask();
    }

    @NonNull
    public Task<HumanIDUser> login(@NonNull String userHash) {
        TaskCompletionSource<HumanIDUser> newTask = new TaskCompletionSource<>();
        LiveData<Resource<User>> source = UserRepository.getInstance(applicationContext)
                .login(
                        userHash,
                        DeviceIDManager.getInstance(applicationContext).getNotificationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationSecret()
                );
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();
        result.addSource(source, resource -> {
            if (resource == null) return;
            switch (resource.status) {
                case SUCCESS:
                    result.removeSource(source);
                    if (resource.data != null) {
                        newTask.setResult(new HumanIDUser(resource.data.getUserHash()));
                    } else {
                        newTask.setResult(null);
                    }
                    break;
                case ERROR:
                    result.removeSource(source);
                    newTask.setException(new Exception(resource.message));
                    break;
            }
        });

        return newTask.getTask();
    }

    @NonNull
    public Task<Void> loginCheck(@NonNull String userHash) {
        TaskCompletionSource<Void> newTask = new TaskCompletionSource<>();
        LiveData<Resource<String>> source = UserRepository.getInstance(applicationContext)
                .loginCheck(
                        userHash,
                        HumanIDSDK.getInstance().getOptions().getApplicationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationSecret()
                );
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();
        result.addSource(source, resource -> {
            if (resource == null) return;
            switch (resource.status) {
                case SUCCESS:
                    result.removeSource(source);
                    newTask.setResult(null);
                    break;
                case ERROR:
                    result.removeSource(source);
                    newTask.setException(new Exception(resource.message));
                    break;
            }
        });

        return newTask.getTask();
    }

    @NonNull
    public Task<Void> logout() {
        UserRepository.getInstance(applicationContext).logout();
        return Tasks.forResult(null);
    }

    @NonNull
    public Task<Void> update(@NonNull String userHash) {
        TaskCompletionSource<Void> newTask = new TaskCompletionSource<>();
        LiveData<Resource<User>> source = UserRepository.getInstance(applicationContext)
                .update(
                        userHash,
                        DeviceIDManager.getInstance(applicationContext).getNotificationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationSecret()
                );
        MediatorLiveData<Boolean> result = new MediatorLiveData<>();
        result.addSource(source, resource -> {
            if (resource == null) return;
            switch (resource.status) {
                case SUCCESS:
                    result.removeSource(source);
                    newTask.setResult(null);
                    break;
                case ERROR:
                    result.removeSource(source);
                    newTask.setException(new Exception(resource.message));
                    break;
            }
        });

        return newTask.getTask();
    }
}