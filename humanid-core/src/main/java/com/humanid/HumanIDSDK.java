package com.humanid;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humanid.internal.Preconditions;

public final class HumanIDSDK {

    private final static String TAG = HumanIDSDK.class.getSimpleName();

    private static HumanIDSDK INSATNCE;
    private Context mApplicationContex;

    private HumanIDSDK(Context applicationContext) {
        mApplicationContex = Preconditions.checkNotNull(applicationContext);
    }

    public static HumanIDSDK getInstance(@NonNull Application application) {
        if (INSATNCE == null) {
            synchronized (HumanIDSDK.class) {
                if (INSATNCE == null) {
                    INSATNCE = new HumanIDSDK(application);
                }
            }
        }
        return INSATNCE;
    }

    @NonNull
    public static HumanIDSDK getInstance() {
        synchronized (HumanIDSDK.class) {
            if (INSATNCE == null) {
                throw new IllegalStateException("HumanIDSDK is not initialized");
            }
            return INSATNCE;
        }
    }

    @Nullable
    public static HumanIDSDK initialize(@NonNull Context context) {
        if (INSATNCE == null) {
            synchronized (HumanIDSDK.class) {
                if (INSATNCE == null) {
                    INSATNCE = new HumanIDSDK(context);
                }
            }
        }
        return INSATNCE;
    }
}
