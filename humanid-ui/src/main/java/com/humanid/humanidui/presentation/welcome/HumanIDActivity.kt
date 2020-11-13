package com.humanid.humanidui.presentation.welcome

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.humanid.humanidui.R
import com.humanid.humanidui.base.BaseActivity
import com.humanid.humanidui.event.CloseAllActivityEvent
import com.humanid.humanidui.presentation.phonenumber.PhoneNumberActivity
import com.humanid.humanidui.util.BundleKeys
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HumanIDActivity : BaseActivity(), WelcomeDialogFragment.OnWelcomeDialogListener {
    companion object{
        @JvmStatic
        fun start(activity: Activity){
            val intent = Intent(activity, HumanIDActivity::class.java)
            activity.startActivityForResult(intent, 200)
        }
    }

    private var welcomeDialogFragment: WelcomeDialogFragment? = null

    override val layoutResource: Int
        get() = R.layout.activity_humanid

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
        welcomeDialogFragment = WelcomeDialogFragment.newInstance()
        WelcomeDialogFragment.listener = this
        welcomeDialogFragment?.show(supportFragmentManager, WelcomeDialogFragment::class.java.simpleName)
    }

    override fun onButtonContinueClicked() {
        PhoneNumberActivity.start(this)
    }



    override fun onDestroy() {
        welcomeDialogFragment?.let {
            if (it.isVisible){
                it.dismissAllowingStateLoss()
                welcomeDialogFragment = null
            }
        }
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCloseActivityEventReceived(closeAllActivityEvent: CloseAllActivityEvent){
        val intent = Intent()
        if (!closeAllActivityEvent.exchangeToken.isNullOrEmpty()){
            intent.putExtra(BundleKeys.KEY_EXCHANGE_TOKEN, closeAllActivityEvent.exchangeToken)
        }else if(!closeAllActivityEvent.errorMessage.isNullOrEmpty()){
            intent.putExtra(BundleKeys.KEY_LOGIN_ERROR, closeAllActivityEvent.errorMessage)
        }else{
            intent.putExtra(BundleKeys.KEY_LOGIN_CANCEL, (closeAllActivityEvent.isCancel))
        }
        setResult(0x300, intent)
        finish()
    }
}
