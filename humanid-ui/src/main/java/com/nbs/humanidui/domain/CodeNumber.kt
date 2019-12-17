package com.nbs.humanidui.domain

import com.nbs.humanidui.util.emptyString


data class CodeNumber (
        val number: String = emptyString(),
        val flag: Int = 0
)