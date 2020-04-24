package com.ericjohnson.moviecatalogue.domain;

public interface UserUsecase {
    void login(String exchangeToken, LoginHttpRequest.OnLoginCallback onLoginCallback);

    String getAccessToken();

    Boolean isLoggedIn();
}
