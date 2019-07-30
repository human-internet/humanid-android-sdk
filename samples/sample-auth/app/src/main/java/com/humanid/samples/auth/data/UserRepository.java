package com.humanid.samples.auth.data;

import android.content.Context;
import android.support.annotation.NonNull;

public class UserRepository {

    private final static String TAG = UserRepository.class.getSimpleName();

    private static volatile UserRepository INSTANCE;

    private final Context applicationContext;

    private UserRepository(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static UserRepository getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepository(context.getApplicationContext());
                }
            }
        }

        return INSTANCE;
    }
}
