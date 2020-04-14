package com.humanid.humanidui.util.extensions

import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.humanid.humanidui.R
import com.humanid.humanidui.util.ContextProvider
import com.humanid.humanidui.util.emptyString

fun MaterialButton.isEnabled(isEnable: Boolean) {

    this.isEnabled = isEnable

    this.setTextColor(ContextCompat.getColor(context, if (isEnable) R.color.colorWhite else R.color.colorWarmGrey))

    this.backgroundTintList = ContextCompat.getColorStateList(context, if (isEnable) R.color.colorTwilightBlue else R.color.colorSilver)
}

fun toHtml(htmlText: String? = null): Spanned {
    return when {
        htmlText == null -> // return an empty spannable if the html is null
            SpannableString(emptyString())

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)

        else -> Html.fromHtml(htmlText)
    }
}



fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.switchVisibility() {
    visibility = when (visibility) {
        View.VISIBLE -> View.GONE
        View.GONE -> View.VISIBLE
        else -> View.VISIBLE
    }
}

fun EditText.onTextChanged(doOnChange: (s: String) -> Unit) {
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

fun View.onClick(action: (view: View) -> Unit) {
    this.setOnClickListener(action)
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}

fun showToast(message: String) {
    Toast.makeText(ContextProvider.get(), message, Toast.LENGTH_SHORT).show()
}