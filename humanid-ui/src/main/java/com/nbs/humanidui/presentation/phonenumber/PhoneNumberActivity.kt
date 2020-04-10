package com.nbs.humanidui.presentation.phonenumber

import android.app.Activity
import android.content.Intent
import com.nbs.humanidui.R
import com.nbs.humanidui.base.BaseActivity
import com.nbs.humanidui.event.CloseAllActivityEvent
import com.nbs.humanidui.util.BundleKeys
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
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCloseActivityEventReceived(closeAllActivityEvent: CloseAllActivityEvent){
        val intent = Intent()
        closeAllActivityEvent.exchangeToken?.let {
            intent.putExtra(BundleKeys.KEY_USER_HASH, it)
        }

        closeAllActivityEvent.errorMessage?.let {
            intent.putExtra(BundleKeys.KEY_LOGIN_ERROR, it)
        }

        intent.putExtra(BundleKeys.KEY_LOGIN_CANCEL, (closeAllActivityEvent.isCancel))

        setResult(0x300, intent)
        finish()
    }
}
