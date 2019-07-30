package com.humanid.samples.auth;

import android.app.Application;
import android.support.annotation.NonNull;

public class MainActivityViewModel extends BaseViewModel {

    private final static String TAG = MainActivityViewModel.class.getSimpleName();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
}
