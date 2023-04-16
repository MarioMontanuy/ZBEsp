package com.example.zbesp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.zbesp.screens.MainScreen
import com.example.zbesp.screens.map.GeofenceBroadcastReceiver
import com.example.zbesp.ui.theme.RoyalBlue
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.ZBEspTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.map
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.InputStream

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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
            ZBEspTheme {
                val systemUiController = rememberSystemUiController()
                val darkTheme = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = SapphireBlue
                    )
                }
                MainScreen(applicationContext)
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
        val notificationPermission = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission.POST_NOTIFICATIONS
        )
        if (locationPermissions || notificationPermission) {
            showSnackbar(
                R.string.permission_denied_explanation,
                android.R.string.ok
            ) {
                locationPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                )
            }
            settings = true
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.POST_NOTIFICATIONS
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
                        ) && permissions.getOrDefault(
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    false
                )-> {
                    if (checkBackgroundPermission()) {
                        showBackgroundPermissionSettingsSnackbar()
                    }
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
        val permissionNotificationState = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.POST_NOTIFICATIONS
        )
        val permissionBackgroundLocationState = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        return (((permissionFineState == PackageManager.PERMISSION_GRANTED) ||
                (permissionCoarseState == PackageManager.PERMISSION_GRANTED)) &&
                (permissionBackgroundLocationState == PackageManager.PERMISSION_GRANTED) &&
                (permissionNotificationState == PackageManager.PERMISSION_GRANTED))
    }
    private fun checkBackgroundPermission(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }
// TODO check background permission request functionality
    private fun showBackgroundPermissionSettingsSnackbar() {
        showSnackbar(
            R.string.background_permission_explanation,
            R.string.settings
        ) {
            val intentBackground = Intent()
            intentBackground.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                "package",
                packageName, null
            )
            intentBackground.data = uri
            intentBackground.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentBackground)
        }
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




