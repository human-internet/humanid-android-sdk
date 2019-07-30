package com.humanid.samples.auth.splash;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import androidx.navigation.fragment.NavHostFragment;

import com.humanid.samples.auth.BaseFragment;
import com.humanid.samples.auth.Event;
import com.humanid.samples.auth.R;
import com.humanid.samples.auth.databinding.FragmentSplashBinding;

public class SplashFragment extends BaseFragment<FragmentSplashBinding, SplashViewModel> {

    private final static String TAG = SplashFragment.class.getSimpleName();

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected SplashViewModel getViewModel() {
        return ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getBaseActivity().getApplication())).get(SplashViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
        getViewModel().start();
    }

    private void setListener() {
        getViewModel().getEOpenProfile().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(@Nullable Event<Object> objectEvent) {
                if (objectEvent != null && !objectEvent.hasBeenHandled()) {
                    openMain();
                }
            }
        });
    }

    private void openMain() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_splashFragment_to_profileFragment);
    }
}
