package com.example.zbesp.screens.map

import android.os.AsyncTask
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.zbesp.R
import com.example.zbesp.screens.map.MyLocationOverlay.myLocationOverlay
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

lateinit var map: MapView
@Composable
fun MapScreen() {
    AndroidView(factory = {
        View.inflate(it, R.layout.map_activity, null)
    },
        modifier = Modifier.fillMaxSize(),
        update = {
            map = it.findViewById(R.id.mapView)
            map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
            initializeMap()
        }
    )
}

private fun initializeMap() {
    map.setBuiltInZoomControls(true)
    map.setMultiTouchControls(true)
    myLocationOverlay = MyLocationNewOverlay(map)
    myLocationOverlay.enableMyLocation()
    myLocationOverlay.enableFollowLocation()
    map.overlays.add(myLocationOverlay)
    val mapController = map.controller
    mapController.setZoom(16.0)
    map.onResume()
}
object MyLocationOverlay {
    lateinit var myLocationOverlay: MyLocationNewOverlay
}

fun loadKml() {
    KmlLoader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
}



