package com.humanid.auth.internal

import android.content.Context
import android.os.Build
import android.provider.Settings

object DeviceIDUtils {
    private fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    @JvmStatic
    fun getPseudoDeviceID(context: Context): String {
        val uniquePseudoID =
            "35" + Build.BOARD.length % 10 +
                    Build.BRAND.length % 10 +
                    Build.DEVICE.length % 10 +
                    Build.DISPLAY.length % 10 +
                    Build.HOST.length % 10 +
                    Build.ID.length % 10 +
                    Build.MANUFACTURER.length % 10 +
                    Build.MODEL.length % 10 +
                    Build.PRODUCT.length % 10 +
                    Build.TAGS.length % 10 +
                    Build.TYPE.length % 10 +
                    Build.USER.length % 10
        val serial = Build.getRadioVersion()
        return uniquePseudoID + serial + getDeviceId(context)
    }
}