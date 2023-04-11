package com.example.zbesp.screens.map

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.renderscript.ScriptGroup.Input
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.test.InstrumentationRegistry.getContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.zbesp.MainActivity
import com.example.zbesp.R
import org.osmdroid.bonuspack.kml.KmlDocument
import java.io.InputStream
import kotlin.coroutines.coroutineContext


class KmlLoader(inputStream: InputStream): AsyncTask<Void, Void, Void>() {
//    private var progressDialog: ProgressDialog = ProgressDialog(MainActivity().applicationContext)
    private lateinit var kmlDocument: KmlDocument
    @SuppressLint("StaticFieldLeak")
    private var currentInputStream: InputStream = inputStream
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): Void? {
        Log.i("KmlLoader2", "doInBackground")
        kmlDocument = KmlDocument()
//        kmlDocument.parseKMLStream(javaClass.getResourceAsStream("android.resource://com.example.zbesp/2131951616"), null)
        kmlDocument.parseKMLStream(currentInputStream, null);
        val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument)
        map.overlays.add(kmlOverlay)
        return null
    }

    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        super.onPreExecute()
        Log.i("KmlLoader", "onPreExecute")

//        progressDialog.setMessage("Loading Project...")
//        progressDialog.show()
    }

    override fun onPostExecute(result: Void?) {
//        progressDialog.dismiss()
        Log.i("KmlLoader", "onPostExecute")
        map.invalidate()
        val boundingBox = kmlDocument.mKmlRoot.boundingBox
////        map.zoomToBoundingBox(boundingBox, true);
//        map.controller.setCenter(boundingBox.center)
        super.onPostExecute(result)
    }
}