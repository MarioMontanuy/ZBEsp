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
    val radius: Float,
    var description: String = "",
    var forbiddenStickers: List<EnvironmentalStickerEnum> = listOf(),
    @DrawableRes var imageId: Int = R.drawable.lleida_zbe,
    var name: String = ""
){
    fun setNameAndImage(){
        if (this.id == R.raw.lleida) {
            this.name = "Lleida LEZ"
            this.description = "Lleida, Catalonia, Spain"
            this.forbiddenStickers = listOf(EnvironmentalStickerEnum.None)
            this.imageId = R.drawable.lleida_zbe
        }
        if (this.id == R.raw.zaragoza) {
            this.name = "Zaragoza LEZ"
            this.description = "Zaragoza, Aragon, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalStickerEnum.None,
                EnvironmentalStickerEnum.B)
            this.imageId = R.drawable.zaragoza_zbe
        }
        if (this.id == R.raw.madrid) {
            this.name = "Madrid LEZ"
            this.description = "Madrid, Madrid, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalStickerEnum.None,
                EnvironmentalStickerEnum.C,
                EnvironmentalStickerEnum.B)
            this.imageId = R.drawable.madrid_zbe
        }
        if (this.id == R.raw.barcelona) {
            this.name = "Barcelona LEZ"
            this.description = "Barcelona, Catalonia, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalStickerEnum.None,
                EnvironmentalStickerEnum.C,
                EnvironmentalStickerEnum.B)
            this.imageId = R.drawable.barcelona_zbe
        }
    }
    fun isStickerForbidden(sticker: EnvironmentalStickerEnum?): Boolean{
        this.forbiddenStickers.forEach { it ->
            if (it == sticker) {
                return true
            }
        }
        return false
    }
}


var geofences: List<GeofenceItem> =
    listOf(
    )

var kmlZones: List<Int> = listOf(R.raw.zaragoza, R.raw.madrid, R.raw.lleida, R.raw.barcelona)

fun getGeofence(geofenceId: String): GeofenceItem{
    return geofences.first {it.id == geofenceId.toInt()}
}


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