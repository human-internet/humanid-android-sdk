package com.humanid.humanidui.util.validation

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.humanid.humanidui.util.validation.util.onTextChange

class ReactiveValidator(override val validations: MutableList<Validation>) : Validator {
    override var mListener: ValidationListener? = null

    private var validated = 0

    override fun addValidation(validation: Validation) {
        // add listener according to each supported view
        when (validation.view) {
            is EditText -> {
                validation.view.onTextChange {
                    executeValidation(validation)
                }
            }
            is TextInputLayout -> {
                validation.view.onTextChange {
                    executeValidation(validation)
                }
            }
            else -> throw IllegalArgumentException("This type of view is not supported yet, the only supported views are EditText, TextInputLayout, ValidacionRadioGroup, ValidacionCheckBox and Validacion Spinner including their subclasses")
        }

        validations.add(validation)
    }

    private fun executeValidation(validation: Validation) {
        val isValid = validation.validate()

        if (isValid) {
            if (isValid != validation.getPreviousValidationResult()) {
                validated++
            }
        } else {
            if (isValid != validation.getPreviousValidationResult()) {
                if (validated > 0) {
                    validated--
                }
            }
        }

        if (validated == validations.size) {
            mListener?.onValidationSuccess()
        } else {
            mListener?.onValidationFailed()
        }
    }

    override fun setListener(listener: ValidationListener) {
        this.mListener = listener
    }

    override fun validate(): Boolean {
        return if (validated == validations.size) {
            mListener?.onValidationSuccess()
            true
        } else {
            mListener?.onValidationFailed()
            false
        }
    }
}