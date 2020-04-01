package com.nbs.humanidui.presentation

import android.app.Activity
import android.content.Intent
import com.nbs.humanidui.R
import com.nbs.humanidui.presentation.welcome.HumanIDActivity
import com.nbs.humanidui.util.BundleKeys

class LoginManager(private val activity: Activity) {
    private lateinit var loginCallback: LoginCallback

    fun registerCallback(callback: LoginCallback){
        this.loginCallback = callback
        HumanIDActivity.start(activity)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (resultCode == 0x300){
            if (data != null){
                if (data.hasExtra(BundleKeys.KEY_USER_HASH)){
                    loginCallback.onSuccess(data.getStringExtra(BundleKeys.KEY_USER_HASH))
                }

                if (data.hasExtra(BundleKeys.KEY_LOGIN_ERROR)){
                    loginCallback.onError(data.getStringExtra(BundleKeys.KEY_LOGIN_ERROR))
                }
            }else{
                loginCallback.onError(activity.getString(R.string.error_message_unable_to_connect))
            }
        }
    }
}

interface LoginCallback{
    fun onSuccess(userHash: String)

    fun onError(errorMessage: String)
}