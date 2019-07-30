package com.humanid.samples.auth;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humanid.samples.auth.BR;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private BaseActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract V getViewModel();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(BR.viewModel, mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }

    protected BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void setupActionBarUpEnable(boolean enable) {
        if (mActivity == null) return;
        mActivity.setupActionBarUpEnable(enable);
    }

    public void setupActionBarTitle(@NonNull String title){
        if (mActivity == null) return;
        mActivity.setupActionBarTitle(title);
    }

    public void setupActionBarSubTitle(@NonNull String subtitle){
        if (mActivity == null) return;
        mActivity.setupActionBarSubTitle(subtitle);
    }

    public void hideKeyboard() {
        if (mActivity == null) return;
        mActivity.hideKeyboard();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
