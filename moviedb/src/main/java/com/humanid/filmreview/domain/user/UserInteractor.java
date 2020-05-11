package com.humanid.filmreview.domain.user;

import android.content.Context;
import android.text.TextUtils;
import com.humanid.filmreview.data.base.RequestMethod;
import com.humanid.filmreview.data.login.PostLoginRequest;
import com.humanid.filmreview.data.login.model.LoginRequest;
import com.humanid.filmreview.data.login.model.LoginResponse;
import com.humanid.filmreview.preference.AppPreference;

public class UserInteractor implements UserUsecase {

    private AppPreference appPreference;

    private PostLoginRequest loginHttpRequest;

    private static UserUsecase userUsecase;

    public UserInteractor(Context context) {
        this.appPreference = new AppPreference(context);
        this.loginHttpRequest = new PostLoginRequest();
    }

    @Override
    public void login(String exchangeToken, PostLoginRequest.OnLoginCallback onLoginCallback) {
        loginHttpRequest.setRequest(new LoginRequest(exchangeToken));
        loginHttpRequest.setResponse(new LoginResponse());
        loginHttpRequest.setOnLoginCallback(onLoginCallback);
        loginHttpRequest.setRequestMethod(RequestMethod.POST);
        loginHttpRequest.execute();
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
