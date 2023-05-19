package com.example.zbesp.screens.map

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.floatPreferencesKey
import com.example.zbesp.R
import com.example.zbesp.domain.GeofenceItem
import com.example.zbesp.domain.geofences
import com.example.zbesp.domain.kmlZones
import com.example.zbesp.dataStore
import com.example.zbesp.screens.createZoneListenerOnDatabase
import com.example.zbesp.screens.map.MyLocationOverlay.myLocationOverlay
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.SapphireBlueTransparent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.bonuspack.kml.LineStyle
import org.osmdroid.bonuspack.kml.Style
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


lateinit var map: MapView
private lateinit var geofencingClient: GeofencingClient
private lateinit var geofenceHelper: GeofenceHelper
private lateinit var mapController: MapController

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MapScreen(context: Context) {
    AndroidView(factory = {
        View.inflate(it, R.layout.map_activity, null)
    },
        modifier = Modifier.fillMaxSize(),
        update = {
            map = it.findViewById(R.id.mapView)
            map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
            initializeMap(context)
            loadKml(context)
        }
    )
    createGeofence(context)
    CreateSearchBar(context)
    CurrentLocationFloatingActionButton()
    createZoneListenerOnDatabase()
}

private fun initializeMap(context: Context) {
    map.setBuiltInZoomControls(true)
    map.setMultiTouchControls(true)
    myLocationOverlay = MyLocationNewOverlay(map)
    myLocationOverlay.enableMyLocation()
    myLocationOverlay.enableFollowLocation()
    map.overlays.add(myLocationOverlay)
    mapController = map.controller as MapController
    val value: Flow<Float> = context.dataStore.data
        .map { preferences ->
            preferences[floatPreferencesKey("mapzoom")] ?: 15f
        }
    runBlocking(Dispatchers.IO) {
        mapController.setZoom(value.first().toInt())
    }
    map.onResume()
}

@RequiresApi(Build.VERSION_CODES.S)
private fun createGeofence(context: Context) {
    geofencingClient = LocationServices.getGeofencingClient(context)
    geofenceHelper = GeofenceHelper(context)
    geofences.forEach { it ->
        addGeofence(it, context)
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun addGeofence(geofenceItem: GeofenceItem, context: Context) {
    val geofence: Geofence = geofenceHelper.getGeofence(
        geofenceItem.id.toString(),
        LatLng(geofenceItem.center.latitude, geofenceItem.center.longitude),
        geofenceItem.radius,
        Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT
    )
    val geofencingRequest: GeofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
    val pendingIntent: PendingIntent = geofenceHelper.getPendingIntent()!!
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    geofencingClient.addGeofences(geofencingRequest, pendingIntent)
        .addOnSuccessListener(OnSuccessListener<Void?> {
            Log.d(
                "MapScreen",
                "onSuccess: Geofence Added..."
            )
        })
        .addOnFailureListener(OnFailureListener { e ->
            val errorMessage: String = geofenceHelper.getErrorString(e)
            Log.d("MapScreen", "onFailure: $errorMessage")
        })
}

@Composable
fun CurrentLocationFloatingActionButton() {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { myLocationOverlay.enableFollowLocation() },
            backgroundColor = SapphireBlue,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = stringResource(id = R.string.create_note)
            )
        }
    }
}

fun loadKml(context: Context) {
    kmlZones.forEach { it ->
        loadZone(context, it)
    }
}

fun loadZone(context: Context, zone: Int) {
    val kmlDocument = KmlDocument()
    kmlDocument.parseKMLStream(context.resources.openRawResource(zone), null)
    val s = Style()
    s.mLineStyle = LineStyle(Color.Blue.value.toInt(), 8.0f)
    kmlDocument.addStyle(s)
    val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument)
    map.overlays.add(kmlOverlay)
    val boundingBox = kmlDocument.mKmlRoot.boundingBox
    if (geofenceNotAdded(zone)) {
        val radius: Float = boundingBox.diagonalLengthInMeters.toFloat().div(2) - 336f
        val currentGeofence = GeofenceItem(zone, boundingBox.center, radius)
        currentGeofence.setNameAndImage()
        geofences = geofences + currentGeofence
    }
    map.invalidate()
}

private fun geofenceNotAdded(kml: Int): Boolean {
    geofences.forEach { it ->
        if (it.id == kml) {
            return false
        }
    }
    return true
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun CreateSearchBar(context: Context) {
    val geocoder = Geocoder(context)
    val searchQuery = remember { mutableStateOf("") }
    val searchResults = remember { mutableStateListOf<Address>() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text(stringResource(id = R.string.search_location)) },
            placeholder = { Text(stringResource(id = R.string.location_name)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                unfocusedLabelColor = Color.White,
                placeholderColor = Color.White,
                backgroundColor = SapphireBlueTransparent,
                trailingIconColor = Color.White,
                cursorColor = Color.White
            ),
            trailingIcon = {
                if (searchQuery.value.isEmpty()) {
                    Icon(Icons.Default.Search, contentDescription = "Search" )
                } else {
                    IconButton(
                        onClick = { searchQuery.value = "" }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear" )
                    }
                } }
        )
        Button(
            onClick = { searchLocation(searchQuery.value, geocoder, searchResults) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.search_location), color = Color.White)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun searchLocation(location: String, geocoder: Geocoder, results: MutableList<Address>) {
    geocoder.getFromLocationName(location, 1, Geocoder.GeocodeListener {
        myLocationOverlay.disableFollowLocation()
        val newLocation = Location("");
        newLocation.longitude = it[0].longitude
        newLocation.latitude = it[0].latitude
        mapController.setCenter(GeoPoint(newLocation))
    })
}

object MyLocationOverlay {
    lateinit var myLocationOverlay: MyLocationNewOverlay
}

