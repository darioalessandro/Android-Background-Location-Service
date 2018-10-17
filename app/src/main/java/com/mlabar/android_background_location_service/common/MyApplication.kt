package com.mlabar.android_background_location_service.common

import android.app.Application
import com.mlabar.android_background_location_service.common.Helper.GoogleApiHelper

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    lateinit var googleApiHelper: GoogleApiHelper

    override fun onCreate() {
        super.onCreate()

        instance = this
        googleApiHelper = GoogleApiHelper(this)
    }

}