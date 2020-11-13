package com.humanid.humanidui.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.Profile
import com.facebook.login.Login
import com.humanid.auth.HumanIDAuth
import com.humanid.humanidui.presentation.phonenumber.PhoneNumberActivity
import com.humanid.humanidui.presentation.welcome.HumanIDActivity
import com.humanid.humanidui.util.BundleKeys
import com.humanid.humanidui.util.ContextProvider
import com.humanid.humanidui.util.SingletonHolder
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.humanid.humanidui.R
import java.util.*

object LoginManager {
    private var loginCallback: LoginCallback? = null

    @JvmStatic
    fun registerCallback(activity: Activity, callback: LoginCallback){

        ContextProvider.initialize(activity)
        this.loginCallback = callback
        HumanIDActivity.start(activity)



    }

    @JvmStatic
    fun registerCallBackFB(activity: Activity,  imgProfile : View , context : Context , callbackManager: CallbackManager) {

        //ContextProvider.initialize(activity)
        //this.loginCallback =
        //HumanIDActivity.start(activity)
        LoginManager.getInstance()
                .logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            //@Override
            override fun onSuccess(loginResult: LoginResult) {
                //Log.d("GotExchangeToken", String.valueOf(loginResult));
                // authenticateUser(String.valueOf(loginResult));
                // auth(MainActivity.this,isLoggedIn);
                var profile: Profile = Profile.getCurrentProfile()
                Toast.makeText(context, profile.getFirstName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, profile.getLastName(), Toast.LENGTH_SHORT).show();

            }


            override fun onCancel() {
                //TODO("Not yet implemented")
                Toast.makeText(context, "Canceled!", Toast.LENGTH_SHORT).show();
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @JvmStatic
    fun logout(){
        if(loginCallback != null){
        HumanIDAuth.getInstance().logout()}
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

        if ( loginCallback != null && requestCode == 200 || resultCode == 0x300 ) {
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
    //fun onSuccess()
    fun onError(errorMessage: String)

    fun onCancel()
}



interface RevokeAccessCallback{
    fun onSuccess()

    fun onError(errorMessage: String)
}