package com.nbs.humanidui.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.nbs.humanidui.R
import com.nbs.humanidui.event.CloseAllActivityEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : AppCompatActivity(), BaseView, BaseFragment.Callback {

    private var mProgressDialog: ProgressDialog? = null

    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResource)

        onViewReady()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun setupToolbar(toolbar: Toolbar?, title: String, isChild: Boolean) {
        toolbar?.let {
            setSupportActionBar(toolbar)
        }

        if (supportActionBar != null) {
            supportActionBar!!.title = title
            supportActionBar!!.setDisplayHomeAsUpEnabled(isChild)
        }
    }

    override fun setupToolbar(toolbar: Toolbar?, isChild: Boolean) {
        toolbar?.let {
            setSupportActionBar(toolbar)
        }

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(isChild)
        }
    }

    override fun setupToolbar(title: String, isChild: Boolean) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
            supportActionBar!!.setDisplayHomeAsUpEnabled(isChild)
        }
    }

    override fun showLoading() {
        hideLoading()

        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog!!.setMessage(getString(R.string.please_wait))
            mProgressDialog!!.isIndeterminate = true
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setCanceledOnTouchOutside(false)
        }
        mProgressDialog!!.show()
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    override fun finishActivity() {
        finish()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    fun setFragment(viewRes: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(viewRes, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        // Commit the transaction
        transaction.commit()
    }

    private fun onViewReady() {
        initLib()
        initIntent()
        initUI()
        initAction()
        initProcess()
    }

    //    Init Presenter and Component Injection here
    protected abstract fun initLib()

    //    Extract desired intent here
    protected abstract fun initIntent()

    //    initialize the UI, setup toolbar, setText etc here
    protected abstract fun initUI()

    //    initialize UI interaction here
    protected abstract fun initAction()

    //    initialize main Process here e.g call presenter to load data
    protected abstract fun initProcess()

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCloseActivityEventReceived(closeAllActivityEvent: CloseAllActivityEvent){
        finish()
    }
}