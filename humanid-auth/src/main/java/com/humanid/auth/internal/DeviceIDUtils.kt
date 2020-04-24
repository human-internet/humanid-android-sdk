package com.humanid.auth.internal

import android.content.Context
import android.provider.Settings

object DeviceIDUtils {
    @JvmStatic
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}