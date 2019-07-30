package com.humanid.auth;

import android.support.annotation.Nullable;

public interface AuthChangeListener {

    void onChange(@Nullable HumanIDUser humanIDUser);
}
