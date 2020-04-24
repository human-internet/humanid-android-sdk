package com.humanid.humanidui.util.validation.util

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.textfield.TextInputLayout
import com.humanid.humanidui.util.validation.RuleNotApplicableException
import com.humanid.humanidui.util.validation.view.InputView
import java.util.regex.Pattern

internal fun regexMatchers(input: String, regexRule: String): Boolean {
    val pattern = Pattern.compile(regexRule)
    val matcher = pattern.matcher(input)
    return matcher.matches()
}

internal fun extractInputTypedView(view: View): String {
    return when (view) {
        is EditText -> {
            view.text.toString().trim()
        }
        is TextInputLayout -> {
            view.editText!!.text.toString().trim()
        }
        is InputView -> {
            view.getText().trim()
        }
        else -> {
            throw RuleNotApplicableException("this view is not applicable")
        }
    }
}

internal fun extractCheckedTypedView(view: View): Boolean {
    return when (view) {
        is CheckBox -> {
            view.isChecked
        }
        is RadioButton -> {
            view.isChecked
        }
        is RadioGroup -> {
            view.checkedRadioButtonId != -1
        }
        else -> {
            throw RuleNotApplicableException("this view is not applicable")
        }
    }
}