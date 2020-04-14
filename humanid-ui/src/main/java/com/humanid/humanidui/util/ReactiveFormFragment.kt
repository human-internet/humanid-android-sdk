package com.humanid.humanidui.util

import androidx.annotation.CallSuper
import com.humanid.humanidui.base.BaseFragment
import com.humanid.humanidui.util.validation.ReactiveValidator
import com.humanid.humanidui.util.validation.Validation
import com.humanid.humanidui.util.validation.ValidationListener
import com.humanid.humanidui.util.validation.Validator

abstract class ReactiveFormFragment: BaseFragment(), ValidationListener {

    private val validator: Validator = ReactiveValidator(mutableListOf())

    protected abstract fun setupFormValidation()

    @CallSuper
    override fun initLib() {
        validator.setListener(this)
        setupFormValidation()
    }

    protected fun addValidation(validation: Validation) {
        validator.addValidation(validation)
    }

    protected fun validate(): Boolean {
        return validator.validate()
    }
}