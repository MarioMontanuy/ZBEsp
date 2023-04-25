/*package com.example.zbesp.screens.map

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.*
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.zbesp.MainActivity
import com.example.zbesp.dataStore
import com.example.zbesp.screens.map.GeofenceBroadcastReceiver
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.util.*

class MyService : Service() {
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private var mCurrentLocation: Location? = Location("here")
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var notificationManager: NotificationManager
    private var gpsStarted: Boolean = false
    private var foregroundServiceStarted: Boolean = false
    var serviceStopped: Boolean = false
    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createLocationCallback()
        createLocationRequest()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return LocalBinder()
    }

    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }


    @RequiresApi(Build.VERSION_CODES.S)
    fun startForeground() {
        if (!foregroundServiceStarted && gpsStarted) {
            startForeground(NOTIFICATION_ID, mCurrentLocation?.let { generateNotification() })
            foregroundServiceStarted = true
        }
    }

    fun stopForeground() {
        stopForeground(STOP_FOREGROUND_DETACH)
        foregroundServiceStarted = false
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest.Builder(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
            .setMaxUpdateDelayMillis(UPDATE_INTERVAL_IN_MILLISECONDS)
            .build()
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            @RequiresApi(Build.VERSION_CODES.S)
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation
                if (foregroundServiceStarted) {
                    notificationManager.notify(NOTIFICATION_ID,
                        mCurrentLocation?.let { generateNotification(it) })
                }
                val broadcastIntent = Intent()
                val bundle = Bundle()
                bundle.putParcelable("Location", mCurrentLocation)
                broadcastIntent.putExtra("Location", bundle)
                broadcastIntent.action = "com.google.android.gms.location.sample.locationupdates"
                broadcastIntent.putExtra("info", "GPS")
                sendBroadcast(broadcastIntent)
            }
        }
    }

    private fun startGPS() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback, Looper.myLooper()!!
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun generateNotification(): Notification {
        notificationHelper.sendHighPriorityNotification(
            GeofenceBroadcastReceiver.GEOFENCE_TRANSITION_EXIT, "",
            MainActivity::class.java
        )
    }

    companion object {
        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 1000

        private const val NOTIFICATION_CHANNEL_ID = "notification_channel"
        private const val NOTIFICATION_ID = 1

        private const val EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION = "cancel"
        private const val EXTRA_RESTORE_UI = "restore"

        /**
         * The fastest rate for active location updates. Exact. Updates will never be more frequent
         * than this value.
         */
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }
}*/

