package com.nbs.humanidui.util.validation.util

object CommonRegex {
    const val EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$"

    //    only alphanumeric with min 8 length
    const val PASSWORD_ALPHANUMERIC = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\\\d)[a-zA-Z0-9\\\\d]{8,}\$"

    //  password with special char with min length 6
    const val PASSWORD_SPECIAL_CHAR = "^[\\s\\d\\w!@#\$%^&_*]{6,}\$"

    const val NUMBER_ONLY = "^[0-9]{0,}\$"

    const val ALPHABET_ONLY = "^[a-zA-Z]{0,}\$"

    const val NOT_EMPTY = "^.{1,}\$"

}