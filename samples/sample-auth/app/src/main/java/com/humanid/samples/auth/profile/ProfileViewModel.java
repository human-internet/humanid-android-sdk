package com.humanid.samples.auth.profile;

import android.app.Application;
import android.support.annotation.NonNull;

import com.humanid.samples.auth.BaseViewModel;

public class ProfileViewModel extends BaseViewModel {

    private final static String TAG = ProfileViewModel.class.getSimpleName();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }
}
