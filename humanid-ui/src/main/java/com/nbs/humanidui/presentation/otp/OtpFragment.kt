package com.nbs.humanidui.presentation.otp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.human.android.util.ReactiveFormFragment
import com.human.android.util.extensions.toHtml
import com.nbs.humanidui.R
import com.nbs.humanidui.util.BundleKeys
import com.nbs.humanidui.util.emptyString
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.humanidui.util.makeLinks
import com.nbs.nucleo.utils.extensions.gone
import com.nbs.nucleo.utils.extensions.onClick
import com.nbs.nucleo.utils.extensions.visible
import com.nbs.validacion.Validation
import com.nbs.validacion.util.minMaxLengthRule
import kotlinx.android.synthetic.main.fragment_otp.*

class OtpFragment : ReactiveFormFragment() {

    private var otpType = emptyString()

    private var phoneNumber: String = emptyString()

    companion object {
        var listener: OnOtpListener? = null

        fun newInstance(type: String = LoginType.NORMAL.type): OtpFragment {
            val fragment = OtpFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_OTP_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(type: String = LoginType.NORMAL.type, phoneNumber: String): OtpFragment {
            val fragment = OtpFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_OTP_TYPE, type)
            bundle.putString(BundleKeys.KEY_PHONENUMBER, phoneNumber)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutResource: Int = R.layout.fragment_otp

    override fun initLib() {
        super.initLib()
    }

    override fun initIntent() {
        arguments?.let {
            otpType = it.getString(BundleKeys.KEY_OTP_TYPE) ?: emptyString()
            phoneNumber = it.getString(BundleKeys.KEY_PHONENUMBER) ?: emptyString()
        }

    }

    override fun initUI() {
        setSpannableString()

        when (otpType) {
            LoginType.NEW_ACCOUNT.type -> {
                val subMessage = getString(R.string.sub_message_new_accoun)
                val phoneNumber = String.format(getString(R.string.format_phone_number), getString(R.string.sample_phone_number))

                tvSubMessage.text = toHtml(subMessage + phoneNumber)
                tvSwitchMessage.gone()

                btnDifferentNumber.onClick {

                }
            }
            LoginType.SWITCH_DEVICE.type -> {
                tvSwitchMessage.gone()
                tvTitle.text = getString(R.string.title_new_device)
                tvMessage.text = getString(R.string.message_new_device)
                tvSubMessage.text = getString(R.string.sub_message_new_device)
                btnDifferentNumber.text = getString(R.string.action_call_me)
            }
            LoginType.SWITCH_NUMBER.type -> {
                tvSwitchMessage.visible()
                tvTitle.text = getString(R.string.title_switching_number)
                tvMessage.text = getString(R.string.message_send_otp_switch)
                tvSubMessage.text = getString(R.string.message_enter_otp_switch)
            }
        }
    }

    override fun initAction() {
        when (otpType) {
            LoginType.NORMAL.type -> btnDifferentNumber.onClick {

            }

            LoginType.SWITCH_DEVICE.type -> btnDifferentNumber.onClick {

            }

            LoginType.SWITCH_NUMBER.type -> btnDifferentNumber.onClick {

            }
        }

        btnResendCode.onClick {

        }
    }

    override fun initProcess() {

    }

    override fun setupFormValidation() {
        addValidation(
                Validation(
                        edtOtp,
                        listOf(minMaxLengthRule(getString(R.string.error_length), 4, 4))
                )
        )
    }

    override fun onValidationFailed() {

    }

    override fun onValidationSuccess() {
        val otpCode: String = edtOtp.text.toString().trim()
        when (otpType) {
            LoginType.SWITCH_NUMBER.type -> {
                listener?.onOtpValidationSuccess(LoginType.SWITCH_NUMBER.type, otpCode, phoneNumber)
            }
            LoginType.SWITCH_DEVICE.type -> {
                listener?.onOtpValidationSuccess(LoginType.SWITCH_DEVICE.type, otpCode, phoneNumber)
            }
            else -> {
                listener?.onOtpValidationSuccess(LoginType.NORMAL.type, otpCode, phoneNumber)
            }
        }

    }

    private fun setSpannableString() {
        tvSwitchMessage.text = getString(R.string.message_humanid_description)
        tvSwitchMessage.makeLinks(
                Pair(getString(R.string.label_learn_about_out_mission), View.OnClickListener {
                    Toast.makeText(context, getString(R.string.label_learn_about_out_mission), Toast.LENGTH_SHORT).show()
                }))
    }

    interface OnOtpListener {
        fun onButtonDifferentNumberClicked()
        fun onOtpValidationSuccess(type: String, otpCode: String, phoneNumber: String)
    }
}
