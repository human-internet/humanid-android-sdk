package com.humanid.auth.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    private final static String TAG = AuthActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthFragment authFragment = AuthFragment.newInstance();
        authFragment.show(getSupportFragmentManager(), TAG);
    }
}
