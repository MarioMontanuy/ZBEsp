package com.example.zbesp.screens.map

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.compose.material.ProgressIndicatorDefaults
import com.example.zbesp.MainActivity
import com.example.zbesp.R
import org.osmdroid.bonuspack.kml.KmlDocument

class KmlLoader: AsyncTask<Void, Void, Void>() {
    private var progressDialog: ProgressDialog = ProgressDialog(MainActivity())
    private lateinit var kmlDocument: KmlDocument

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): Void? {
        kmlDocument = KmlDocument()
        //kmlDocument.parseKMLStream()
        val kmlOverlay = kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument)
        map.overlays.add(kmlOverlay)
        return null
    }

    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.setMessage("Loading Project...")
        progressDialog.show()
    }

    override fun onPostExecute(result: Void?) {
        progressDialog.dismiss()
        map.invalidate()
        val boundingBox = kmlDocument.mKmlRoot.boundingBox
        map.zoomToBoundingBox(boundingBox, true)
        super.onPostExecute(result)
    }
}