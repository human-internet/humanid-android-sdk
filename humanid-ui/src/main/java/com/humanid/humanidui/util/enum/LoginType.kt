package com.humanid.humanidui.util.enum

enum class LoginType(val type: String) {
    NORMAL("normal"),
    SWITCH_NUMBER("switch number"),
    SWITCH_DEVICE("switch device"),
    OTP_EXPIRED("otp expired"),
    INPUT_OTP("input otp"),
    NEW_ACCOUNT("new account")
}