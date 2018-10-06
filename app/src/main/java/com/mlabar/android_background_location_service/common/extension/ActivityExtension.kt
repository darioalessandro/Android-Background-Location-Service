package com.mlabar.android_background_location_service.common.extension

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

fun Activity.requestPermissionAccessFineLocation(requestCode: Int) {
    return requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, requestCode)
}

fun Activity.requestPermission(permission: String, requestCode: Int) {
    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }
}
