package com.humanid.filmreview.domain.user;

import com.humanid.filmreview.data.login.PostLoginRequest;
import com.humanid.filmreview.data.logout.PutLogoutRequest;

public interface UserUsecase {
    void login(String exchangeToken, PostLoginRequest.OnLoginCallback onLoginCallback);

    String getAccessToken();

    Boolean isLoggedIn();

    void logout(PutLogoutRequest.OnLogoutCallback onLogoutCallback);
}
