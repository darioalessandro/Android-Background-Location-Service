package com.mlabar.android_background_location_service.common.repository

import android.arch.lifecycle.MutableLiveData
import android.location.Location

object LocationRepository {

    var location: MutableLiveData<Location> = MutableLiveData()

    var isServiceStarting: MutableLiveData<Boolean> = MutableLiveData()

}