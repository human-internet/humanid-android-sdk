package com.humanid.samples.auth;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import androidx.navigation.Navigation;

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
    public boolean onSupportNavigateUp() {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
        return super.onSupportNavigateUp();
    }
}
