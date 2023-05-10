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
            this.url = "https://firebasestorage.googleapis.com/v0/b/zbesp-a6692.appspot.com/o/Lleida-ZBE.jpg?alt=media&token=de18e797-6787-4dd8-892f-1eac811eb5a8"
        }
        if (this.id == R.raw.zaragoza) {
            this.name = "Zaragoza LEZ"
            this.description = "Zaragoza, Aragon, Spain"
            this.forbiddenStickers = listOf(
                EnvironmentalSticker(EnvironmentalStickerEnum.None, R.drawable.pegatinanone),
                EnvironmentalSticker(EnvironmentalStickerEnum.B, R.drawable.pegatinab)
            )
            this.imageId = R.drawable.zaragoza_zbe
            this.url = "https://firebasestorage.googleapis.com/v0/b/zbesp-a6692.appspot.com/o/Zaragoza-ZBE.jpg?alt=media&token=2f511711-0f26-415a-867f-d84bf5c98f05"
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
            this.url = "https://firebasestorage.googleapis.com/v0/b/zbesp-a6692.appspot.com/o/Madrid-ZBE.png?alt=media&token=2e30367b-d26d-4de1-b649-4159ac9fa953"
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
            this.url = "https://firebasestorage.googleapis.com/v0/b/zbesp-a6692.appspot.com/o/Barcelona-ZBE.jpg?alt=media&token=4c4c903f-4744-4363-a65c-7f3242fee679"
        }
    }

    fun isStickerForbidden(sticker: String): Boolean {
        this.forbiddenStickers.forEach { it ->
            if (it.type!!.name == sticker) {
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