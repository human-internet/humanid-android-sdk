package com.nbs.humanidui.util.validation.rules

import android.view.View
import com.nbs.humanidui.util.validation.Rule
import com.nbs.humanidui.util.validation.util.extractInputTypedView
import com.nbs.humanidui.util.validation.util.regexMatchers

class RegexRule(private val regexRule: String, override val errorMessage: String) : Rule {
    override fun isRulePassed(view: View): Boolean {
        val input = extractInputTypedView(view)

        return regexMatchers(input, regexRule)
    }
}