package com.example.zbesp.screens.map

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zbesp.MainActivity
import com.example.zbesp.R
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.screens.map.MyLocationOverlay.myLocationOverlay
import com.example.zbesp.screens.vehicles.VehiclesFloatingActionButton
import com.example.zbesp.ui.theme.SapphireBlue
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.bonuspack.kml.LineStyle
import org.osmdroid.bonuspack.kml.Style
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.InputStream

lateinit var map: MapView
@SuppressLint("StaticFieldLeak")
private lateinit var currentInputStream: InputStream
/*
class KmlLoader: AppCompatActivity() {
    fun load() {
        val kmlDocument = KmlDocument()
//        kmlDocument.parseKMLStream(javaClass.getResourceAsStream("android.resource://com.example.zbesp/2131951616"), null)
        kmlDocument.parseKMLStream(resources.openRawResource(R.raw.lleida), null)
        val s = Style()
        s.mLineStyle = LineStyle(Color.Blue.value.toInt(), 8.0f)
        kmlDocument.addStyle(s)
        val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument)
        map.overlays.add(kmlOverlay)
        map.invalidate()
        // KmlLoader(inputStream = currentInputStream).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}*/
@Composable
fun MapScreen(inputStream: InputStream) {
    currentInputStream = inputStream
    AndroidView(factory = {
        View.inflate(it, R.layout.map_activity, null)
    },
        modifier = Modifier.fillMaxSize(),
        update = {
            map = it.findViewById(R.id.mapView)
            map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
            initializeMap()
            loadKml()
            //val kmlLoader = KmlLoader()
            //kmlLoader.load()
        }
    )
    CurrentLocationFloatingActionButton()
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

@Composable
fun CurrentLocationFloatingActionButton() {
    Box(modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { myLocationOverlay.enableFollowLocation() },
            backgroundColor = SapphireBlue,
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.MyLocation, contentDescription = "Crear nota")
        }
    }
}

fun loadKml() {
    val kmlDocument = KmlDocument()
//        kmlDocument.parseKMLStream(javaClass.getResourceAsStream("android.resource://com.example.zbesp/2131951616"), null)
    kmlDocument.parseKMLStream(currentInputStream, null)
    val s = Style()
    s.mLineStyle = LineStyle(Color.Blue.value.toInt(), 8.0f)
    kmlDocument.addStyle(s)
    val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument)
    map.overlays.add(kmlOverlay)
    map.invalidate()
    // KmlLoader(inputStream = currentInputStream).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
}

object MyLocationOverlay {
    lateinit var myLocationOverlay: MyLocationNewOverlay
}

