package com.humanid.humanidui.presentation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;

public final class HumanIDOptions {
    private static final String APPLICATION_ICON_METADATA_NAME = "com.humanid.sdk.applicationIcon";
    private static final String APPLICATION_NAME_METADATA_NAME = "com.humanid.sdk.applicationName";

    private final int applicationIcon;
    private final String applicationName;

    public HumanIDOptions(int applicationIcon, String applicationName) {
        Preconditions.checkArgument(applicationIcon != -1, "applicationIcon");
        Preconditions.checkArgument(!TextUtils.isEmpty(applicationName), "applicationName");

        this.applicationIcon = applicationIcon;
        this.applicationName = applicationName;
    }

    @Nullable
    public static HumanIDOptions fromResource(@NonNull Context context) {
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

        int applicationIcon = applicationInfo.metaData.getInt(APPLICATION_ICON_METADATA_NAME);
        String applicationName = applicationInfo.metaData.getString(APPLICATION_NAME_METADATA_NAME);

        if (applicationIcon == -1 || TextUtils.isEmpty(applicationName)) {
            return null;
        }

        return new HumanIDOptions(applicationIcon, applicationName);
    }

    @NonNull
    public int getApplicationIcon() {
        return applicationIcon;
    }

    @NonNull
    public String getApplicationName() {
        return applicationName;
    }
}

