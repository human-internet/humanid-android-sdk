package com.humanid.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.humanid.HumanIDSDK;
import com.humanid.util.Preconditions;

/**
 *Class presumably used by a humanID client. Purpose unclear. Class overrides common methods to most likely protect user data.
 */
public class HumanIDInitProvider extends ContentProvider {
    /**
     *“HumanIDSDK”
     */
    private final static String TAG = HumanIDSDK.class.getSimpleName();
    /**
     *"com.humanid.provider.HumanIDInitProvider"
     */
    @VisibleForTesting
    static final String EMPTY_APPLICATION_ID_PROVIDER_AUTHORITY =
            "com.humanid.provider.HumanIDInitProvider";

    /**
     *Calls contentProviderAuthority(info) to verify that the property info.authority is not empty. Attaches info on success.
     * @param context: Context object from the Android system
     * @param info: ProviderInfo object used to verify application ID
     */
    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        // super.attachInfo calls onCreate. Fail as early as possible.
        checkContentProviderAuthority(info);
        super.attachInfo(context, info);
    }

    /**
     *Returns true if HumanIDSDK was initialized successfully. False otherwise.
     * @return boolean: true if HumanIDSDK was initialized successfully. false otherwise
     */
    @Override
    public boolean onCreate() {
        if (getContext() == null || HumanIDSDK.initialize(getContext()) == null) {
            Log.i(TAG, "HumanIDSDK initialization unsuccessful");
        } else {
            Log.i(TAG, "HumanIDSDK initialization successful");
        }
        return false;
    }

    /**
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return null
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        return null;
    }

    /**
     *
     * @param uri
     * @return null
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     *
     * @param uri
     * @param values
     * @return null
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    /**
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return 0
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return null
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     *Checks if info.authority is empty. Throws error if info.authority is empty.
     * @param info ProviderInfo object containing information on current provider in system
     */
    private static void checkContentProviderAuthority(@NonNull ProviderInfo info) {
        boolean isEmptyApplicationID = EMPTY_APPLICATION_ID_PROVIDER_AUTHORITY
                .equals(info.authority);

        Preconditions.checkState(!isEmptyApplicationID, "Incorrect provider authority in manifest."
                + " Most likely due to a missing applicationId variable"
                + " in application's build.gradle.");
    }
}
