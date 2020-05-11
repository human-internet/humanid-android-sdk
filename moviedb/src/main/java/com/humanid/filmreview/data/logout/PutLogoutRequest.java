package com.humanid.filmreview.data.logout;

import com.humanid.filmreview.BuildConfig;
import com.humanid.filmreview.data.base.BaseRequest;
import com.humanid.filmreview.data.logout.model.LogoutRequest;
import com.humanid.filmreview.data.logout.model.LogoutResponse;

public class PutLogoutRequest extends BaseRequest<LogoutRequest, LogoutResponse> {

    private OnLogoutCallback onLogoutCallback;

    public OnLogoutCallback getOnLogoutCallback() {
        return onLogoutCallback;
    }

    public void setOnLogoutCallback(final OnLogoutCallback onLogoutCallback) {
        this.onLogoutCallback = onLogoutCallback;
    }

    @Override
    protected void onRequestSuccess(final LogoutResponse response) {
        appPreference.setAccessToken("");
        getOnLogoutCallback().onLogoutSuccess();
    }

    @Override
    protected String getUrl() {
        return BuildConfig.DEMO_APP + "users/log-out";
    }

    @Override
    protected void onLoading() {
        getOnLogoutCallback().onLoading();
    }

    @Override
    protected void onRequestFailure(final String message) {
        getOnLogoutCallback().onLogoutFailure(message);
    }

    public interface OnLogoutCallback{
        void onLoading();

        void onLogoutSuccess();

        void onLogoutFailure(String message);
    }
}
