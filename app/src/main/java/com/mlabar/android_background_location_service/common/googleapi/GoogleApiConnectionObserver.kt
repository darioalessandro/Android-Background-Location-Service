package com.mlabar.android_background_location_service.common.googleapi

import android.os.Bundle
import com.google.android.gms.common.ConnectionResult

interface GoogleApiConnectionObserver {

    fun onConnected(bundle: Bundle?)

    fun onConnectionSuspended(i: Int)

    fun onConnectionFailed(connectionResult: ConnectionResult)

}