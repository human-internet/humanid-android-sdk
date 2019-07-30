package com.humanid.samples.auth.auth;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import androidx.navigation.fragment.NavHostFragment;

import com.humanid.samples.auth.BaseFragment;
import com.humanid.samples.auth.Event;
import com.humanid.samples.auth.R;
import com.humanid.samples.auth.databinding.FragmentAuthBinding;

public class AuthFragment extends BaseFragment<FragmentAuthBinding, AuthViewModel> {

    private final static String TAG = AuthFragment.class.getSimpleName();

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance() {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_auth;
    }

    @Override
    protected AuthViewModel getViewModel() {
        return ViewModelProviders.of(requireActivity(), ViewModelProvider.AndroidViewModelFactory
                .getInstance(getBaseActivity().getApplication())).get(AuthViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {
        getViewModel().getSnackBar().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(@Nullable Event<String> event) {
                if (event == null) return;

                String message = event.getContentIfNotHandled();
                if (!TextUtils.isEmpty(message)) {
                    showSnackBar(message);
                }
            }
        });

        getViewModel().getAuthenticationState().observe(this,
                new Observer<AuthViewModel.AuthenticationState>() {
                    @Override
                    public void onChanged(@Nullable AuthViewModel.AuthenticationState state) {
                        if (state == null) return;
                        switch (state) {
                            case AUTHENTICATED:
                                onAuthenticated();
                                break;
                            case UNAUTHENTICATED:
                                break;
                            case INVALID_AUTHENTICATION:
                                break;
                        }
                    }
                });
    }

    private void showSnackBar(@NonNull String message) {
        Snackbar.make(getViewDataBinding().clFooter, message, Snackbar.LENGTH_SHORT).show();
    }

    private void onAuthenticated() {
        NavHostFragment.findNavController(this).popBackStack();
    }
}
