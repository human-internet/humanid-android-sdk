package com.humanid.humanidui.util.validation.rules

import android.view.View
import com.humanid.humanidui.util.validation.Rule
import com.humanid.humanidui.util.validation.util.extractInputTypedView
import com.humanid.humanidui.util.validation.util.regexMatchers

class RegexRule(private val regexRule: String, override val errorMessage: String) : Rule {
    override fun isRulePassed(view: View): Boolean {
        val input = extractInputTypedView(view)

        return regexMatchers(input, regexRule)
    }
}