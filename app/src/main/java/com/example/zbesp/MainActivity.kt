package com.example.zbesp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import com.example.zbesp.screens.MainScreen
import com.example.zbesp.ui.theme.BottomNavigationBarTheme
import com.example.zbesp.ui.theme.SapphireBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.snackbar.Snackbar
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.InputStream

class MainActivity : ComponentActivity() {
    private lateinit var map: MapView
    private var settings = false
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionOnCreate()
        Log.i("MainActivity1", "applname:" + applicationContext.packageName)
        Log.i("MainActivity1", "raw:" + R.raw.lleida)
        val res = resources.openRawResource(R.raw.lleida)
        Log.i("MainActivity1", "raw2:$res")
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        setContent {
            BottomNavigationBarTheme {
                val systemUiController = rememberSystemUiController()
                val darkTheme = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if (darkTheme) Color.LightGray else SapphireBlue
                    )
//                    systemUiController.setNavigationBarColor(
//                        color = if (darkTheme) Color.Black else RoyalBlue
//                    )
                }
                MainScreen(applicationContext.resources.openRawResource(R.raw.lleida_verde))
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public override fun onResume() {
        super.onResume()

            if (!checkPermissions() && !settings) {
                Log.i("Main", "OnResume")
                requestPermissions()
            }
//            else {
//                PERMISSIONS_GRANTED = true
//            }

    }

//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ),
//            PERMISSION_REQUEST_CODE
//        )
//    }
//
//    private fun isPermissionGranted(): Boolean {
//        return if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.i(TAG, "Permission is granted");
//            true
//        } else {
//            Log.i(TAG, "Permission is revoked");
//            requestPermission()
//            false
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.i(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
//        }
//    }
//    private fun showSnackbar(
//        mainTextStringId: Int, actionStringId: Int,
//        listener: View.OnClickListener
//    ) {
//        Snackbar.make(
//            findViewById(android.R.id.content),
//            getString(mainTextStringId),
//            Snackbar.LENGTH_INDEFINITE
//        )
//            .setAction(getString(actionStringId), listener).show()
//    }
private fun showSnackbar(
    mainTextStringId: Int, actionStringId: Int,
    listener: View.OnClickListener
) {
    Snackbar.make(
        findViewById(android.R.id.content),
        getString(mainTextStringId),
        Snackbar.LENGTH_INDEFINITE
    )
        .setAction(getString(actionStringId), listener).show()
}

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissionOnCreate() {
        requestLocation()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissions() {
        val locationPermissions = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) || ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (locationPermissions) {
            showSnackbar(
                R.string.permission_denied_explanation,
                android.R.string.ok
            ) {
                locationPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
            settings = true
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun requestLocation() {
        locationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                (permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false)
                        || permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ,
                    false
                )
                        || permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, false)
                        ) -> {
                }
                else -> {
                    showSnackbar(
                        R.string.permission_denied_explanation,
                        R.string.settings
                    ) {
                        val intentBuild = Intent()
                        intentBuild.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            packageName, null
                        )
                        intentBuild.data = uri
                        intentBuild.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intentBuild)
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkPermissions(): Boolean {
        val permissionFineState = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permissionCoarseState = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        return (((permissionFineState == PackageManager.PERMISSION_GRANTED) ||
                (permissionCoarseState == PackageManager.PERMISSION_GRANTED)))
    }

//    class KmlLoader(stream: InputStream): AsyncTask<Void, Void, Void>() {
//        //    private var progressDialog: ProgressDialog = ProgressDialog(MainActivity().applicationContext)
//        private lateinit var kmlDocument: KmlDocument
//        private var currentStream = stream
//        @Deprecated("Deprecated in Java")
//        override fun doInBackground(vararg params: Void?): Void? {
//            Log.i("KmlLoader", "doInBackground")
//            kmlDocument = KmlDocument()
////        kmlDocument.parseKMLStream(javaClass.getResourceAsStream("android.resource://com.example.zbesp/2131951616"), null)
//            kmlDocument.parseKMLStream(resources, null);
//            val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument)
//            map.overlays.add(kmlOverlay)
//            return null
//        }
//
//        @Deprecated("Deprecated in Java")
//        override fun onPreExecute() {
//            super.onPreExecute()
//            Log.i("KmlLoader", "onPreExecute")
//
////        progressDialog.setMessage("Loading Project...")
////        progressDialog.show()
//        }
//
//        override fun onPostExecute(result: Void?) {
////        progressDialog.dismiss()
//            Log.i("KmlLoader", "onPostExecute")
//            map.invalidate()
//            val boundingBox = kmlDocument.mKmlRoot.boundingBox
//            map.zoomToBoundingBox(boundingBox, true);
////        map.controller.setCenter(boundingBox.center)
//            super.onPostExecute(result)
//        }
//    }
}




