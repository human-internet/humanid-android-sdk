package com.humanid.auth.tasks;

import android.support.annotation.Nullable;

import com.humanid.auth.HumanIDUser;

public interface AuthResult {

    @Nullable
    HumanIDUser getUser();
}
