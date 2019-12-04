package com.human.android.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.human.android.util.extensions.setImageResource
import com.human.android.util.extensions.toHtml
import com.nbs.humanidui.R
import com.nbs.nucleo.utils.extensions.onClick
import com.nbs.nucleo.utils.extensions.visible
import kotlinx.android.synthetic.main.layout_alert_dialog.view.*

fun showUncancelableDialog(
        context: Context,
        icon: Int? = null,
        title: String? = null,
        message: String? = null,
        isMessageLight: Boolean = false,
        email: String? = null,
        subMessage: String? = null,
        first: String? = null, @NonNull firstListener: (() -> Unit)? = null,
        second: String? = null, @NonNull secondListener: (() -> Unit)? = null
) {
    val dialogView: View = LayoutInflater.from(context).inflate(R.layout.layout_alert_dialog, null)

    val builder = AlertDialog.Builder(context).setView(dialogView)
    val dialog = builder.create()



    with(dialogView) {
        if (icon != null) {
            imgIcon.visible()
            imgIcon.setImageResource(context, icon)
        }

        if (title != null) {
            tvTitle.visible()
            tvTitle.text = title
        }

        if (message != null) {
            tvMessage.visible()
            tvMessage.text = toHtml(message)

            if (isMessageLight) {
                tvMessage.typeface = ResourcesCompat.getFont(context, R.font.roboto_light)
            }
        }

        if (email != null) {
            tvEmail.visible()
            tvEmail.text = email
        }

        if (subMessage != null) {
            tvSubMessage.visible()
            tvSubMessage.text = subMessage
        }

        if (first != null) {
            btnFirst.visible()
            btnFirst.text = first
        }

        if (second != null) {
            btnSecond.visible()
            btnSecond.text = second
        }

        btnFirst.onClick {
            dialog.dismiss()
            firstListener?.invoke()
        }

        btnSecond.onClick {
            dialog.dismiss()
            secondListener?.invoke()
        }
    }

    dialog.apply {
        window?.apply {
            setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.CENTER)
            val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 60)
            setBackgroundDrawable(inset)
        }
        setCancelable(false)
        show()
    }
}