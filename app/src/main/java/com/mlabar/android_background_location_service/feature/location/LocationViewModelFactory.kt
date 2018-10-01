package com.mlabar.android_background_location_service.feature.location

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class LocationViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationViewModel() as T
    }

}