package com.humanid.filmreview.data.login;

import com.humanid.filmreview.BuildConfig;
import com.humanid.filmreview.data.base.BaseRequest;
import com.humanid.filmreview.data.login.model.LoginRequest;
import com.humanid.filmreview.data.login.model.LoginResponse;

public class PostLoginRequest extends BaseRequest<LoginRequest, LoginResponse> {

    private OnLoginCallback onLoginCallback;

    public OnLoginCallback getOnLoginCallback() {
        return onLoginCallback;
    }

    public void setOnLoginCallback(final OnLoginCallback onLoginCallback) {
        this.onLoginCallback = onLoginCallback;
    }

    @Override
    protected void onRequestSuccess(final LoginResponse response) {
        appPreference.setAccessToken(response.getData().getToken());
        getOnLoginCallback().onLoginSuccess();
    }

    @Override
    protected String getUrl() {
        return BuildConfig.DEMO_APP + "users/log-in";
    }

    @Override
    protected void onLoading() {
        getOnLoginCallback().onLoading();
    }

    @Override
    protected void onRequestFailure(final String message) {
        getOnLoginCallback().onLoginFailed(message);
    }

    public interface OnLoginCallback{
        void onLoading();

        void onLoginSuccess();

        void onLoginFailed(String message);
    }
}
