package com.humanid;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class HumanIDOptions {

    private static final String APP_ID_METADATA_NAME = "humanid_app_id";
    private static final String APP_SECRET_METADATA_NAME = "humanid_app_secret";

    private final String appId;
    private final String appSecret;

    public static final class Builder {

        private String appId;
        private String appSecret;

        public Builder() {}

        public Builder(HumanIDOptions options) {
            appId = options.appId;
            appSecret = options.appSecret;
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setAppSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        @NonNull
        public HumanIDOptions build() {
            return new HumanIDOptions(appId, appSecret);
        }
    }

    private HumanIDOptions(@NonNull String appId, @NonNull String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
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

        String appId = ai.metaData.getString(APP_ID_METADATA_NAME);
        String appSecret = ai.metaData.getString(APP_SECRET_METADATA_NAME);

        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appSecret)) {
            return null;
        }

        return new HumanIDOptions(appId, appSecret);
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
