package com.example.zbesp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.SideEffect
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.zbesp.data.vehiclesDatabase
import com.example.zbesp.navigation.authentication.AuthenticationNavGraph
import com.example.zbesp.screens.LogInScreen
import com.example.zbesp.screens.MainScreen
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.ZBEspTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ValueEventListener
import org.osmdroid.config.Configuration

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    private var settings = false
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionOnCreate()
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        setContent {
            ZBEspTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = SapphireBlue
                    )
                }
                AuthenticationNavGraph(context = this)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public override fun onResume() {
        super.onResume()
        if (!checkPermissions() && !settings) {
            requestPermissions()
        }

    }

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
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    false
                )
                        || permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, false
                )
                        ) && permissions.getOrDefault(
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    false
                ) -> {
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
                            getString(R.string.package_text),
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

    private fun showBackgroundPermissionSettingsSnackbar() {
        showSnackbar(
            R.string.background_permission_explanation,
            R.string.settings
        ) {
            val intentBackground = Intent()
            intentBackground.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                getString(R.string.package_text),
                packageName, null
            )
            intentBackground.data = uri
            intentBackground.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentBackground)
        }
    }
}




