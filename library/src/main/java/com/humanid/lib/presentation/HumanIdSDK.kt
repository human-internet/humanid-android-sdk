package com.humanid.lib.presentation

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.humanid.lib.di.Injector
import com.humanid.lib.domain.LoginCallback
import com.humanid.lib.domain.LoginParam
import com.humanid.lib.domain.UseCase

class HumanIdSDK private constructor(builder: Builder){
    private val useCase: UseCase = Injector.getUseCase()
    private val handler: Handler by lazy {
        Handler(activity?.mainLooper!!)
    }
    
    private val activity: AppCompatActivity? = builder.activity
    private var clientId: String? = builder.clientId
    private val clientSecret: String? = builder.clientSecret
    private var defaultLanguage: String = "en"
    private var priorityCountryCodes: Array<String> = arrayOf("US", "DE", "FR")
    
    private val progressDialog: ProgressDialog? by lazy {
        ProgressDialog(activity)
    }
    
    init {
        this.defaultLanguage = builder.defaultLanguage
    }
    
    fun login(){
        if (progressDialog?.isShowing == true){
            progressDialog?.dismiss()
        }

        progressDialog?.setMessage("Please wait...")
        progressDialog?.show()
        
        useCase.login(
            LoginParam(
                clientId = clientId.orEmpty(),
                clientSecret = clientSecret.orEmpty(),
                language = defaultLanguage,
                priorityCodes = priorityCountryCodes
            ), object : LoginCallback{
                override fun onLoginSucceed(url: String) {
                    handler.post {
                        progressDialog?.dismiss()
                        WebActivity.start(activity!!, url)
                    }
                }
        
                override fun onLoginFailed(e: Exception) {
                    handler.post {
                        progressDialog?.dismiss()
                        activity?.showToast(e.message.orEmpty())
                    }
                }
            }
        )
    }

    fun getHumanIdExchangeToken(requestCode: Int, resultCode: Int, data: Intent?): String? {
        if (resultCode == WebActivity.RESULT_CODE){
            if (requestCode == WebActivity.REQUEST_CODE){
                if (data != null) {
                    return data.getParcelableExtra<Credential>(WebActivity.KEY_CREDENTIAL)?.exchangeToken
                }
            }
        }

        return null
    }
    class Builder{
        var activity: AppCompatActivity? = null
            private set
        var clientId: String? = null
            private set
        var clientSecret: String? = null
            private set
        var defaultLanguage: String = "en"
            private set
        var priorityCountryCodes: Array<String>? = null
            private set
        
        fun withActivity(activity: AppCompatActivity) = apply { this.activity = activity }
        fun addClientId(clientId: String) = apply { this.clientId = clientId }
        fun addClientSecret(clientSecret: String) = apply { this.clientSecret = clientSecret }
        fun setDefaultLanguage(language: String) = apply { this.defaultLanguage = language }
        fun setPriorityCountryCodes(countryCodes: Array<String>) = apply { this.priorityCountryCodes = countryCodes }
        
        fun build(): HumanIdSDK{
            return if (activity == null){
                throw IllegalArgumentException("Activity must be set!")
            }else if (clientId == null || clientSecret == null){
                throw IllegalArgumentException("ClientID and Client Secret must be set")
            }else{
                HumanIdSDK(this)
            }
        }
    }
    
    fun parse(uri: Uri): String?{
        if (uri.toString().contains("humanid")){
            val authority = uri.authority
            if (authority.equals("login", true)){
                val path = uri.pathSegments[0].toString()
                if (path == "success"){
                    return uri.getQueryParameter("et")
                }else{
                    activity?.showToast("Error humanID login")
                }
            }else{
                throw IllegalArgumentException("Invalid Uri")
            }
        }else{
            throw IllegalArgumentException("Invalid Uri")
        }
        return null
    }
}