package com.humanid.humanidui.util.validation

import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.humanid.humanidui.util.validation.view.ErrorableView

class Validation(val view: View, private val rules: List<Rule>) {

    private var isValid: Boolean = true
    private var ruleNotPassedIndex = -1

    //storing validation result history first = newest, second = oldest
    private var validationResultHistory: Pair<Boolean?, Boolean?> = Pair(null, null)

    fun validate(): Boolean {
        for (index in 0 until rules.size) {
            val rule = rules[index]
            if (rule.isRulePassed(view)) {
                isValid = true
                when (view) {
                    is EditText -> {
                        if (index == rules.size - 1 && view.error != null) {
                            view.error = null
                        }
                    }
                    is TextInputLayout -> {
                        if (index == rules.size - 1 && view.error != null) {
                            view.error = null
                        }
                    }
                    is ErrorableView -> {
                        if (index == rules.size - 1 && view.isErrorShowing()) {
                            view.hideError()
                        }
                    }
                }
            } else {
                isValid = false
                ruleNotPassedIndex = index
                val errorMessage = rule.errorMessage
                when (view) {
                    is EditText -> view.error = errorMessage
                    is TextInputLayout -> view.error = errorMessage
                    is ErrorableView -> view.showError(errorMessage)
                }
                break
            }
        }

        updateValidationHistory(isValid)
        return isValid
    }

    private fun updateValidationHistory(valid: Boolean) {
        val old = validationResultHistory.copy()
        validationResultHistory = Pair(valid, old.first)
    }

    fun getPreviousValidationResult(): Boolean {
        return validationResultHistory.second ?: false
    }
}