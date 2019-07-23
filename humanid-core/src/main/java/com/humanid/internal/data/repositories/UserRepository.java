package com.humanid.internal.data.repositories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.HumanIDSDK;
import com.humanid.internal.Validate;
import com.humanid.internal.data.model.User;
import com.humanid.internal.data.source.local.preference.user.UserPreference;
import com.humanid.internal.data.source.remote.api.user.UserAPI;
import com.humanid.internal.data.source.remote.api.user.login.LoginRequest;
import com.humanid.internal.data.source.remote.api.user.login.LoginResponse;
import com.humanid.internal.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.internal.data.source.remote.api.user.register.RegisterResponse;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class UserRepository {

    private final static String TAG = UserRepository.class.getSimpleName();

    private static volatile UserRepository INSTANCE;

    private Context applicationContext;
    private UserPreference userPreference;
    private UserAPI userAPI;

    private UserRepository(@NonNull Context applicationContext) {
        Validate.sdkInitialized();
        this.applicationContext = applicationContext;
        this.userPreference = UserPreference.getInstance(applicationContext);
        this.userAPI = UserAPI.getInstance(applicationContext);
    }

    @NonNull
    public static UserRepository getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    public String getUserHash() {
        return userPreference.getUserHash();
    }

    public Single<User> register(@NonNull String countryCode, @NonNull String phone,
                                 @NonNull String verificationCode) {
        RegisterRequest request = new RegisterRequest(countryCode, phone,
                "xxxx", verificationCode, "xxxx");

        return userAPI.register(request)
                .map(new Function<RegisterResponse, User>() {
                    @Override
                    public User apply(RegisterResponse response) throws Exception {

                        User user = new User();
                        user.setUserHash(response.getHash());
                        user.setCreateAt(response.getCreatedAt());
                        user.setUpdateAt(response.getUpdatedAt());

                        return user;
                    }
                })
                .doOnSuccess(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        saveUser(user);
                    }
                });
    }

    public Single<User> login(@NonNull String existingHash) {
        LoginRequest request = new LoginRequest(existingHash, "xxxx");

        return userAPI.login(request)
                .map(new Function<LoginResponse, User>() {
                    @Override
                    public User apply(LoginResponse response) throws Exception {

                        User user = new User();
                        user.setUserHash(response.getHash());
                        user.setCreateAt(response.getCreatedAt());
                        user.setUpdateAt(response.getUpdatedAt());

                        return user;
                    }
                })
                .doOnSuccess(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        saveUser(user);
                    }
                });
    }

    private void saveUser(@NonNull User user) {
        userPreference.setUserHash(user.getUserHash());
    }
}
