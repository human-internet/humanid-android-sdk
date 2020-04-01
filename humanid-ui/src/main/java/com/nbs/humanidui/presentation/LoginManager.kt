package com.nbs.humanidui.presentation

import android.app.Activity
import android.content.Intent
import com.nbs.humanidui.R
import com.nbs.humanidui.presentation.welcome.HumanIDActivity
import com.nbs.humanidui.util.BundleKeys
import com.nbs.humanidui.util.SingletonHolder

class LoginManager(private val activity: Activity) {

    companion object : SingletonHolder<LoginManager, Activity>({
        LoginManager(activity = it)
    })

    private var loginCallback: LoginCallback? = null

    fun registerCallback(callback: LoginCallback){
        this.loginCallback = callback
        HumanIDActivity.start(activity)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode == 200){
            if (data != null){
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

interface LoginCallback{
    fun onSuccess(userHash: String)

    fun onError(errorMessage: String)

    fun onCancel(errorMessage: String)
}