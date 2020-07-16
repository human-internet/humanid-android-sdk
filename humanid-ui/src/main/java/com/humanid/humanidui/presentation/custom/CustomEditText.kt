package com.humanid.humanidui.presentation.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.humanid.humanidui.R

/**
 * Created by johnson on 6/13/20.
 */
class CustomEditText : AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setError(error: CharSequence?) {
        if (error == null) {
            setError(null, null)
        } else {
            val dr = context.resources.getDrawable(R.drawable.ic_error)
            dr.setBounds(0, 0, dr.intrinsicWidth, dr.intrinsicHeight)
            setError(error, dr)
        }
    }

}