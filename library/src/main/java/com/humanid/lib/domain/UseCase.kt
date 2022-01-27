package com.humanid.lib.domain

interface UseCase {
    fun login(loginParam: LoginParam, loginCallback: LoginCallback)
}

interface LoginCallback{
    fun onLoginSucceed(url: String)
    fun onLoginFailed(e: Exception)
}