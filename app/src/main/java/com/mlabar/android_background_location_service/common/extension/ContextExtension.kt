package com.mlabar.android_background_location_service.common.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

fun Context.checkPermission(permission : String) : Boolean {
    return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.checkPermissionLocation() : Boolean {
    return checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}