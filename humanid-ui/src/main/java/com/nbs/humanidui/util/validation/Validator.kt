package com.nbs.humanidui.util.validation

/**
 * Created by ghiyatshanif on 23/01/18.
 */

interface Validator {
    val validations: MutableList<Validation>

    var mListener: ValidationListener?

    fun addValidation(validation: Validation)

    fun setListener(listener: ValidationListener)

    fun validate(): Boolean
}
