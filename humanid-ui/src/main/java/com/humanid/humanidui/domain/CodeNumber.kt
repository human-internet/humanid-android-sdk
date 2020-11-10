package com.humanid.humanidui.domain

import com.humanid.humanidui.util.emptyString


data class CodeNumber (
        val number: String = emptyString(),
        val flag: Int = 0
)