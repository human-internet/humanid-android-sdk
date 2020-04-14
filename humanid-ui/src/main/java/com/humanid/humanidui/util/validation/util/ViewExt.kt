package com.humanid.humanidui.util.validation.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun View.onClick(action: (view: View) -> Unit) {
    this.setOnClickListener(action)
}

fun EditText.onTextChange(doOnChange: (s: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            doOnChange(p0.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })
}

fun TextInputLayout.onTextChange(doOnChange: (s: String) -> Unit) {
    this.editText?.onTextChange { doOnChange.invoke(it) }
}
