package com.humanid.samples.auth;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.humanid.samples.auth.BR;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements BaseFragment.Callback {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private T mViewDataBinding;
    private V mViewModel;

    @LayoutRes
    protected abstract int getLayoutId();

    protected T getViewDataBinding() {
        return mViewDataBinding;
    }

    protected abstract V getViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(BR.viewModel, mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }

    public void setupActionBar(@NonNull Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    public void setupActionBarUpEnable(boolean enable) {
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    public void setupActionBarTitle(@NonNull String title) {
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setTitle(title);
    }

    public void setupActionBarSubTitle(@NonNull String subtitle) {
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setSubtitle(subtitle);
    }

    public void setFragment(@IdRes int containerId, @NonNull Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
