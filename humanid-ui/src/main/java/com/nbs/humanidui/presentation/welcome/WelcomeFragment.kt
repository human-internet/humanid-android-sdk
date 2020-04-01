package com.nbs.humanidui.presentation.welcome

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.nbs.humanidui.R
import com.nbs.humanidui.base.BaseFragment
import com.nbs.humanidui.presentation.HumanIDOptions
import com.nbs.humanidui.util.extensions.onClick
import com.nbs.humanidui.util.extensions.visible
import kotlinx.android.synthetic.main.fragment_dialog_welcome.*


class WelcomeFragment : BaseFragment() {

    private var listener: OnWelcomeButtonListener? = null

    companion object{
        fun newInstance(listener: OnWelcomeButtonListener): WelcomeFragment {
            val fragment = WelcomeFragment()
            fragment.listener = listener
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutResource: Int
        get() = R.layout.fragment_dialog_welcome

    override fun initLib() {
    }

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
        btnContinue.onClick {
            listener?.onButtonContinueClicked()
        }
    }

    override fun initProcess() {
        context?.let {
            val humanIDOptions = HumanIDOptions.fromResource(it)
            if (humanIDOptions?.applicationIcon != -1){
                humanIDOptions?.applicationIcon?.let { it1 ->
                    imgAppIcon.visible()
                    imgAppIcon.setImageDrawable(ContextCompat.getDrawable(it, it1))
                }
            }

            if (!humanIDOptions?.applicationName.isNullOrEmpty()){
                tvAppName.visible()
                tvTnC.visible()
                tvAppName.text = humanIDOptions?.applicationName
                tvTnC.text = String.format(getString(R.string.message_hereby), humanIDOptions?.applicationName)
            }
        }
    }

    interface OnWelcomeButtonListener {
        fun onButtonContinueClicked()
    }
}
