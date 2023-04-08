package com.example.zbesp.screens.map

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.zbesp.R
import com.example.zbesp.databinding.MapActivityBinding
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var binding: MapActivityBinding
//    private lateinit var circle: MapCircleOverlay
    private lateinit var circleGeopoint: GeoPoint
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        setContentView(binding.root)
        initializeMap()
//        addProtoZBE()
    }


    private fun initializeMap() {
        map = findViewById(R.id.mapView)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        myLocationOverlay = MyLocationNewOverlay(map)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        map.overlays.add(myLocationOverlay)
        val mapController = map.controller
        mapController.setZoom(16.0)
    }

//    private fun addProtoZBE() {
//        circleGeopoint = GeoPoint(41.617592,0.620015 )
//        circle = MapCircleOverlay(circleGeopoint)
//        map.overlays.add(circle)
//        map.invalidate()
//    }
    companion object {
        lateinit var myLocationOverlay: MyLocationNewOverlay
    }
}