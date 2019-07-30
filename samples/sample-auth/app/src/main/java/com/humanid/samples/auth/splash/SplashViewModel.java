package com.humanid.samples.auth.splash;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.humanid.auth.HumanIDAuth;
import com.humanid.samples.auth.BaseViewModel;
import com.humanid.samples.auth.Event;

import java.util.concurrent.TimeUnit;

public class SplashViewModel extends BaseViewModel {

    private final static String TAG = SplashViewModel.class.getSimpleName();

    private final static long NEXT_PAGE_DELAY = TimeUnit.SECONDS.toMillis(3);

    private final MutableLiveData<Event<Object>> eOpenProfile = new MutableLiveData<>();

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openProfile();
            }
        }, NEXT_PAGE_DELAY);
    }

    private void openProfile() {
        eOpenProfile.setValue(new Event<>(new Object()));
    }

    public LiveData<Event<Object>> getEOpenProfile() {
        return eOpenProfile;
    }
}
