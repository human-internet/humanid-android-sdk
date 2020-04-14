package com.humanid.humanidui.presentation

import android.app.Activity
import android.content.Intent
import com.humanid.humanidui.presentation.phonenumber.PhoneNumberActivity
import com.humanid.humanidui.util.BundleKeys
import com.humanid.humanidui.util.ContextProvider
import com.humanid.humanidui.util.SingletonHolder

class LoginManager(private val activity: Activity) {

    companion object INSTANCE : SingletonHolder<LoginManager, Activity>({
        LoginManager(activity = it)
    })

    init {
        ContextProvider.initialize(activity)
    }

    private var loginCallback: LoginCallback? = null

    fun registerCallback(callback: LoginCallback){
        this.loginCallback = callback
        PhoneNumberActivity.start(activity)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode == 200){
            if (data != null){
                if (data.getBooleanExtra(BundleKeys.KEY_LOGIN_CANCEL, false)){
                    loginCallback?.onCancel()
                }else{
                    if (data.hasExtra(BundleKeys.KEY_USER_HASH)){
                        loginCallback?.onSuccess(data.getStringExtra(BundleKeys.KEY_USER_HASH))
                    }

                    if (data.hasExtra(BundleKeys.KEY_LOGIN_ERROR)){
                        loginCallback?.onError(data.getStringExtra(BundleKeys.KEY_LOGIN_ERROR))
                    }
                }
            }
        }
    }
}

interface LoginCallback{
    fun onSuccess(exchangeToken: String)

    fun onError(errorMessage: String)

    fun onCancel()
}