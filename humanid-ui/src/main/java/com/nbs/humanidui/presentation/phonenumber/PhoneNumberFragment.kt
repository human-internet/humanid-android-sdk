package com.nbs.humanidui.presentation.phonenumber

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.human.android.util.BundleKeys
import com.human.android.util.ReactiveFormFragment
import com.human.android.util.emptyString
import com.human.android.util.extensions.isEnabled
import com.human.android.util.makeLinks
import com.humanid.auth.HumanIDAuth
import com.nbs.humanidui.R
import com.nbs.humanidui.domain.CodeNumber
import com.nbs.humanidui.presentation.adapter.SpinnerAdapter
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.nucleo.utils.extensions.gone
import com.nbs.nucleo.utils.extensions.onClick
import com.nbs.nucleo.utils.extensions.visible
import com.nbs.validacion.Validation
import com.nbs.validacion.util.notEmptyRule
import com.nbs.validacion.util.numberOnlyRule
import kotlinx.android.synthetic.main.fragment_phone_number.*

class PhoneNumberFragment : ReactiveFormFragment() {

    private var loginType = emptyString()

    companion object {
        var listener: OnPhoneNumberListener? = null

        fun newInstance(type: String = LoginType.NORMAL.type): PhoneNumberFragment {
            val fragment = PhoneNumberFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_LOGIN_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutResource: Int = R.layout.fragment_phone_number

    override fun initLib() {
        super.initLib()
        btnEnter.isEnabled(false)
    }

    override fun initIntent() {
        arguments?.let {
            loginType = it.getString(BundleKeys.KEY_LOGIN_TYPE) ?: emptyString()
        }
    }

    override fun initUI() {
        context?.let {
            initView()
            setSpannableString()

            spinnerCodeNumber.adapter = SpinnerAdapter(
                    it,
                    getCodeNumberData()
            )
        }

    }

    override fun initAction() {
        btnCancel.onClick {
            if (loginType == LoginType.SWITCH_NUMBER.type) {
                listener?.onButtonCancelClicked(LoginType.SWITCH_NUMBER.type)
            } else {
                listener?.onButtonCancelClicked(LoginType.NORMAL.type)
            }
        }

        btnTransfer.onClick {
            listener?.onButtonTransferClicked()
        }

        btnEnter.onClick {
            when (loginType) {
                LoginType.SWITCH_DEVICE.type -> {
                    listener?.onButtonEnterClicked(LoginType.SWITCH_DEVICE.type)
                }
                LoginType.NEW_ACCOUNT.type -> {
                    showLoading()
                    val task = HumanIDAuth.getInstance().requestOTP("62", "81210841382")
                    task.addOnSuccessListener {
                        hideLoading()
                        listener?.onButtonEnterClicked(LoginType.NEW_ACCOUNT.type)
                    }
                    task.addOnFailureListener {
                        hideLoading()
                        Log.d("test", it.message)
                    }
                }
                LoginType.SWITCH_NUMBER.type -> {
                    listener?.onButtonEnterClicked(LoginType.SWITCH_NUMBER.type)
                }
                else -> {

                }
            }
        }
    }

    override fun initProcess() {

    }

    override fun setupFormValidation() {
        addValidation(
                Validation(
                        edtPhoneNumber,
                        listOf(notEmptyRule(getString(R.string.error_field_required)),
                                numberOnlyRule(getString(R.string.error_number_format)))
                )
        )
    }

    override fun onValidationFailed() {
        btnEnter.isEnabled(false)
    }

    override fun onValidationSuccess() {
        btnEnter.isEnabled(true)
    }

    private fun initView() {
        if (loginType == LoginType.SWITCH_NUMBER.type) {
            containerTopNormal.gone()
            containerTopSwitch.visible()
            btnTransfer.gone()
            mcvAd.gone()
        } else {
            containerTopNormal.visible()
            containerTopSwitch.gone()
            btnTransfer.visible()
            mcvAd.visible()
        }
    }

    private fun getCodeNumberData(): List<CodeNumber> {
        val skinData = mutableListOf<CodeNumber>()

        val codeNumbers = resources.getStringArray(R.array.list_of_code_number)
        val flags = resources.obtainTypedArray(R.array.list_of_flag)

        codeNumbers.forEachIndexed { i, _ ->
            skinData.add(
                    CodeNumber(
                            codeNumbers[i],
                            flags.getResourceId(i, 0)
                    )
            )
        }
        flags.recycle()
        return skinData
    }

    private fun setSpannableString() {
        tvOTP.text = getString(R.string.message_otp_verification)
        tvAboutOurMission.text = getString(R.string.message_humanid_description)

        tvOTP.makeLinks(
                Pair(getString(R.string.label_learn_more), View.OnClickListener {
                    Toast.makeText(context, getString(R.string.label_learn_more), Toast.LENGTH_SHORT).show()
                }))

        tvAboutOurMission.makeLinks(
                Pair(getString(R.string.label_learn_about_out_mission), View.OnClickListener {
                    Toast.makeText(context, getString(R.string.label_learn_about_out_mission), Toast.LENGTH_SHORT).show()
                }))
    }

    interface OnPhoneNumberListener {
        fun onButtonCancelClicked(type: String)
        fun onButtonEnterClicked(type: String)
        fun onButtonTransferClicked()
    }

}