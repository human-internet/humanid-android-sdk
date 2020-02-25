package com.nbs.humanidui.presentation.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_UP
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.humanid.auth.HumanIDAuth
import com.nbs.humanidui.R
import com.nbs.humanidui.base.BaseBottomSheetDialogFragment
import com.nbs.humanidui.presentation.otp.OtpFragment
import com.nbs.humanidui.presentation.phonenumber.PhoneNumberFragment
import com.nbs.humanidui.presentation.phonenumberemail.PhoneNumberEmailFragment
import com.nbs.humanidui.presentation.registeremail.RegisterEmailFragment
import com.nbs.humanidui.presentation.termandcondition.TermsAndConditionFragment
import com.nbs.humanidui.util.BundleKeys
import com.nbs.humanidui.util.emptyString
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.humanidui.util.extensions.replaceFragment
import com.nbs.humanidui.util.extensions.showToast

class MainDialogFragment : BaseBottomSheetDialogFragment(),
        RegisterEmailFragment.OnRegisterEmailListener,
        OtpFragment.OnOtpListener,
        PhoneNumberFragment.OnPhoneNumberListener,
        TermsAndConditionFragment.OnTermsConditionListener,
        PhoneNumberEmailFragment.OnPhoneEmailListener{

    companion object {
        fun newInstance(): MainDialogFragment {
            val fragment = MainDialogFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(loginType: String): MainDialogFragment {
            val fragment = MainDialogFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_LOGIN_TYPE, loginType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var loginType: String ?= emptyString()

    override val layoutResource: Int = R.layout.layout_bottomsheet_white_dialog

    override fun initLib() {
        PhoneNumberFragment.listener = this
        OtpFragment.listener = this
        RegisterEmailFragment.listener = this
        TermsAndConditionFragment.listener = this
        PhoneNumberEmailFragment.listener = this
    }

    override fun initIntent() {
        arguments?.let {
            if (it.containsKey(BundleKeys.KEY_LOGIN_TYPE)){
                loginType = it.getString(BundleKeys.KEY_LOGIN_TYPE)
            }
        }
    }

    override fun initUI() {
        if (!loginType.isNullOrEmpty()){
            replaceFragment(R.id.flDialog, PhoneNumberFragment.newInstance(LoginType.SWITCH_DEVICE.type), false)
        }else{
            replaceFragment(R.id.flDialog, PhoneNumberFragment.newInstance(LoginType.NEW_ACCOUNT.type), false)
        }
    }

    override fun initAction() {
        dialog?.setOnKeyListener { _, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.action == ACTION_UP) {
                if (childFragmentManager.backStackEntryCount > 0)
                    childFragmentManager.popBackStackImmediate()
                else
                    dismiss()
            }

            return@setOnKeyListener false
        }

    }

    override fun initProcess() {

    }

    override fun onButtonCancelClicked(type: String) {
        if (type == LoginType.SWITCH_NUMBER.type) {
            if (childFragmentManager.backStackEntryCount > 0)
                childFragmentManager.popBackStackImmediate()
            else dismiss()
        } else  dismiss()
    }

    override fun onButtonWrongAccountClicked() {
        dismiss()
    }

    override fun onButtonSaveClicked() {
        dismiss()
    }

    override fun onButtonSkipEmailClicked() {
        replaceFragment(R.id.flDialog, TermsAndConditionFragment.newInstance(), true)
    }

    override fun onOtpValidationSuccess(type: String, otpCode: String, countryCode: String, phoneNumber: String) {
        when (type) {
            LoginType.SWITCH_DEVICE.type ->
                switchDevice(otpCode = otpCode,
                        phoneNumber = phoneNumber,
                        countryCode = countryCode)
            LoginType.SWITCH_NUMBER.type ->
                replaceFragment(R.id.flDialog, PhoneNumberEmailFragment.newInstance(), false)
            LoginType.NORMAL.type ->
//                replaceFragment(R.id.flDialog, RegisterEmailFragment.newInstance(LoginType.NORMAL.type), false)
            verifyOtp(otpCode = otpCode,
                    phoneNumber = phoneNumber,
                    countryCode = countryCode)
        }
    }

    private fun switchDevice(otpCode: String, countryCode: String, phoneNumber: String) {
        showLoading()
        HumanIDAuth.getInstance().currentUser?.userHash?.let {
            HumanIDAuth.getInstance().updatePhone(countryCode.replace("+", "").trim(), phoneNumber, otpCode,
                    it).addOnCompleteListener {
                hideLoading()
                dismiss()
                if (it.isSuccessful){
                    showToast("Switch Device Succeeded")
                }else{
                    showToast("Switch Device Failed")
                }
            }.addOnFailureListener {
                hideLoading()
                dismiss()
                showToast(it.message.toString())
            }
        }
    }

    override fun onButtonDifferentNumberClicked(otpType: String) {
        loginType = otpType
        initUI()
    }

    private fun verifyOtp(otpCode: String, countryCode: String, phoneNumber: String){
        showLoading()
        HumanIDAuth.getInstance().register(countryCode.replace("+", "").trim(), phoneNumber, otpCode)
                .addOnCompleteListener {
                    hideLoading()
                    if (it.isSuccessful){
                        it.result?.userHash?.let { it1 -> login(it1) }
                    }else{
                        showToast("Verify otp failed")
                    }

                }.addOnFailureListener {
                    hideLoading()
                    it.message?.let {message ->
                        if (message.contains("Existing login found on deviceId")){
                            showToast("Login succeeded - Phone number not stored")
                            dismissAllowingStateLoss()
                        }else{
                            showToast(message)
                        }
                    }
                }
    }

    private fun login(userHash: String){
        showLoading()
        HumanIDAuth.getInstance().login(userHash)
                .addOnCompleteListener {
                    hideLoading()
                    if (it.isSuccessful){
                        dismissAllowingStateLoss()
                        showToast("Login succeeded - Phone number not stored")
                    }else{
                        showToast("Login failed")
                    }
                }.addOnFailureListener {
                    hideLoading()
                    dismissAllowingStateLoss()
                    showToast(it.message.toString())
                }
    }

    override fun onButtonSkipClicked() {
        dismiss()
    }

    override fun onButtonTransferClicked() {
        replaceFragment(R.id.flDialog, PhoneNumberFragment.newInstance(LoginType.SWITCH_NUMBER.type), true)
    }

    override fun onButtonEnterClicked(countryCode: String, type: String, phoneNumber: String) {
        when (type) {
            LoginType.NORMAL.type -> {
                replaceFragment(R.id.flDialog, OtpFragment.newInstance(LoginType.SWITCH_DEVICE.type), true)
            }
            LoginType.NEW_ACCOUNT.type -> {
                replaceFragment(R.id.flDialog, OtpFragment.newInstance(type = LoginType.NEW_ACCOUNT.type,
                        phoneNumber = phoneNumber,
                        countryCode = countryCode), true)
            }
            LoginType.SWITCH_DEVICE.type -> {
                replaceFragment(R.id.flDialog, OtpFragment.newInstance(type = LoginType.SWITCH_DEVICE.type,
                        phoneNumber = phoneNumber,
                        countryCode = countryCode), true)
            }
        }
    }

    override fun onButtonEnterClicked() {
        replaceFragment(R.id.flDialog, RegisterEmailFragment.newInstance(LoginType.INPUT_OTP.type), true)
    }

    override fun onButtonBackClicked() {
        if (childFragmentManager.backStackEntryCount > 0)
            childFragmentManager.popBackStackImmediate()
        else dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(R.style.AppBottomSheetDialogTheme, R.style.AppTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setWhiteNavigationBar(dialog)
        }

        return dialog
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setWhiteNavigationBar(@NonNull dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            val metrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(metrics)

            val dimDrawable = GradientDrawable()
            // ...customize your dim effect here

            val navigationBarDrawable = GradientDrawable()
            navigationBarDrawable.shape = GradientDrawable.RECTANGLE
            navigationBarDrawable.setColor(Color.WHITE)

            val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)

            val windowBackground = LayerDrawable(layers)
            windowBackground.setLayerInsetTop(1, metrics.heightPixels)

            window.setBackgroundDrawable(windowBackground)
        }
    }

}
