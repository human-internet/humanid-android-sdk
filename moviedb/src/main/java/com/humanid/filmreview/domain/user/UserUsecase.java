package com.humanid.filmreview.domain.user;

import com.humanid.filmreview.data.login.PostLoginRequest;

public interface UserUsecase {
    void login(String exchangeToken, PostLoginRequest.OnLoginCallback onLoginCallback);

    String getAccessToken();

    Boolean isLoggedIn();
}
