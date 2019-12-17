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
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.human.android.util.extensions.replaceFragment
import com.humanid.auth.HumanIDAuth
import com.humanid.auth.HumanIDUser
import com.nbs.humanidui.R
import com.nbs.humanidui.base.BaseBottomSheetDialogFragment
import com.nbs.humanidui.presentation.logout.LogoutFragment
import com.nbs.humanidui.presentation.otp.OtpFragment
import com.nbs.humanidui.presentation.phonenumber.PhoneNumberFragment
import com.nbs.humanidui.presentation.phonenumberemail.PhoneNumberEmailFragment
import com.nbs.humanidui.presentation.registeremail.RegisterEmailFragment
import com.nbs.humanidui.presentation.termandcondition.TermsAndConditionFragment
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.nucleo.utils.showToast
import io.reactivex.annotations.NonNull


/**
 * A simple [Fragment] subclass.
 */
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
    }

    override val layoutResource: Int = R.layout.layout_bottomsheet_white_dialog

    override fun initLib() {
        PhoneNumberFragment.listener = this
        OtpFragment.listener = this
        RegisterEmailFragment.listener = this
        TermsAndConditionFragment.listener = this
        PhoneNumberEmailFragment.listener = this
    }

    override fun initIntent() {
    }

    override fun initUI() {
        if (HumanIDAuth.getInstance().currentUser != null){
            dismiss()
            replaceFragment(R.id.flDialog, LogoutFragment.newInstance(), false)
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

    override fun onOtpValidationSuccess(type: String, otpCode: String, phoneNumber: String) {
        when (type) {
            LoginType.SWITCH_DEVICE.type ->
                dismiss()
            LoginType.SWITCH_NUMBER.type ->
                replaceFragment(R.id.flDialog, PhoneNumberEmailFragment.newInstance(), false)
            LoginType.NORMAL.type ->
//                replaceFragment(R.id.flDialog, RegisterEmailFragment.newInstance(LoginType.NORMAL.type), false)
            verifyOtp(otpCode, phoneNumber)
        }
    }

    override fun onButtonDifferentNumberClicked() {

    }

    private fun verifyOtp(otpCode: String, phoneNumber: String){
        showLoading()
        HumanIDAuth.getInstance().register("62", phoneNumber, otpCode)
                .addOnCompleteListener {
                    hideLoading()
                    if (it.isSuccessful){
                        checkLogin(it.result)
                    }else{
                        showToast("Verify otp failed")
                    }

                }.addOnFailureListener {
                    hideLoading()
                    showToast(it.message.toString())
                }
    }

    private fun checkLogin(result: HumanIDUser?) {
        showLoading()
        result?.let {
            HumanIDAuth.getInstance().loginCheck(it.userHash)
                    .addOnCompleteListener {task ->
                        hideLoading()
                        if (task.isSuccessful){
                            login(result.userHash)
                        }else{
                            showToast("Login Check Failed")
                        }
                    }.addOnFailureListener {exception ->
                        hideLoading()
                        showToast(exception.message.toString())
                    }
        }
    }

    private fun login(userHash: String){
        showLoading()
        HumanIDAuth.getInstance().login(userHash)
                .addOnCompleteListener {
                    hideLoading()
                    if (it.isSuccessful){
                        showToast("Login succeed")
                        dismiss()
                    }else{
                        showToast("Login failed")
                    }
                }.addOnFailureListener {
                    hideLoading()
                    showToast(it.message.toString())
                }
    }

    override fun onButtonSkipClicked() {
        dismiss()
    }

    override fun onButtonTransferClicked() {
        replaceFragment(R.id.flDialog, PhoneNumberFragment.newInstance(LoginType.SWITCH_NUMBER.type), true)
    }

    override fun onButtonEnterClicked(type: String, phoneNumber: String) {
        when (type) {
            LoginType.NORMAL.type -> {
                replaceFragment(R.id.flDialog, OtpFragment.newInstance(LoginType.SWITCH_DEVICE.type), true)
            }
            LoginType.NEW_ACCOUNT.type -> {
                replaceFragment(R.id.flDialog, OtpFragment.newInstance(LoginType.NEW_ACCOUNT.type, phoneNumber), true)
            }
            LoginType.SWITCH_NUMBER.type -> {
                replaceFragment(R.id.flDialog, OtpFragment.newInstance(LoginType.SWITCH_NUMBER.type), true)
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
