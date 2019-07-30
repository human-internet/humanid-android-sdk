package com.humanid.samples.auth;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public abstract class BaseViewModel extends AndroidViewModel {

    private final static String TAG = BaseViewModel.class.getSimpleName();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
}
