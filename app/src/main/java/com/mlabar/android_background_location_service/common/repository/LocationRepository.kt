package com.mlabar.android_background_location_service.common.repository

import android.arch.lifecycle.LiveData
import android.location.Location

object LocationRepository {

    private val TAG = LocationRepository::class.java.simpleName

    var location : LiveData<Location>? = null

}