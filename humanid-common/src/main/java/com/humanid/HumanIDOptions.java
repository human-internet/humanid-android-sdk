package com.humanid;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.humanid.util.Preconditions;

/**
 * Class holding metadata for HumanID.
 */
public final class HumanIDOptions {
    /**
     * "com.humanid.sdk.applicationId"
     */
    private static final String APPLICATION_ID_METADATA_NAME = "com.humanid.sdk.applicationId";
    /**
     * "com.humanid.sdk.applicationSecret"
     */
    private static final String APPLICATION_SECRET_METADATA_NAME = "com.humanid.sdk.applicationSecret";
    /**
     * "com.humanid.sdk.applicationIcon"
     */
    private static final String APPLICATION_ICON_METADATA_NAME = "com.humanid.sdk.applicationIcon";
    /**
     * "com.humanid.sdk.applicationName"
     */
    private static final String APPLICATION_NAME_METADATA_NAME = "com.humanid.sdk.applicationName";
    /**
     * Metadata
     */
    private final String applicationID;
    /**
     * Metadata
     */
    private final String applicationSecret;
    /**
     * Metadata
     */
    private final int applicationIcon;
    /**
     * Metadata
     */
    private final String applicationName;

    /**
     * Constructor.
     * @param applicationID set to this.applicationID
     * @param applicationSecret set to this.applicationSecret
     * @param applicationIcon set to this.applicationIcon
     * @param applicationName set to this.applicationName
     */
    public HumanIDOptions(String applicationID, String applicationSecret, int applicationIcon,
                          String applicationName) {
        Preconditions.checkArgument(!TextUtils.isEmpty(applicationID), "applicationID");
        Preconditions.checkArgument(!TextUtils.isEmpty(applicationSecret), "applicationSecret");


        this.applicationID = applicationID;
        this.applicationSecret = applicationSecret;
        this.applicationIcon = applicationIcon;
        this.applicationName = applicationName;
    }

    /**
     * Creates and returns a new HumanIDOptions object. The new object’s parameters are derived from context.
     * @param context the Context object from which to derive metadata
     * @return HumanIDOptions: object created using metadata from context
     */
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
        int applicationIcon = applicationInfo.metaData.getInt(APPLICATION_ICON_METADATA_NAME);
        String applicationName = applicationInfo.metaData.getString(APPLICATION_NAME_METADATA_NAME);

        if (TextUtils.isEmpty(applicationID) || TextUtils.isEmpty(applicationSecret)) {
            return null;
        }

        return new HumanIDOptions(applicationID, applicationSecret, applicationIcon, applicationName);
    }

    /**
     * Getter for applicationID.
     * @return String: this.applicationID
     */
    @NonNull
    public String getApplicationID() {
        return applicationID;
    }

    /**
     * Getter for applicationSecret.
     * @return String: this.applicationSecret
     */
    @NonNull
    public String getApplicationSecret() {
        return applicationSecret;
    }

    /**
     * Getter for applicationIcon.
     * @return int: this.applicationIcon
     */
    @NonNull
    public int getApplicationIcon() {
        return applicationIcon;
    }

    /**
     * Getter for applicationName.
     * @return String: this.applicationName
     */
    @NonNull
    public String getApplicationName() {
        return applicationName;
    }
}
