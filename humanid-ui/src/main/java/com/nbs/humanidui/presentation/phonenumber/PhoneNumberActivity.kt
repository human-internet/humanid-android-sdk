package com.nbs.humanidui.presentation.phonenumber

import android.content.Context
import android.content.Intent
import com.nbs.humanidui.R
import com.nbs.humanidui.base.BaseActivity
import com.nbs.humanidui.util.enum.LoginType

class PhoneNumberActivity : BaseActivity(), PhoneNumberFragment.OnPhoneNumberListener {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, PhoneNumberActivity::class.java))
        }
    }

    override val layoutResource: Int
        get() = R.layout.activity_phone_number

    override fun initLib() {

    }

    override fun initIntent() {

    }

    override fun initUI() {

    }

    override fun initAction() {

    }

    override fun initProcess() {
        val phoneNumberFragment = PhoneNumberFragment.newInstance(type = LoginType.NEW_ACCOUNT.type,
            phoneNumberListener = this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerPhoneNumber, phoneNumberFragment)
                .commitAllowingStateLoss()
    }

    override fun onButtonCancelClicked(type: String) {

    }

    override fun onButtonEnterClicked(countryCode: String, type: String, phoneNumber: String) {
        if (type == LoginType.NEW_ACCOUNT.type){

        }
    }

    override fun onButtonTransferClicked() {

    }
}
