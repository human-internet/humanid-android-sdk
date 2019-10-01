package com.humanid.util;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Preconditions {

    private Preconditions() {
        throw new AssertionError("Uninstantiable");
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T var0) {
        if (var0 == null) {
            throw new NullPointerException("null reference");
        } else {
            return var0;
        }
    }

    public static String checkNotEmpty(String var0) {
        if (TextUtils.isEmpty(var0)) {
            throw new IllegalArgumentException("Given String is empty or null");
        } else {
            return var0;
        }
    }

    public static String checkNotEmpty(String var0, Object var1) {
        if (TextUtils.isEmpty(var0)) {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    @NonNull
    public static <T> T checkNotNull(T var0, Object var1) {
        if (var0 == null) {
            throw new NullPointerException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    public static int checkNotZero(int var0, Object var1) {
        if (var0 == 0) {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    public static int checkNotZero(int var0) {
        if (var0 == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        } else {
            return var0;
        }
    }

    public static long checkNotZero(long var0, Object var2) {
        if (var0 == 0L) {
            throw new IllegalArgumentException(String.valueOf(var2));
        } else {
            return var0;
        }
    }

    public static long checkNotZero(long var0) {
        if (var0 == 0L) {
            throw new IllegalArgumentException("Given Long is zero");
        } else {
            return var0;
        }
    }

    public static void checkState(boolean var0) {
        if (!var0) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean var0, Object var1) {
        if (!var0) {
            throw new IllegalStateException(String.valueOf(var1));
        }
    }

    public static void checkState(boolean var0, String var1, Object... var2) {
        if (!var0) {
            throw new IllegalStateException(String.format(var1, var2));
        }
    }

    public static void checkArgument(boolean var0, Object var1) {
        if (!var0) {
            throw new IllegalArgumentException(String.valueOf(var1));
        }
    }

    public static void checkArgument(boolean var0, String var1, Object... var2) {
        if (!var0) {
            throw new IllegalArgumentException(String.format(var1, var2));
        }
    }

    public static void checkArgument(boolean var0) {
        if (!var0) {
            throw new IllegalArgumentException();
        }
    }
}
