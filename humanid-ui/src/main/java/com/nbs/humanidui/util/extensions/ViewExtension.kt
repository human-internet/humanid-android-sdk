package com.human.android.util.extensions

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.human.android.util.emptyString
import com.nbs.humanidui.R

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