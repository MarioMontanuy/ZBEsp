package com.example.zbesp.screens.map

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import com.example.zbesp.MainActivity
import com.example.zbesp.dataStore
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


class GeofenceBroadcastReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence triggered...", Toast.LENGTH_SHORT).show();
        val notificationHelper = NotificationHelper(context)
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent!!.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...")
            return
        }
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
//            Geofence.GEOFENCE_TRANSITION_ENTER -> {
//                Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_SHORT).show()
//                runBlocking(Dispatchers.IO) {
//                    if (value.first()!!)
//                    notificationHelper.sendHighPriorityNotification(
//                        "GEOFENCE_TRANSITION_ENTER", "",
//                        MainActivity::class.java
//                    )
//                    Log.i("notificationHelper", "value:${value.first()}")
//                }
//            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show()
                runBlocking(Dispatchers.IO) {
                    if (value.first()!!)
                        notificationHelper.sendHighPriorityNotification(
                            "GEOFENCE_TRANSITION_DWELL", "",
                            MainActivity::class.java
                        )
                    Log.i("notificationHelper", "value:${value.first()}")
                }
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show()
                runBlocking(Dispatchers.IO) {
                    if (value.first()!!)
                        notificationHelper.sendHighPriorityNotification(
                            "GEOFENCE_TRANSITION_EXIT", "",
                            MainActivity::class.java
                        )
                    Log.i("notificationHelper", "value:${value.first()}")
                }
            }
        }
    }

    companion object {
        private const val TAG = "GeofenceBroadcastReceiv"
    }
}