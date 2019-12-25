package com.nbs.humanidui.presentation.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.human.android.util.ReactiveFormFragment
import com.human.android.util.extensions.toHtml
import com.nbs.humanidui.R
import com.nbs.humanidui.util.BundleKeys
import com.nbs.humanidui.util.emptyString
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.humanidui.util.makeLinks
import com.nbs.nucleo.utils.debug
import com.nbs.nucleo.utils.extensions.gone
import com.nbs.nucleo.utils.extensions.onClick
import com.nbs.nucleo.utils.extensions.visible
import com.nbs.validacion.Validation
import com.nbs.validacion.util.minMaxLengthRule
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
    private var time: Timer
    private var defaultTime: Long = 30000
    private var timeLeft: Long = 0
    private var isCountingFinished: Boolean = false

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

        fun newInstance(type: String = LoginType.NORMAL.type, phoneNumber: String): OtpFragment {
            val fragment = OtpFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_OTP_TYPE, type)
            bundle.putString(BundleKeys.KEY_PHONENUMBER, phoneNumber)
            fragment.arguments = bundle
            return fragment
        }
    }

    init {
        time = Timer(defaultTime)
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
        startCountDownTimer()


        when (otpType) {
            LoginType.NEW_ACCOUNT.type -> {
                val subMessage = getString(R.string.sub_message_new_accoun)
                val phoneNumber = String.format(getString(R.string.format_phone_number), phoneNumber)

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

        }

        btnDifferentNumber.onClick {
            listener?.onButtonDifferentNumberClicked(type = otpType)
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
            })
        )
    }

    interface OnOtpListener {
        fun onButtonDifferentNumberClicked(type: String)
        fun onOtpValidationSuccess(type: String, otpCode: String, phoneNumber: String)
    }

    //region CountDownTimer

    private fun startCountDownTimer() {
        isCountingFinished = false
        time.start()
    }

    private fun continueCountDownTimer() {
        isCountingFinished = false
        time = Timer(timeLeft)
        time.start()
    }

    private fun cancelCountDownTimer() {
        time.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(KEY_TIMER_STATE, defaultTime)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        defaultTime = savedInstanceState?.getLong(KEY_TIMER_STATE) ?: 0
    }

    override fun onStop() {
        super.onStop()
        cancelCountDownTimer()
        isCountingFinished = false
    }

    override fun onResume() {
        super.onResume()
        continueCountDownTimer()
    }

    inner class Timer(milis: Long) : CountDownTimer(milis, COUNT_DOWN_TIMER_INTERVAL) {
        override fun onFinish() {
        }

        override fun onTick(milisUntilFinished: Long) {
            timeLeft = milisUntilFinished
            val timeInSeconds = milisUntilFinished / COUNT_DOWN_TIMER_INTERVAL
            btnResendCode.text = getString(R.string.action_resend_code_sec, timeInSeconds.toString())
            btnResendCode.isClickable = false
            debug { "Timer : $milisUntilFinished" }
            if ((milisUntilFinished / COUNT_DOWN_TIMER_INTERVAL) == 0L) {
                btnResendCode.text = getString(R.string.action_resend_code)
                isCountingFinished = true
                btnResendCode.isClickable = true
            }
        }
    }

    //endregion
}
