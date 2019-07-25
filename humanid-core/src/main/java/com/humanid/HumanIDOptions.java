package com.humanid;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.humanid.internal.Validate;

public final class HumanIDOptions {

    private static final String APPLICATION_ID_METADATA = "humanid_application_id";
    private static final String APPLICATION_SECRET_METADATA = "humanid_application_secret";

    private final String applicationID;
    private final String applicationSecret;

    public static final class Builder {

        private String applicationID;
        private String applicationSecret;

        public Builder() {}

        public Builder(@NonNull HumanIDOptions options) {
            Validate.checkNotNull(options, "HumanIDOptions cannot be null.");

            applicationID = options.applicationID;
            applicationSecret = options.applicationSecret;
        }

        public Builder setApplicationID(@NonNull String applicationID) {
            Validate.checkArgument(!TextUtils.isEmpty(applicationID), "applicationID");

            this.applicationID = applicationID;
            return this;
        }

        public Builder setApplicationSecret(@NonNull String applicationSecret) {
            Validate.checkArgument(!TextUtils.isEmpty(applicationSecret), "applicationSecret");

            this.applicationSecret = applicationSecret;
            return this;
        }

        @NonNull
        public HumanIDOptions build() {
            return new HumanIDOptions(applicationID, applicationSecret);
        }
    }

    private HumanIDOptions(String applicationID, String applicationSecret) {
        this.applicationID = applicationID;
        this.applicationSecret = applicationSecret;
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

        String applicationID = applicationInfo.metaData.getString(APPLICATION_ID_METADATA);
        String applicationSecret = applicationInfo.metaData.getString(APPLICATION_SECRET_METADATA);

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
