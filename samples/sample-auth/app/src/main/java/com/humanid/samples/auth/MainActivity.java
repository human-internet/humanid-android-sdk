package com.humanid.samples.auth;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import androidx.navigation.Navigation;

import com.humanid.auth.ui.AuthActivity;
import com.humanid.samples.auth.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityViewModel getViewModel() {
        return ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(MainActivityViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, AuthActivity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
        return super.onSupportNavigateUp();
    }
}
