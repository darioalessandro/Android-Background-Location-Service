package com.mlabar.android_background_location_service.common.util

import com.mlabar.android_background_location_service.feature.location.LocationViewModelFactory

object InjectorUtils {

    fun locationViewModelFactory() : LocationViewModelFactory {
        return LocationViewModelFactory()
    }

}