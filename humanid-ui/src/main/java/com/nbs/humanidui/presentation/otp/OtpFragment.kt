package com.nbs.humanidui.presentation.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.humanid.auth.HumanIDAuth
import com.nbs.humanidui.R
import com.nbs.humanidui.R.string
import com.nbs.humanidui.util.BundleKeys
import com.nbs.humanidui.util.ReactiveFormFragment
import com.nbs.humanidui.util.emptyString
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.humanidui.util.extensions.*
import com.nbs.humanidui.util.makeLinks
import com.nbs.humanidui.util.validation.Validation
import com.nbs.humanidui.util.validation.util.minMaxLengthRule
import kotlinx.android.synthetic.main.fragment_otp.*

class OtpFragment : ReactiveFormFragment() {

    private var phoneNumber: String = emptyString()
    private var countryCode: String = emptyString()
    private lateinit var onVerifyOtpListener: OnVerifyOtpListener

    //CountDownTimer
    private var time: Timer? = null
    private var defaultTime: Long = 30000
    private var timeLeft: Long = 0
    private var isCountingFinished: Boolean = false

    private val TAG = this.javaClass.simpleName

    companion object {
        private const val KEY_TIMER_STATE: String = "timer_state"
        private const val COUNT_DOWN_TIMER_INTERVAL: Long = 1000

        var listener: OnOtpListener? = null

        fun newInstance(countryCode: String, phoneNumber: String, onVerifyOtpListener: OnVerifyOtpListener): OtpFragment {
            val fragment = OtpFragment()
            fragment.onVerifyOtpListener = onVerifyOtpListener

            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_PHONENUMBER, phoneNumber)
            bundle.putString(BundleKeys.KEY_COUNTRY_CODE, countryCode)

            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutResource: Int = R.layout.fragment_otp

    override fun initIntent() {
        arguments?.let {
            phoneNumber = it.getString(BundleKeys.KEY_PHONENUMBER) ?: emptyString()
            countryCode = it.getString(BundleKeys.KEY_COUNTRY_CODE) ?: emptyString()
        }
    }

    override fun initUI() {
        setSpannableString()
        startCountDownTimer()
        updateView()
    }

    private fun updateView() {
        val subMessage = getString(R.string.sub_message_new_accoun)
        val phoneNumber = String.format(getString(R.string.format_phone_number), countryCode + phoneNumber)

        tvSubMessage.text = toHtml(subMessage + phoneNumber)
        tvSwitchMessage.gone()
        btnDifferentNumber.gone()
    }

    override fun initAction() {
        btnResendCode.onClick {
            edtOtp.setText("")
            resendOtp()
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
        cancelCountDownTimer()
        val otpCode: String = edtOtp.text.toString().trim()
        verifyOtp(otpCode)
    }

    private fun verifyOtp(otpCode: String) {
        showLoading()
        HumanIDAuth.getInstance().register(countryCode.replace("+", "").trim(), phoneNumber, otpCode)
                .addOnCompleteListener {
                    hideLoading()
                    if (it.isSuccessful) {
                        it.result?.userHash?.let { userHash ->
                            onVerifyOtpListener.onVerifySuccess(userHash)
                        }
                    } else {
                        onVerifyOtpListener.onVerifyFailed(getString(string.error_message_verify_otp_failed))
                    }

                }.addOnFailureListener {
                    hideLoading()
                    it.message?.let { message ->
                        if (message.contains("Existing login found on deviceId")) {
                            onVerifyOtpListener.onVerifyFailed(getString(string.message_login_succeeded))
                        } else {
                            onVerifyOtpListener.onVerifyFailed(message)
                        }
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
                    btnResendCode.setTextColor(resources.getColor(R.color.colorTwilightBlue))
                } else {
                    btnResendCode.text = getString(R.string.action_resend_code_sec, "${timeInSeconds}s")
                    btnResendCode.isClickable = false
                    btnResendCode.setTextColor(resources.getColor(R.color.colorWarmGrey))
                }
            }
        }
    }

    //endregion

    interface OnVerifyOtpListener{
        fun onVerifySuccess(userHash: String)

        fun onVerifyFailed(message: String)
    }
}
