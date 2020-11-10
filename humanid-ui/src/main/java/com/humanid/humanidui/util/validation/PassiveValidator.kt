package com.humanid.humanidui.util.validation

import android.view.View

class PassiveValidator(override val validations: MutableList<Validation>) : Validator {
    override var mListener: ValidationListener? = null

    override fun addValidation(validation: Validation) {
        validations.add(validation)
    }

    override fun setListener(listener: ValidationListener) {
        this.mListener = listener
    }

    override fun validate(): Boolean {
        var isValid = true
        var notValidView: View? = null

        validations.forEachIndexed { _, validation ->
            if (!validation.validate()) isValid = false
        }

        if (isValid) mListener?.onValidationSuccess() else mListener?.onValidationFailed()

        return isValid
    }
}