package com.nbs.humanidui.util.validation

interface ValidationListener {
    fun onValidationSuccess()

    fun onValidationFailed()
}