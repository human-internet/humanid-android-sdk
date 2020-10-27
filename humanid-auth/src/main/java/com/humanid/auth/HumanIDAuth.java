package com.humanid.auth;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.humanid.HumanIDSDK;
import com.humanid.auth.data.model.User;
import com.humanid.auth.data.repository.UserRepository;
import com.humanid.auth.internal.DeviceIDManager;
import com.humanid.auth.util.livedata.vo.Resource;


public class HumanIDAuth {
    /**
     * Java string with value HumanIDAuth.
     */
    private final static String TAG = HumanIDAuth.class.getSimpleName();

    /**
     *Instance of HumanIDAuth Object - only one instance exists at a time.
     */
    private static volatile HumanIDAuth INSTANCE;

    /**
     * Default Android application environment.
     */
    private final Context applicationContext;

    /**
     * HumanIDUser object storing current user.
     */
    private HumanIDUser humanIDUser;


    /**
     * Constructor.
     *
     * @param applicationContext : the Context object that will be set to member variable applicationCont
     */
    private HumanIDAuth(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     *
     * @return : Returns INSTANCE, sets INSTANCE to new HumanIDAuth object if null.


     */
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

    /**
     *
     * @return : Returns humanIDUser object in HumanIDAuth.

     */
    @Nullable
    public HumanIDUser getCurrentUser() {
        loadCurrentUser();
        return humanIDUser;
    }

    /**
     Removes humanIDUser object.

     */
    public void removeCurrentUser(){
        UserRepository.getInstance(applicationContext).clearUserPreference();
    }

    /**
     * Sets humanIDUser object to the current user
     */
    private void loadCurrentUser() {
        User user = UserRepository.getInstance(applicationContext).getCurrentUser();
        if (user == null) return;
        String userHash = user.getUserHash();
        if (TextUtils.isEmpty(userHash)) return;
        humanIDUser = new HumanIDUser(userHash);
    }

    /**
     *Sends an OTP (One-Time password) to the phone number given by parameters countryCode + phone.
     * @param countryCode : String representing the user phone country code
     * @param phone : String the user phone number
     * @return : Returns Task object indicating whether the request was successful
     */
    @NonNull
    public Task<Void> requestOTP(@NonNull String countryCode, @NonNull String phone) {
        TaskCompletionSource<Void> task = new TaskCompletionSource<>();
        LiveData<Resource<String>> source = UserRepository.getInstance(applicationContext)
                .requestOTP(countryCode, phone);

        source.observeForever(new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> resource) {
                if (resource == null) return;
                switch (resource.status) {
                    case SUCCESS:
                        source.removeObserver(this);
                        task.setResult(null);
                        break;
                    case ERROR:
                        source.removeObserver(this);
                        task.setException(new Exception(resource.message));
                        break;
                }
            }
        });

        return task.getTask();
    }

    /**
     * Attempts to log user in, using the parameters countryCode, phone, and verificationCode as credentials.
     * @param countryCode : String representing the user phone country code
     * @param phone : String the user phone number
     * @param verificationCode : String representing verification code from OTP
     * @return : Returns Task object indicating whether the request was successful
     */
    @NonNull
    public Task<HumanIDUser> login(@NonNull String countryCode, @NonNull String phone,
                                      @NonNull String verificationCode) {
        TaskCompletionSource<HumanIDUser> newTask = new TaskCompletionSource<>();
        LiveData<Resource<User>> source = UserRepository.getInstance(applicationContext)
                .login(countryCode, phone,
                        verificationCode,
                        DeviceIDManager.getInstance(applicationContext).getDeviceID()
                );

        source.observeForever(new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> resource) {
                if (resource == null) return;
                switch (resource.status) {
                    case SUCCESS:
                        source.removeObserver(this);
                        if (resource.data != null) {
                            newTask.setResult(new HumanIDUser(resource.data.getExchangeToken()));
                        } else {
                            newTask.setResult(null);
                        }
                        break;
                    case ERROR:
                        source.removeObserver(this);
                        newTask.setException(new Exception(resource.message));
                        break;
                }
            }
        });

        return newTask.getTask();
    }

    /**
     *
     * @return : Returns Task object indicating success of whether instance logout was successful.
     */
    @NonNull
    public Task<Void> logout() {
        UserRepository.getInstance(applicationContext).logout();
        return Tasks.forResult(null);
    }

    /**
     * Attempts to revoke access using instance application Id and application secret.
     * @return : Returns Task object indicating whether revoke was successful.
     */
    @NonNull
    public Task<Void> revokeAccess() {
        TaskCompletionSource<Void> task = new TaskCompletionSource<>();
        LiveData<Resource<String>> source = UserRepository.getInstance(applicationContext)
                .revokeAccess(
                        HumanIDSDK.getInstance().getOptions().getApplicationID(),
                        HumanIDSDK.getInstance().getOptions().getApplicationSecret()
                );

        source.observeForever(new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> resource) {
                if (resource == null) return;
                switch (resource.status) {
                    case SUCCESS:
                        source.removeObserver(this);
                        task.setResult(null);
                        break;
                    case ERROR:
                        source.removeObserver(this);
                        task.setException(new Exception(resource.message));
                        break;
                }
            }
        });

        return task.getTask();
    }

}
