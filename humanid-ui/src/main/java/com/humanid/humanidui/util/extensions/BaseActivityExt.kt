package com.humanid.humanidui.util.extensions

import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.humanid.humanidui.base.BaseActivity
import com.humanid.humanidui.base.BaseBottomSheetDialogFragment
import com.humanid.humanidui.base.BaseFragment

val BaseActivity.LOADING_FRAGMENT_TAG: String
    get() = "LOADING_FRAGMENT"

fun BaseActivity.replaceFragment(viewRes: Int, fragment: Fragment, addToBackStack: Boolean) {
    val transaction = this.supportFragmentManager.beginTransaction()

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack so the user can navigate back
    if (addToBackStack) {
        transaction.addToBackStack(null)
    }

    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    transaction.replace(viewRes, fragment)

    // Commit the transaction
    transaction.commit()
}

fun BaseFragment.replaceFragment(viewRes: Int, fragment: Fragment, addToBackStack: Boolean) {
    val transaction = this.childFragmentManager.beginTransaction()

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack so the user can navigate back
    if (addToBackStack) {
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
    }

    transaction.replace(viewRes, fragment)

    // Commit the transaction
    transaction.commit()
}

fun BaseBottomSheetDialogFragment.replaceFragment(viewRes: Int, fragment: Fragment, addToBackStack: Boolean) {
    val transaction = this.childFragmentManager.beginTransaction()

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack so the user can navigate back
    if (addToBackStack) {
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
    }

    transaction.replace(viewRes, fragment)

    // Commit the transaction
    transaction.commit()
}

fun BaseActivity.addFragment(viewRes: Int, fragment: Fragment, addToBackStack: Boolean) {
    val transaction = this.supportFragmentManager.beginTransaction()
    // Add fragment and make stack, it will not destroy previous added fragment
    // and add the transaction to the back stack so the user can navigate back when its true
    transaction.add(viewRes, fragment)
    if (addToBackStack) {
        transaction.addToBackStack(null)
    }
    // Commit the transaction
    transaction.commit()
}

fun BaseActivity.toFullScreen(){
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}