package com.nbs.humanidui.presentation

import androidx.fragment.app.FragmentManager
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

    private val welcomeDialogFragment = WelcomeDialogFragment.newInstance()

    private val userLoggedInFragment = UserLoggedInFragment.newInstance(this)

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
        welcomeDialogFragment.show(supportFragmentManager, WelcomeDialogFragment::class.java.simpleName)
    }

    private fun showDialogUserLoggedIn(){
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
        if (welcomeDialogFragment.isVisible){
            welcomeDialogFragment.dismissAllowingStateLoss()
        }

        if (userLoggedInFragment.isVisible){
            userLoggedInFragment.dismissAllowingStateLoss()
        }
    }

}