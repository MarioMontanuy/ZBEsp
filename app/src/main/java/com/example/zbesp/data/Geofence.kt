package com.example.zbesp.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.example.zbesp.R
import com.google.android.gms.maps.model.LatLng
import org.osmdroid.util.GeoPoint
import java.util.*


@Immutable
data class GeofenceItem(
    val id: Int,
    val center: GeoPoint,
    val radius: Float
)

var geofences: List<GeofenceItem> =
    listOf(
    )



/**
 * Constants used in this sample.
 */
internal object GeofenceConstants {
    private const val PACKAGE_NAME = "com.google.android.gms.location.Geofence"
    const val GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY"

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    private const val GEOFENCE_EXPIRATION_IN_HOURS: Long = 12

    /**
     * For this sample, geofences expire after twelve hours.
     */
    const val GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000
    const val GEOFENCE_RADIUS_IN_METERS = 1609f // 1 mile, 1.6 km

}