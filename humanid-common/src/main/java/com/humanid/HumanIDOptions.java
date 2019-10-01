package com.humanid;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.humanid.util.Preconditions;

public final class HumanIDOptions {

    private static final String APPLICATION_ID_METADATA_NAME = "com.humanid.sdk.applicationId";
    private static final String APPLICATION_SECRET_METADATA_NAME = "com.humanid.sdk.applicationSecret";

    private final String applicationID;
    private final String applicationSecret;

    private HumanIDOptions(String applicationID, String applicationSecret) {
        Preconditions.checkArgument(!TextUtils.isEmpty(applicationID), "applicationID");
        Preconditions.checkArgument(!TextUtils.isEmpty(applicationSecret), "applicationSecret");

        this.applicationID = applicationID;
        this.applicationSecret = applicationSecret;
    }

    @Nullable
    static HumanIDOptions fromResource(@NonNull Context context) {
        ApplicationInfo applicationInfo;

        try {
            applicationInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }

        if (applicationInfo == null || applicationInfo.metaData == null) {
            return null;
        }

        String applicationID = applicationInfo.metaData.getString(APPLICATION_ID_METADATA_NAME);
        String applicationSecret = applicationInfo.metaData.getString(APPLICATION_SECRET_METADATA_NAME);

        if (TextUtils.isEmpty(applicationID) || TextUtils.isEmpty(applicationSecret)) {
            return null;
        }

        return new HumanIDOptions(applicationID, applicationSecret);
    }

    @NonNull
    public String getApplicationID() {
        return applicationID;
    }

    @NonNull
    public String getApplicationSecret() {
        return applicationSecret;
    }
}
