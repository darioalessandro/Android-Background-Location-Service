package com.mlabar.android_background_location_service.feature.location

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import com.mlabar.android_background_location_service.common.repository.LocationRepository


class LocationViewModel() : ViewModel() {

    fun getLocation(): LiveData<Location>? {
        return LocationRepository.location
    }

}