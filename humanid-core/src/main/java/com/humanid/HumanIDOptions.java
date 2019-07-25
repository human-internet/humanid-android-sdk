package com.humanid;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class HumanIDOptions {

    private static final String APPLICATION_ID_METADATA_NAME = "humanid_application_id";
    private static final String APPLICATION_SECRET_METADATA_NAME = "humanid_application_secret";

    private final String applicationID;
    private final String applicationSecret;

    public static final class Builder {

        private String applicationID;
        private String applicationSecret;

        public Builder() {}

        public Builder(HumanIDOptions options) {
            applicationID = options.applicationID;
            applicationSecret = options.applicationSecret;
        }

        public Builder setApplicationID(@NonNull String applicationID) {
            this.applicationID = applicationID;
            return this;
        }

        public Builder setApplicationSecret(@NonNull String applicationSecret) {
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

        ApplicationInfo ai;

        try {
            ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }

        if (ai == null || ai.metaData == null) {
            return null;
        }

        String appId = ai.metaData.getString(APPLICATION_ID_METADATA_NAME);
        String appSecret = ai.metaData.getString(APPLICATION_SECRET_METADATA_NAME);

        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appSecret)) {
            return null;
        }

        return new HumanIDOptions(appId, appSecret);
    }

    public String getApplicationID() {
        return applicationID;
    }

    public String getApplicationSecret() {
        return applicationSecret;
    }
}
