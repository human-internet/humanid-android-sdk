package com.nbs.humanidui.presentation.termandcondition

import android.os.Bundle
import com.nbs.humanidui.R
import com.nbs.nucleo.utils.extensions.onClick
import com.nbs.nucleosnucleo.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_terms_and_condition.*

class TermsAndConditionFragment : BaseFragment() {

    companion object{
        var listener: OnTermsConditionListener? = null

        fun newInstance(): TermsAndConditionFragment {
            val fragment = TermsAndConditionFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutResource: Int = R.layout.fragment_terms_and_condition

    override fun initLib() {

    }

    override fun initIntent() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        btnSkip.onClick {
            listener?.onButtonSkipClicked()
        }
        btnBack.onClick {
            listener?.onButtonBackClicked()

//            parentFragment?.let {fragment ->
//                fragment.childFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.flDialog, RegisterEmailFragment.newInstance(LoginType.NORMAL.type))
//                        .commit()
//            }
        }
    }

    override fun initProcess() {

    }

    interface OnTermsConditionListener {
        fun onButtonSkipClicked()
        fun onButtonBackClicked()
    }

}