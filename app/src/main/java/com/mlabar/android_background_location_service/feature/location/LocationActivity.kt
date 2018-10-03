package com.mlabar.android_background_location_service.feature.location

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mlabar.android_background_location_service.R
import com.mlabar.android_background_location_service.common.extension.checkPermissionAccessFineLocation
import com.mlabar.android_background_location_service.common.extension.requestPermissionAccessFineLocation
import com.mlabar.android_background_location_service.common.receiver.StartServiceReceiver
import com.mlabar.android_background_location_service.common.util.InjectorUtils
import com.mlabar.android_background_location_service.databinding.ActivityLocationBinding
import kotlinx.android.synthetic.main.activity_location.*


class LocationActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = LocationActivity::class.java.simpleName

    private val REQUEST_CODE_ACCESS_FINE_LOCATION = 1

    private val mStartCompletedServiceReceiver = StartServiceReceiver()

    private lateinit var mLocationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = InjectorUtils.locationViewModelFactory()
        mLocationViewModel = ViewModelProviders.of(this, factory).get(LocationViewModel::class.java)

        DataBindingUtil.setContentView<ActivityLocationBinding>(this, R.layout.activity_location).apply {
            viewModel = mLocationViewModel
            setLifecycleOwner(this@LocationActivity)
        }

        button_start_service.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter(StartServiceReceiver.ACTION_START_SERVICE)
        registerReceiver(mStartCompletedServiceReceiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(mStartCompletedServiceReceiver)
    }

    /**
    * Services
    */

    private fun startBackgroundLocation() {
        if (!checkPermissionAccessFineLocation()) {
            requestPermissionAccessFineLocation(REQUEST_CODE_ACCESS_FINE_LOCATION)
        } else {
            val intent = Intent(StartServiceReceiver.ACTION_START_SERVICE)
            sendBroadcast(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startBackgroundLocation()
                }
                return
            }
        }
    }

    /**
    * Listeners
    */

    override fun onClick(v: View?) {
        startBackgroundLocation()
    }

}
