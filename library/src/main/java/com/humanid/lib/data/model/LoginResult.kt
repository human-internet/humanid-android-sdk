package com.humanid.lib.data.model
import com.google.gson.annotations.SerializedName

data class LoginResult(
    @SerializedName("code")
    val code: String?,
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)

data class Data(
    @SerializedName("webLoginUrl")
    val webLoginURL: String?
)