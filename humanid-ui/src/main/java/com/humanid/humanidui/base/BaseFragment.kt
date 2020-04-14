package com.humanid.humanidui.base


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseView {

    private var baseActivity: BaseActivity? = null

    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(layoutResource, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onViewReady()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.baseActivity = activity
            activity?.onFragmentAttached()
        }
    }

    override fun showLoading() {
        if (baseActivity != null) {
            baseActivity?.showLoading()
        }
    }

    override fun hideLoading() {
        if (baseActivity != null) {
            baseActivity?.hideLoading()
        }
    }

    override fun setupToolbar(toolbar: Toolbar?, title: String, isChild: Boolean) {
        baseActivity?.setupToolbar(toolbar, title, isChild)
    }

    override fun setupToolbar(title: String, isChild: Boolean) {
        baseActivity?.setupToolbar(title, isChild)
    }

    override fun setupToolbar(toolbar: Toolbar?, isChild: Boolean) {
        baseActivity?.setupToolbar(toolbar, isChild)
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }
    
    override fun finishActivity() {
        baseActivity?.finishActivity()
    }

    private fun onViewReady() {
        initLib()
        initIntent()
        initUI()
        initAction()
        initProcess()
    }

    protected fun hideSoftKeyboard(){
        val imm: InputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
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


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}