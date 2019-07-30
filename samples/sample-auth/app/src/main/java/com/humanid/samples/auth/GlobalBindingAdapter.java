package com.humanid.samples.auth;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

public class GlobalBindingAdapter {

    @BindingAdapter("error")
    public static void showError(@NonNull TextInputLayout view, @Nullable String message) {
        if (TextUtils.isEmpty(message)) {
            view.setError(null);
            return;
        }

        view.setError(message);
    }
}
