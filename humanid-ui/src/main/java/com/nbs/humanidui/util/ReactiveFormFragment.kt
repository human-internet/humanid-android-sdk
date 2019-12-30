package com.human.android.util

import androidx.annotation.CallSuper
import com.nbs.nucleosnucleo.presentation.BaseFragment
import com.nbs.validacion.ReactiveValidator
import com.nbs.validacion.Validation
import com.nbs.validacion.ValidationListener
import com.nbs.validacion.Validator

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