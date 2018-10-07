package com.mlabar.android_background_location_service.common.location

import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.support.v4.app.NotificationCompat
import com.mlabar.android_background_location_service.R
import java.text.DateFormat
import java.util.*

object LocationResultHelper {

    private const val LOCATION_CHANNEL = "LOCATION_CHANNEL"

    fun showNotification(mContext: Context,
                         mLocations: List<Location>) {

        val mNotificationManager: NotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val title = "Date : " + DateFormat.getDateTimeInstance().format(Date())

        val content = StringBuilder().apply {
            if (mLocations.isEmpty()) {
                append("unknown_location")
            } else {
                for (location in mLocations) {
                    append("(${location.altitude} , ${location.longitude})\n")
                }
            }
        }

        val mBuilder = NotificationCompat.Builder(mContext, LOCATION_CHANNEL)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        mNotificationManager.notify(100, mBuilder.build())

    }

}