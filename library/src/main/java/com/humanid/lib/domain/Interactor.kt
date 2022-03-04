package com.humanid.lib.domain

import com.humanid.lib.data.ApiCallback
import com.humanid.lib.data.Repository
import com.humanid.lib.data.model.LoginResult

class Interactor(private val repository: Repository): UseCase {
    
    override fun login(loginParam: LoginParam, loginCallback: LoginCallback) {
        repository.getLoginUrl(
            language = loginParam.language,
            countryCodes = loginParam.priorityCodes,
            clientId = loginParam.clientId,
            clientSecret = loginParam.clientSecret,
            apiCallback = object : ApiCallback{
                override fun onSucceed(loginResult: LoginResult) {
                    loginResult.data?.webLoginURL?.let {
                        loginCallback.onLoginSucceed(it)
                    } ?: loginCallback.onLoginFailed(Exception("Login Failed"))
                }
    
                override fun onFailed(e: Exception) {
                    loginCallback.onLoginFailed(e)
                }
            }
        )
    }
}