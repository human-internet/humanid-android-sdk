package com.humanid.humanidui.presentation.otp

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.humanid.auth.HumanIDAuth
import com.humanid.humanidui.R
import com.humanid.humanidui.R.string
import com.humanid.humanidui.util.BundleKeys
import com.humanid.humanidui.util.PassiveFormFragment
import com.humanid.humanidui.util.emptyString
import com.humanid.humanidui.util.extensions.gone
import com.humanid.humanidui.util.extensions.onClick
import com.humanid.humanidui.util.extensions.showToast
import com.humanid.humanidui.util.extensions.toHtml
import com.humanid.humanidui.util.makeLinks
import com.humanid.humanidui.util.validation.Validation
import com.humanid.humanidui.util.validation.util.minMaxLengthRule
import kotlinx.android.synthetic.main.fragment_otp.btnDifferentNumber
import kotlinx.android.synthetic.main.fragment_otp.btnResendCode
import kotlinx.android.synthetic.main.fragment_otp.edtOtp
import kotlinx.android.synthetic.main.fragment_otp.tvMessage
import kotlinx.android.synthetic.main.fragment_otp.tvSubMessage
import kotlinx.android.synthetic.main.fragment_otp.tvSwitchMessage

class OtpFragment : PassiveFormFragment() {

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

        fun newInstance(
            countryCode: String,
            phoneNumber: String,
            onVerifyOtpListener: OnVerifyOtpListener
        ): OtpFragment {
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
        requestFocusAndShowKeyboard(requireContext())
        setSpannableString()
        startCountDownTimer()
        updateView()
    }

    private fun updateView() {
        val message = String.format(getString(R.string.message_send_otp_switch), countryCode + phoneNumber)
        val subMessage = getString(R.string.sub_message_new_accoun)
        val phoneNumber = String.format(getString(R.string.format_phone_number), countryCode + phoneNumber)

        tvMessage.text = message
        tvSubMessage.text = toHtml(subMessage + phoneNumber)
        tvSwitchMessage.gone()
        btnDifferentNumber.gone()
    }

    override fun initAction() {
        btnResendCode.onClick {
            edtOtp.setText("")
            resendOtp()
        }

        edtOtp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validate()
            }
        })
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
                    btnResendCode.text = getString(string.action_resend_code)
                }
            }.addOnFailureListener {
                btnResendCode.text = getString(string.action_resend_code)
                btnResendCode.isClickable = true
            }
    }

    override fun initProcess() {
        setupFormValidation()
    }

    override fun setupFormValidation() {
        addValidation(
            Validation(
                edtOtp,
                listOf(minMaxLengthRule(emptyString(), 4, 4))
            )
        )
    }

    override fun onValidationFailed() {}

    override fun onValidationSuccess() {
        hideSoftKeyboard(edtOtp, requireContext())
        cancelCountDownTimer()
        val otpCode: String = edtOtp.text.toString().trim()
        verifyOtp(otpCode)
    }

    private fun verifyOtp(otpCode: String) {
        showLoading()
        cancelCountDownTimer()
        HumanIDAuth.getInstance().login(countryCode.replace("+", "").trim(), phoneNumber, otpCode)
            .addOnCompleteListener {
                hideLoading()
                if (it.isSuccessful) {
                    it.result?.exchangeToken?.let { userHash ->
                        onVerifyOtpListener.onVerifySuccess(userHash)
                    }
                }

            }.addOnFailureListener {
                hideLoading()
                it.message?.let { message ->
                    if (message.contains("Existing login found on deviceId")) {
                        showToast(getString(string.message_login_succeeded))
                    } else {
                        showOtpFailedDialog()
                    }
                }
            }
    }

    private fun showOtpFailedDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(getString(string.error_message_invalid_veridication_code))
                .setPositiveButton(getString(string.action_close), DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    edtOtp.editableText?.clear()
                    requestFocusAndShowKeyboard(it)
                })
                .show()
        }
    }

    private fun requestFocusAndShowKeyboard(context: Context) {
        showKeyboard(edtOtp, context)
        edtOtp.requestFocus()
    }

    private fun setSpannableString() {
        tvSwitchMessage.text = getString(R.string.message_humanid_description)
        tvSwitchMessage.makeLinks(
            Pair(getString(R.string.label_learn_about_out_mission), View.OnClickListener {
                Toast.makeText(context, getString(R.string.label_learn_about_out_mission), Toast.LENGTH_SHORT).show()
            })
        )
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
                    btnResendCode.text = getString(string.action_resend_code)
                    isCountingFinished = true
                    btnResendCode.isClickable = true
                    btnResendCode.setTextColor(resources.getColor(R.color.colorTwilightBlue))
                } else {
                    btnResendCode.text = getString(string.action_resend_code_sec, "${timeInSeconds}s")
                    btnResendCode.isClickable = false
                    btnResendCode.setTextColor(resources.getColor(R.color.colorWarmGrey))
                }
            }
        }
    }

    //endregion

    interface OnVerifyOtpListener {
        fun onVerifySuccess(exchangeToken: String)

        fun onVerifyFailed(message: String)
    }
}
