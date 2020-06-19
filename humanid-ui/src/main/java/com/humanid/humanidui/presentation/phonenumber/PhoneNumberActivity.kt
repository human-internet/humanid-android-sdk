package com.humanid.humanidui.presentation.phonenumber

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.humanid.humanidui.R
import com.humanid.humanidui.base.BaseActivity
import com.humanid.humanidui.event.CloseAllActivityEvent
import com.humanid.humanidui.util.BundleKeys
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PhoneNumberActivity : BaseActivity(), PhoneNumberFragment.OnPhoneNumberListener{

    companion object{
        fun start(activity: Activity){
            val intent = Intent(activity, PhoneNumberActivity::class.java)
            activity.startActivityForResult(intent, 200)
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
        val phoneNumberFragment = PhoneNumberFragment.newInstance(phoneNumberListener = this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerPhoneNumber, phoneNumberFragment)
                .commitAllowingStateLoss()
    }

    override fun onButtonCancelClicked() {
        EventBus.getDefault().post(CloseAllActivityEvent(isCancel = true, exchangeToken = null, errorMessage = null))
        finishActivity()
    }

    override fun onBackPressed() {
        EventBus.getDefault().post(CloseAllActivityEvent(isCancel = true, exchangeToken = null, errorMessage = null))
        super.onBackPressed()
    }

    override fun onButtonTransferClicked() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
