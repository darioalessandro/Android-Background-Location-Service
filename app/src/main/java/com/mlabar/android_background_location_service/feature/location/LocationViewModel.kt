package com.mlabar.android_background_location_service.feature.location

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.location.Location
import android.widget.Toast
import com.mlabar.android_background_location_service.R
import com.mlabar.android_background_location_service.common.repository.LocationRepository


class LocationViewModel : ViewModel() {

    fun getLocation(): LiveData<Location> = LocationRepository.location

    fun isLocationServiceStarting(): LiveData<Boolean> = LocationRepository.isServiceStarting

}