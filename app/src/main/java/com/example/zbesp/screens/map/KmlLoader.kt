package com.example.zbesp.screens.map

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import androidx.compose.ui.graphics.Color
import org.osmdroid.bonuspack.kml.KmlDocument
import org.osmdroid.bonuspack.kml.LineStyle
import org.osmdroid.bonuspack.kml.Style
import org.osmdroid.bonuspack.kml.StyleSelector
import java.io.InputStream


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
        kmlDocument.parseKMLStream(currentInputStream, null)
        val s = Style()
        s.mLineStyle = LineStyle(0xFF0F52BA.toInt(), 8.0f)
        kmlDocument.addStyle(s)
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