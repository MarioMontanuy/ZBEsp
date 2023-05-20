package com.example.zbesp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.zbesp.navigation.authentication.AuthenticationNavGraph
import com.example.zbesp.network.NetworkStatusObserver
import com.example.zbesp.network.StatusObserver
import com.example.zbesp.service.MapNotificationService
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.ZBEspTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import org.osmdroid.config.Configuration

private lateinit var statusObserver: StatusObserver
var currentConnectivity = StatusObserver.Status.Unknown
var currentUserConnectivity = true
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
        statusObserver = NetworkStatusObserver(this)
        checkTestMode()
        getToken()
        val intentService = Intent(this, MapNotificationService::class.java)
        startService(intentService)
        setContent {
            ZBEspTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = SapphireBlue
                    )
                }
                val status by statusObserver.observe()
                    .collectAsState(initial = StatusObserver.Status.Unknown)
                if (status != StatusObserver.Status.Unknown) {
                    Toast.makeText(this, "Network $status", Toast.LENGTH_SHORT).show()
                    currentConnectivity = status
                    if (currentUserConnectivity && currentConnectivity != StatusObserver.Status.WiFi || currentConnectivity == StatusObserver.Status.Lost) {
                        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT)
                            .show()
                    }
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

private fun checkTestMode() {
    firebaseFirestore()
    firebaseAuth()
    firebaseFunctions()
}

private fun firebaseFirestore() {
    val firestore = Firebase.firestore
    firestore.useEmulator("10.0.2.2", 8080)
    firestore.firestoreSettings = firestoreSettings {
        isPersistenceEnabled = false
    }
}

private fun firebaseAuth() {
    val auth = Firebase.auth
    auth.useEmulator("10.0.2.2", 9099)
}

private fun firebaseFunctions() {
    val functions = Firebase.functions
    functions.useEmulator("10.0.2.2", 5001)
}

private fun getToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("getToken", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
        val token = task.result
        val tokenMap: MutableMap<String, Boolean> = mutableMapOf()
        tokenMap[token] = true
        val firebaseRef = Firebase.firestore.collection("tokens").document(token)
        firebaseRef.set(tokenMap).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("token added", "successful")
            } else {
                Log.i("token added", "error")
            }
        }
        Log.d("getToken", token)
    })
}


