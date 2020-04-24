package com.ericjohnson.moviecatalogue.domain;

import android.content.Context;
import android.text.TextUtils;

import com.ericjohnson.moviecatalogue.preference.AppPreference;

public class UserInteractor implements UserUsecase {

    private AppPreference appPreference;

    private LoginHttpRequest loginHttpRequest;

    private static UserUsecase userUsecase;

    public UserInteractor(Context context) {
        this.appPreference = new AppPreference(context);
        this.loginHttpRequest = new LoginHttpRequest(context);
    }

    @Override
    public void login(String exchangeToken, LoginHttpRequest.OnLoginCallback onLoginCallback) {
        loginHttpRequest.login(exchangeToken, new LoginHttpRequest.OnLoginCallback() {
            @Override
            public void onLoading() {
                onLoginCallback.onLoading();
            }

            @Override
            public void onLoginSuccess() {
                onLoginCallback.onLoginSuccess();
            }

            @Override
            public void onLoginFailed(String message) {
                onLoginCallback.onLoginFailed(message);
            }
        });
    }

    @Override
    public String getAccessToken() {
        return appPreference.getAccessToken();
    }

    @Override
    public Boolean isLoggedIn() {
        return !TextUtils.isEmpty(appPreference.getAccessToken());
    }

    public static UserUsecase getInstance(Context context){
        if (userUsecase == null){
            userUsecase = new UserInteractor(context);
        }
        return userUsecase;
    }
}
