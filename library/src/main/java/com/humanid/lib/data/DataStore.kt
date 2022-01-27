package com.humanid.lib.data

import com.google.gson.Gson
import com.humanid.lib.data.model.LoginResult
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class DataStore : Repository {
    
    companion object {
        
        val url: String = "https://core.human-id.org/staging/"
        
        fun getHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .build()
        }
        
        fun getJsonParser(): Gson = Gson()
        
        fun getInstance(): Repository = DataStore()
    }
    
    override fun getLoginUrl(language: String, countryCodes: Array<String>,
        clientId: String, clientSecret: String, apiCallback: ApiCallback) {
        
        val priorityCodes = countryCodes.joinToString(",")
        
        val urlBuilder = StringBuilder()
        urlBuilder.append(url)
        urlBuilder.append("mobile/users/web-login?")
        urlBuilder.append("lang=$language")
        urlBuilder.append("priority_country=$priorityCodes")

        val requestBody = MultipartBody
            .Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("id", clientId)
            .build()

        val request = Request.Builder()
            .url(urlBuilder.toString())
            .post(requestBody)
            .addHeader("client-id", clientId)
            .addHeader("client-secret", clientSecret)
            .build()
        
        getHttpClient().newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    apiCallback.onFailed(e)
                    e.printStackTrace()
                    throw e
                }
                
                override fun onResponse(call: Call, response: Response) {
                    val loginResult = getJsonParser()
                        .fromJson<LoginResult>(response.body?.string(), LoginResult::class.java)
                    apiCallback.onSucceed(loginResult)
                }
            })
    }
}