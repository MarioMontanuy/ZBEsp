package com.example.zbesp.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.example.zbesp.R
import org.osmdroid.util.GeoPoint

@Immutable
data class GeofenceItem(
    val id: Int,
    val center: GeoPoint,
    val radius: Float,
    var description: String = "",
    var forbiddenStickers: List<EnvironmentalStickerEnum> = listOf(),
    @DrawableRes var imageId: Int = R.drawable.lleida_zbe,
    var name: String = ""
) {
    fun setNameAndImage() {
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
                EnvironmentalStickerEnum.B
            )
            this.imageId = R.drawable.zaragoza_zbe
        }
        if (this.id == R.raw.madrid) {
            this.name = "Madrid LEZ"
            this.description = "Madrid, Madrid, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalStickerEnum.None,
                EnvironmentalStickerEnum.C,
                EnvironmentalStickerEnum.B
            )
            this.imageId = R.drawable.madrid_zbe
        }
        if (this.id == R.raw.barcelona) {
            this.name = "Barcelona LEZ"
            this.description = "Barcelona, Catalonia, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalStickerEnum.None,
                EnvironmentalStickerEnum.C,
                EnvironmentalStickerEnum.B
            )
            this.imageId = R.drawable.barcelona_zbe
        }
    }

    fun isStickerForbidden(sticker: EnvironmentalStickerEnum?): Boolean {
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

fun getGeofence(geofenceId: String): GeofenceItem {
    return geofences.first { it.id == geofenceId.toInt() }
}