package com.humanid.humanidui.util.validation.rules

import android.view.View
import com.humanid.humanidui.util.validation.Rule
import com.humanid.humanidui.util.validation.util.extractInputTypedView
import com.humanid.humanidui.util.validation.util.regexMatchers

class MinMaxLengthRule(
    private val minLength: String,
    private val maxLength: String,
    override val errorMessage: String
) : Rule {
    override fun isRulePassed(view: View): Boolean {
        val regexRule = "^.{$minLength,$maxLength}\$"

        val input = extractInputTypedView(view)

        return regexMatchers(input, regexRule)
    }
}