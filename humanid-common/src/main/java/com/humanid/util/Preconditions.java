package com.humanid.util;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Class with error checking functions
 */
public final class Preconditions {
    /**
     *Constructor. Throws error if instance is created.
     */
    private Preconditions() {
        throw new AssertionError("Uninstantiable");
    }

    /**
     *Checks whether parameter var0 is null. If var0 is null, a NullPointerException is thrown.
     * @param var0 object of type T
     * @return T: if var0 is not null, var0 is returned
     */
    @NonNull
    public static <T> T checkNotNull(@Nullable T var0) {
        if (var0 == null) {
            throw new NullPointerException("null reference");
        } else {
            return var0;
        }
    }

    /**
     *Checks whether String var0 is empty. If var0 is empty or null, an IllegalArgumentException is thrown.
     * @param var0 String
     * @return String: if var0 is not empty/null, var0 is returned
     */
    public static String checkNotEmpty(String var0) {
        if (TextUtils.isEmpty(var0)) {
            throw new IllegalArgumentException("Given String is empty or null");
        } else {
            return var0;
        }
    }

    /**
     *Checks whether String var0 is empty. If var0 is empty or null, an IllegalArgumentException is thrown with String var1.
     * @param var0 String
     * @param var1 Object representing error message
     * @return String: if var0 is not empty/null, var0 is returned
     */
    public static String checkNotEmpty(String var0, Object var1) {
        if (TextUtils.isEmpty(var0)) {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    /**
     *Checks whether parameter var0 is null. If var0 is null, a NullPointerException is thrown with message var1.
     * @param var0 T
     * @param var1 Object representing error message
     * @return T: if var0 is not null, var0 is returned
     */
    @NonNull
    public static <T> T checkNotNull(T var0, Object var1) {
        if (var0 == null) {
            throw new NullPointerException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    /**
     *Checks whether int var0 is equal to 0. If var0 is equal to 0, an IllegalArgumentException is thrown with String var1.
     * @param var0 int
     * @param var1 Object representing error
     * @return int: if var0 is nonzero, var0 is returned
     */
    public static int checkNotZero(int var0, Object var1) {
        if (var0 == 0) {
            throw new IllegalArgumentException(String.valueOf(var1));
        } else {
            return var0;
        }
    }

    /**
     *Checks whether int var0 is equal to 0. If var0 is equal to 0, an IllegalArgumentException is thrown.
     * @param var0 int
     * @return int: if var0 is nonzero, var0 is returned
     */
    public static int checkNotZero(int var0) {
        if (var0 == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        } else {
            return var0;
        }
    }

    /**
     *Checks whether long var0 is equal to 0. If var0 is equal to 0, an IllegalArgumentException is thrown with String var2.
     * @param var0 long
     * @param var2 Object representing error
     * @return long: if var0 is nonzero, var0 is returned
     */
    public static long checkNotZero(long var0, Object var2) {
        if (var0 == 0L) {
            throw new IllegalArgumentException(String.valueOf(var2));
        } else {
            return var0;
        }
    }

    /**
     *Checks whether long var0 is equal to 0. If var0 is equal to 0, an IllegalArgumentException is thrown.
     * @param var0 long
     * @return long: if var0 is nonzero, var0 is returned
     */
    public static long checkNotZero(long var0) {
        if (var0 == 0L) {
            throw new IllegalArgumentException("Given Long is zero");
        } else {
            return var0;
        }
    }

    /**
     *Checks whether boolean var0 is false. If var0 is false, an IllegalStateException is thrown.
     * @param var0 boolean
     */
    public static void checkState(boolean var0) {
        if (!var0) {
            throw new IllegalStateException();
        }
    }

    /**
     *Checks whether boolean var0 is false. If var0 is false, an IllegalStateException is thrown with String var1.
     * @param var0 boolean
     * @param var1 Object representing error
     */
    public static void checkState(boolean var0, Object var1) {
        if (!var0) {
            throw new IllegalStateException(String.valueOf(var1));
        }
    }

    /**
     *Checks whether boolean var0 is false. If var0 is false, an IllegalStateException is thrown with String var1 and Throwable var2.
     * @param var0 boolean
     * @param var1 String representing error
     * @param var2 Object representing cause
     */
    public static void checkState(boolean var0, String var1, Object... var2) {
        if (!var0) {
            throw new IllegalStateException(String.format(var1, var2));
        }
    }

    /**
     *Checks whether boolean var0 is false. If var0 is false, an IllegalArgumentException is thrown with String var1.
     * @param var0 boolean
     * @param var1 Object representing error
     */
    public static void checkArgument(boolean var0, Object var1) {
        if (!var0) {
            throw new IllegalArgumentException(String.valueOf(var1));
        }
    }

    /**
     *Checks whether boolean var0 is false. If var0 is false, an IllegalArgumentException is thrown with String var1 and Throwable var2.
     * @param var0 boolean
     * @param var1 String representing error
     * @param var2 Object representing cause
     */
    public static void checkArgument(boolean var0, String var1, Object... var2) {
        if (!var0) {
            throw new IllegalArgumentException(String.format(var1, var2));
        }
    }

    /**
     *Checks whether boolean var0 is false. If var0 is false, an IllegalArgumentException is thrown.
     * @param var0 boolean
     */
    public static void checkArgument(boolean var0) {
        if (!var0) {
            throw new IllegalArgumentException();
        }
    }
}
