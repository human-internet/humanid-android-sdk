package com.humanid.lib.data

import com.humanid.lib.data.model.LoginResult

interface Repository {
    fun getLoginUrl(language: String, countryCodes: Array<String>, clientId: String, clientSecret: String,
        apiCallback: ApiCallback)
}

interface ApiCallback {
    fun onSucceed(loginResult: LoginResult)
    fun onFailed(e: Exception)
}