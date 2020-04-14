package com.humanid.humanidui.util.validation.rules

import android.view.View
import com.humanid.humanidui.util.validation.Rule

class CustomRule(private val customRule: () -> Boolean, override val errorMessage: String) : Rule {
    override fun isRulePassed(view: View): Boolean {
        return customRule.invoke()
    }
}