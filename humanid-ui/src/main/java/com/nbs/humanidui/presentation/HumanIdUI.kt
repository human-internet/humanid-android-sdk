package com.nbs.humanidui.presentation

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nbs.humanidui.presentation.logout.UserLoggedInFragment
import com.nbs.humanidui.presentation.main.MainDialogFragment
import com.nbs.humanidui.presentation.route.Route
import com.nbs.humanidui.presentation.welcome.WelcomeDialogFragment
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.nucleo.utils.showToast

class HumanIdUI(private val supportFragmentManager: FragmentManager): WelcomeDialogFragment.OnWelcomeDialogListener,
        UserLoggedInFragment.OnButtonSwitchDeviceClickListener {

    init {
        WelcomeDialogFragment.listener = this
    }

    fun verifyLogin(){
        val route = Route()
        route.checkIsLoggedIn(onLoggedIn = {
            showDialogUserLoggedIn()
        }, onNotLoggedIn = {
            showDialogWelcome()
        }, onCheckInLoading = {
            showToast("Checking your authentication validity")
        })
    }

    private fun showDialogWelcome(){
        val welcomeDialogFragment = WelcomeDialogFragment.newInstance()
        welcomeDialogFragment.show(supportFragmentManager, WelcomeDialogFragment::class.java.simpleName)
    }

    private fun showDialogUserLoggedIn(){
        val userLoggedInFragment = UserLoggedInFragment.newInstance(this)
        userLoggedInFragment.show(supportFragmentManager, UserLoggedInFragment::class.java.simpleName)
    }

    private fun showDialogMain(){
        val mainDialogFragment = MainDialogFragment.newInstance()
        mainDialogFragment.show(supportFragmentManager, MainDialogFragment::class.java.simpleName)
    }

    override fun onButtonContinueClicked() {
        showDialogMain()
    }

    override fun onButtonSwitchDeviceClicked() {
        val mainDialogFragment = MainDialogFragment.newInstance(LoginType.SWITCH_DEVICE.type)
        mainDialogFragment.show(supportFragmentManager, MainDialogFragment::class.java.simpleName)
    }

    fun closeDialog(){
        supportFragmentManager.fragments.forEach {
            if (it is BottomSheetDialogFragment){
                it.dismissAllowingStateLoss()
            }
        }
    }

}