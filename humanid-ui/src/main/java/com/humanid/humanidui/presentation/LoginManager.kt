package com.humanid.humanidui.presentation

import android.app.Activity
import android.content.Intent
import com.humanid.auth.HumanIDAuth
import com.humanid.humanidui.presentation.phonenumber.PhoneNumberActivity
import com.humanid.humanidui.presentation.welcome.HumanIDActivity
import com.humanid.humanidui.util.BundleKeys
import com.humanid.humanidui.util.ContextProvider
import com.humanid.humanidui.util.SingletonHolder

object LoginManager {
    private var loginCallback: LoginCallback? = null

    @JvmStatic
    fun registerCallback(activity: Activity, callback: LoginCallback){
        ContextProvider.initialize(activity)
        this.loginCallback = callback
        HumanIDActivity.start(activity)
    }

    @JvmStatic
    fun logout(){
        HumanIDAuth.getInstance().logout()
    }

    @JvmStatic
    fun revoke(callback: RevokeAccessCallback){
        HumanIDAuth.getInstance().revokeAccess().addOnCompleteListener {
            callback.onSuccess()
        }.addOnFailureListener {
            it.message?.let {message ->
                callback.onError(message)
            }
        }
    }

    @JvmStatic
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode == 200 || resultCode == 0x300) {
            if (data != null) {
                if (data.getBooleanExtra(BundleKeys.KEY_LOGIN_CANCEL, false)) {
                    loginCallback?.onCancel()
                } else {
                    if (data.hasExtra(BundleKeys.KEY_EXCHANGE_TOKEN)) {
                        loginCallback?.onSuccess(data.getStringExtra(BundleKeys.KEY_EXCHANGE_TOKEN))
                    }

                    if (data.hasExtra(BundleKeys.KEY_LOGIN_ERROR)) {
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

interface RevokeAccessCallback{
    fun onSuccess()

    fun onError(errorMessage: String)
}