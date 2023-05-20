package com.example.zbesp.screens.map

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.example.zbesp.service.MapNotificationService
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = NotificationHelper(context)
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent!!.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...")
            return
        }
        val manager = getSystemService(context, MapNotificationService::class.java)
        if (manager == null) {
            Log.i("manger", "null")
        }
        val instance = MapNotificationService.getServiceInstance()
        instance.sendNotification(context, notificationHelper, geofencingEvent)
    }

    companion object {
        private const val TAG = "GeofenceBroadcastReceiv"
    }
}