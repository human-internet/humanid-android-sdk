package com.nbs.humanidui.presentation.phonenumber

import android.content.Context
import android.content.Intent
import com.nbs.humanidui.R
import com.nbs.humanidui.base.BaseActivity
import com.nbs.humanidui.presentation.otp.OtpActivity
import org.greenrobot.eventbus.EventBus

class PhoneNumberActivity : BaseActivity(), PhoneNumberFragment.OnPhoneNumberListener {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, PhoneNumberActivity::class.java))
        }
    }

    override val layoutResource: Int
        get() = R.layout.activity_phone_number

    override fun initLib() {
        EventBus.getDefault().register(this)
    }

    override fun initIntent() {

    }

    override fun initUI() {

    }

    override fun initAction() {

    }

    override fun initProcess() {
        val phoneNumberFragment = PhoneNumberFragment.newInstance(phoneNumberListener = this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerPhoneNumber, phoneNumberFragment)
                .commitAllowingStateLoss()
    }

    override fun onButtonCancelClicked() {
        finishActivity()
    }

    override fun onRequestOtpSucceed(countryCode: String, phoneNumber: String) {
        OtpActivity.start(this, phoneNumber = phoneNumber, countryCode = countryCode)
        finishActivity()
    }

    override fun onButtonTransferClicked() {

    }
}
