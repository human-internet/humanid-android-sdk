package com.humanid.lib.domain

data class LoginParam(
    val language: String,
    val priorityCodes: Array<String>,
    val clientId: String,
    val clientSecret: String,
    val isDevelopmentMode: Boolean
)
