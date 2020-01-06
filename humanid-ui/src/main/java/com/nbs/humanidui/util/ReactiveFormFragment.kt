package com.nbs.humanidui.util

import androidx.annotation.CallSuper
import com.nbs.humanidui.base.BaseFragment
import com.nbs.humanidui.util.validation.ReactiveValidator
import com.nbs.humanidui.util.validation.Validation
import com.nbs.humanidui.util.validation.ValidationListener
import com.nbs.humanidui.util.validation.Validator

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