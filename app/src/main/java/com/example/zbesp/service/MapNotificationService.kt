package com.example.zbesp.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.zbesp.MainActivity
import com.example.zbesp.dataStore
import com.example.zbesp.screens.map.NotificationHelper
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class MapNotificationService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    @RequiresApi(Build.VERSION_CODES.S)
    fun sendNotification(context: Context, notificationHelper: NotificationHelper, geofencingEvent: GeofencingEvent) {
        val geofenceList = geofencingEvent.triggeringGeofences
        for (geofence in geofenceList!!) {
            Log.d(TAG, "onReceive: " + geofence.requestId)
        }
        val transitionType = geofencingEvent.geofenceTransition
        val value: Flow<Boolean?> = context.dataStore.data
            .map { preferences ->
                preferences[booleanPreferencesKey("notification")]
            }
        when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Toast.makeText(context, GEOFENCE_TRANSITION_DWELL, Toast.LENGTH_SHORT).show()
                runBlocking(Dispatchers.IO) {
                    if (value.first() != null) {
                        if (value.first()!!)
                            notificationHelper.sendHighPriorityNotification(
                                GEOFENCE_TRANSITION_DWELL, "",
                                MainActivity::class.java
                            )
                    }
                }
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Toast.makeText(context, GEOFENCE_TRANSITION_EXIT, Toast.LENGTH_SHORT).show()
                runBlocking(Dispatchers.IO) {
                    if (value.first() != null) {
                        if (value.first()!!)
                            notificationHelper.sendHighPriorityNotification(
                                GEOFENCE_TRANSITION_EXIT, "",
                                MainActivity::class.java
                            )
                    }
                }
            }
        }
    }
    companion object {
        private lateinit var instance: MapNotificationService
        @JvmStatic
        fun getServiceInstance() = instance
        private const val TAG = "GeofenceBroadcastReceiv"
        private const val GEOFENCE_TRANSITION_EXIT = "You have left the LEZ"
        private const val GEOFENCE_TRANSITION_DWELL = "You are in the LEZ"
    }
}