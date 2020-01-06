package com.nbs.humanidui.presentation.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.nbs.humanidui.util.ReactiveFormFragment
import com.humanid.auth.HumanIDAuth
import com.nbs.humanidui.R
import com.nbs.humanidui.R.string
import com.nbs.humanidui.util.BundleKeys
import com.nbs.humanidui.util.emptyString
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.humanidui.util.extensions.gone
import com.nbs.humanidui.util.extensions.onClick
import com.nbs.humanidui.util.extensions.toHtml
import com.nbs.humanidui.util.extensions.visible
import com.nbs.humanidui.util.makeLinks
import com.nbs.humanidui.util.validation.Validation
import com.nbs.humanidui.util.validation.util.minMaxLengthRule
import kotlinx.android.synthetic.main.fragment_otp.btnDifferentNumber
import kotlinx.android.synthetic.main.fragment_otp.btnResendCode
import kotlinx.android.synthetic.main.fragment_otp.edtOtp
import kotlinx.android.synthetic.main.fragment_otp.tvMessage
import kotlinx.android.synthetic.main.fragment_otp.tvSubMessage
import kotlinx.android.synthetic.main.fragment_otp.tvSwitchMessage
import kotlinx.android.synthetic.main.fragment_otp.tvTitle

class OtpFragment : ReactiveFormFragment() {

    private var otpType = emptyString()

    private var phoneNumber: String = emptyString()

    //CountDownTimer
    private var time: Timer? = null
    private var defaultTime: Long = 30000
    private var timeLeft: Long = 0
    private var isCountingFinished: Boolean = false

    private var countryCode: String = emptyString()
    private val TAG = this.javaClass.simpleName

    companion object {
        private const val KEY_TIMER_STATE: String = "timer_state"
        private const val COUNT_DOWN_TIMER_INTERVAL: Long = 1000

        var listener: OnOtpListener? = null

        fun newInstance(type: String = LoginType.NORMAL.type): OtpFragment {
            val fragment = OtpFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_OTP_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(type: String = LoginType.NORMAL.type, countryCode: String, phoneNumber: String): OtpFragment {
            val fragment = OtpFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_OTP_TYPE, type)
            bundle.putString(BundleKeys.KEY_PHONENUMBER, phoneNumber)
            bundle.putString(BundleKeys.KEY_COUNTRY_CODE, countryCode)
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
            countryCode = it.getString(BundleKeys.KEY_COUNTRY_CODE) ?: emptyString()
        }
    }

    override fun initUI() {
        setSpannableString()
        startCountDownTimer()

        when (otpType) {
            LoginType.NEW_ACCOUNT.type -> {
                val subMessage = getString(R.string.sub_message_new_accoun)
                val phoneNumber = String.format(getString(R.string.format_phone_number), countryCode + phoneNumber)

                tvSubMessage.text = toHtml(subMessage + phoneNumber)
                tvSwitchMessage.gone()
            }
            LoginType.SWITCH_DEVICE.type -> {
                tvSwitchMessage.gone()
                tvTitle.text = getString(R.string.title_new_device)
                tvMessage.text = getString(R.string.message_new_device)
                tvSubMessage.text = getString(R.string.sub_message_new_device)
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
            resendOtp()
        }

        btnDifferentNumber.onClick {
            cancelCountDownTimer()
            listener?.onButtonDifferentNumberClicked(type = otpType)
        }
    }

    private fun resendOtp() {
        btnResendCode.isClickable = false
        btnResendCode.text = "Resending code"
        HumanIDAuth.getInstance()
            .requestOTP(countryCode, phoneNumber)
            .addOnCompleteListener {
                btnResendCode.isClickable = true
                if (it.isSuccessful) {
                    startCountDownTimer()
                } else {
                    btnResendCode.text = getString(R.string.action_resend_code)
                }
            }.addOnFailureListener {
                btnResendCode.text = getString(R.string.action_resend_code)
                btnResendCode.isClickable = true
            }
    }

    override fun initProcess() {
    }

    override fun setupFormValidation() {
        addValidation(
            Validation(
                edtOtp,
                listOf(minMaxLengthRule(getString(string.error_length), 4, 4))
            )
        )
    }

    override fun onValidationFailed() {
    }

    override fun onValidationSuccess() {
        val otpCode: String = edtOtp.text.toString().trim()

        cancelCountDownTimer()

        when (otpType) {
            LoginType.SWITCH_NUMBER.type -> {
                listener?.onOtpValidationSuccess(
                    type = LoginType.SWITCH_NUMBER.type,
                    otpCode = otpCode,
                    countryCode = countryCode,
                    phoneNumber = phoneNumber
                )
            }
            LoginType.SWITCH_DEVICE.type -> {
                listener?.onOtpValidationSuccess(
                    type = LoginType.SWITCH_DEVICE.type,
                    otpCode = otpCode,
                    countryCode = countryCode,
                    phoneNumber = phoneNumber
                )
            }
            else -> {
                listener?.onOtpValidationSuccess(
                    type = LoginType.NORMAL.type,
                    otpCode = otpCode,
                    countryCode = countryCode,
                    phoneNumber = phoneNumber
                )
            }
        }
    }

    private fun setSpannableString() {
        tvSwitchMessage.text = getString(R.string.message_humanid_description)
        tvSwitchMessage.makeLinks(
            Pair(getString(R.string.label_learn_about_out_mission), View.OnClickListener {
                Toast.makeText(context, getString(R.string.label_learn_about_out_mission), Toast.LENGTH_SHORT).show()
            })
        )
    }

    interface OnOtpListener {
        fun onButtonDifferentNumberClicked(type: String)
        fun onOtpValidationSuccess(type: String, otpCode: String, countryCode: String, phoneNumber: String)
    }

    //region CountDownTimer

    private fun startCountDownTimer() {
        isCountingFinished = false

        time?.cancel()

        time = Timer(defaultTime)
        time?.start()
    }

    private fun continueCountDownTimer() {
        isCountingFinished = false
        time = Timer(timeLeft)
        time?.start()
    }

    private fun cancelCountDownTimer() {
        time?.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(KEY_TIMER_STATE, defaultTime)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        cancelCountDownTimer()
        isCountingFinished = false
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        continueCountDownTimer()
    }

    inner class Timer(milis: Long) : CountDownTimer(milis, COUNT_DOWN_TIMER_INTERVAL) {
        override fun onFinish() {
        }

        override fun onTick(milisUntilFinished: Long) {
            if (btnResendCode != null) {
                timeLeft = milisUntilFinished
                val timeInSeconds: Long = milisUntilFinished / COUNT_DOWN_TIMER_INTERVAL
                Log.d(TAG, "Timer : $timeInSeconds")
                if (timeInSeconds == 0L) {
                    btnResendCode.text = getString(R.string.action_resend_code)
                    isCountingFinished = true
                    btnResendCode.isClickable = true
                } else {
                    btnResendCode.text = getString(R.string.action_resend_code_sec, timeInSeconds.toString())
                    btnResendCode.isClickable = false
                }
            }
        }
    }

    //endregion
}
