package com.humanid.samples.auth.profile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import androidx.navigation.fragment.NavHostFragment;

import com.humanid.samples.auth.BaseFragment;
import com.humanid.samples.auth.R;
import com.humanid.samples.auth.auth.AuthViewModel;
import com.humanid.samples.auth.databinding.FragmentProfileBinding;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {

    private final static String TAG = ProfileFragment.class.getSimpleName();

    private AuthViewModel authViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected ProfileViewModel getViewModel() {
        return ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getBaseActivity().getApplication())).get(ProfileViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = ViewModelProviders.of(requireActivity(), ViewModelProvider.AndroidViewModelFactory
                .getInstance(getBaseActivity().getApplication())).get(AuthViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {
        authViewModel.getAuthenticationState().observe(this,
                new Observer<AuthViewModel.AuthenticationState>() {
                    @Override
                    public void onChanged(@Nullable AuthViewModel.AuthenticationState state) {
                        if (state == null) return;
                        switch (state) {
                            case AUTHENTICATED:
                                break;
                            case UNAUTHENTICATED:
                                needToAuth();
                                break;
                            case INVALID_AUTHENTICATION:
                                break;
                        }
                    }
                });
    }

    private void needToAuth() {
        NavHostFragment.findNavController(this).navigate(R.id.authFragment);
    }
}
