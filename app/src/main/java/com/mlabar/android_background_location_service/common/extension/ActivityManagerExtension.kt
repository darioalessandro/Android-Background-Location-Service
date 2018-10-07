package com.mlabar.android_background_location_service.common.extension

import android.app.ActivityManager
import android.support.annotation.Nullable

fun ActivityManager.isServiceRunning(@Nullable serviceClass: Class<*>?): Boolean {
    for (service in this.getRunningServices(Integer.MAX_VALUE)) {
        serviceClass?.apply {
            if (name == service.service.className) {
                return true
            }
        }
    }
    return false
}
