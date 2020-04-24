package com.humanid.humanidui.util.validation

interface ValidationListener {
    fun onValidationSuccess()

    fun onValidationFailed()
}