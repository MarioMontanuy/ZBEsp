package com.example.zbesp.data

import android.provider.Settings.Global.getString
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.example.zbesp.R
import org.osmdroid.util.GeoPoint

@Immutable
data class GeofenceItem(
    val id: Int,
    val center: GeoPoint,
    val radius: Float,
    var description: String = "",
    var forbiddenStickers: List<EnvironmentalSticker> = listOf(),
    var url: String = "",
    @DrawableRes var imageId: Int = R.drawable.lleida_zbe,
    var name: String = ""
) {
    fun setNameAndImage() {
        if (this.id == R.raw.lleida) {
            this.name = "Lleida LEZ"
            this.description = "Lleida, Catalonia, Spain"
            this.forbiddenStickers = listOf(EnvironmentalSticker(EnvironmentalStickerEnum.None, R.drawable.pegatinanone))
            this.imageId = R.drawable.lleida_zbe
            this.url = "https://cdn01.segre.com/uploads/imagenes/bajacalidad/2022/03/30/_zbe_9f1868be.jpg?d3d6b208763767ca650f5301abad64fd"
        }
        if (this.id == R.raw.zaragoza) {
            this.name = "Zaragoza LEZ"
            this.description = "Zaragoza, Aragon, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalSticker(EnvironmentalStickerEnum.None, R.drawable.pegatinanone),
                EnvironmentalSticker(EnvironmentalStickerEnum.B, R.drawable.pegatinab)
            )
            this.imageId = R.drawable.zaragoza_zbe
            this.url = "https://cadenaser.com/resizer/yBbl0kHU7T6DCDMyuWwa7f9PYKo=/736x414/filters" +
                    ":format(jpg):quality(70)/cloudfront-eu-central-1.images.arcpublishing.com" +
                    "/prisaradio/L3XSZE73HRGR3EE6KTTIQNHLRE.jpg"
        }
        if (this.id == R.raw.madrid) {
            this.name = "Madrid LEZ"
            this.description = "Madrid, Madrid, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalSticker(EnvironmentalStickerEnum.None, R.drawable.pegatinanone),
                EnvironmentalSticker(EnvironmentalStickerEnum.C, R.drawable.pegatinac),
                EnvironmentalSticker(EnvironmentalStickerEnum.B, R.drawable.pegatinab)
            )
            this.imageId = R.drawable.madrid_zbe
            this.url = "https://pre.madrid360.es/wp-content/uploads/2021/12/Portada-Madrid-ZBE-2.png"
        }
        if (this.id == R.raw.barcelona) {
            this.name = "Barcelona LEZ"
            this.description = "Barcelona, Catalonia, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalSticker(EnvironmentalStickerEnum.None, R.drawable.pegatinanone),
                EnvironmentalSticker(EnvironmentalStickerEnum.C, R.drawable.pegatinac),
                EnvironmentalSticker(EnvironmentalStickerEnum.B, R.drawable.pegatinab)
            )
            this.imageId = R.drawable.barcelona_zbe
            this.url = "https://www.ocu.org/-/media/ocu/images/home/coches/coches/mapa%20zona" +
                    "%20bajas%20emisiones%20barcelona.jpg?rev=f8af675e-a912-4ee2-9b03-137ed935ac" +
                    "36&hash=904A7F9E2663EDCD8E54DC306CE0E793"
        }
    }

    fun isStickerForbidden(sticker: EnvironmentalSticker?): Boolean {
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