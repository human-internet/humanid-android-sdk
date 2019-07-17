package com.humanid.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.humanid.HumanIDSDK;

public class HumanIDInitProvider extends ContentProvider {

    private final static String TAG = HumanIDSDK.class.getSimpleName();

    @VisibleForTesting
    static final String EMPTY_APPLICATION_ID_PROVIDER_AUTHORITY = "com.humanid.humanidinitprovider";

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        // super.attachInfo calls onCreate. Fail as early as possible.
        checkContentProviderAuthority(info);
        super.attachInfo(context, info);
    }

    @Override
    public boolean onCreate() {
        if (getContext() == null || HumanIDSDK.initialize(getContext()) == null) {
            Log.i(TAG, "HumanIDSDK initialization unsuccessful");
        } else {
            Log.i(TAG, "HumanIDSDK initialization successful");
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private static void checkContentProviderAuthority(@NonNull ProviderInfo info) {
        if (EMPTY_APPLICATION_ID_PROVIDER_AUTHORITY.equals(info.authority)) {
            throw new IllegalStateException(
                    "Incorrect provider authority in manifest. Most likely due to a missing "
                            + "applicationId variable in application's build.gradle.");
        }
    }
}
