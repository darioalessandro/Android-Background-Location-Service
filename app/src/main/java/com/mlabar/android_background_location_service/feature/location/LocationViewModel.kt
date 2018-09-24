package com.mlabar.android_background_location_service.feature.location

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Location

class LocationViewModel() : ViewModel() {

    private val mLocation = MutableLiveData<Location>()

}