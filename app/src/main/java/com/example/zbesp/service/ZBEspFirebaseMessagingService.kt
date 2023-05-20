package com.example.zbesp.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.zbesp.MainActivity
import com.example.zbesp.dataStore
import com.example.zbesp.screens.map.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class ZBEspFirebaseMessagingService : FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notificationHelper = NotificationHelper(this)
        val value: Flow<Boolean?> = this.dataStore.data
            .map { preferences ->
                preferences[booleanPreferencesKey("notification")]
            }
        runBlocking(Dispatchers.IO) {
            if (value.first() != null) {
                if (value.first()!!) {
                    notificationHelper.sendHighPriorityNotification(
                        message.notification!!.title, message.notification!!.body,
                        MainActivity::class.java
                    )
                }
            }
        }
    }
}