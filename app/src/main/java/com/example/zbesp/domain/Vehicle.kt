package com.example.zbesp.domain

import android.util.Log
import androidx.compose.runtime.Immutable
import ch.benlu.composeform.fields.PickerValue
import com.example.zbesp.screens.userEmail
import com.example.zbesp.screens.vehicles.communityVehicles
import com.example.zbesp.screens.vehicles.vehicles
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.Date


const val DATABASE = "https://zbesp-a6692-default-rtdb.europe-west1.firebasedatabase.app/"
//val vehiclesDatabase: DatabaseReference = Firebase.database(DATABASE).getReference("Vehicles")
var vehiclesDatabase = Firebase.firestore.collection("vehicles")
lateinit var commentsDatabase: CollectionReference
val idDatabase = Firebase.firestore.collection("id")
//val idDatabase = getFirestore().collection("id")
//val postListener = object : ValueEventListener {
//    var currentVehicleList = listOf<Vehicle>()
//    override fun onDataChange(dataSnapshot: DataSnapshot) {
//        // Get Post object and use the values to update the UI
//        Log.i("onDataChange", "onDataChange" )
//        dataSnapshot.children.forEach { it ->
//            val countryMap : Map<String, Country> = it.child("country").value as Map<String, Country>
//            val currentCountry = Country(CountryEnum.valueOf(countryMap["type"].toString()))
//            val typeMap : Map<String, VehicleType> = it.child("type").value as Map<String, VehicleType>
//            val currentType = VehicleType(VehicleTypeEnum.valueOf(typeMap["type"].toString()))
//
//            Log.i("mapresult", typeMap["type"].toString())
//
//            val currentSticker = EnvironmentalSticker(
//                EnvironmentalStickerEnum.valueOf(it.child("environmentalSticker")
//                    .child("type").value as String), it.child("environmentalSticker")
//                    .child("stickerImage").value.toString().toInt())
////            it.child("registrationYear").value as Date
//            val currentVehicle = Vehicle(it.child("id").value.toString().toLong(), it.child("name").value as String,
//                currentCountry, currentType,
//                Calendar.getInstance().time,
//                currentSticker,
//                it.child("enabled").value as Boolean)
//            currentVehicle.imageId = it.child("imageId").value.toString().toInt()
//            currentVehicleList  = currentVehicleList + currentVehicle
//
//        }
//        vehicles = currentVehicleList.reversed()
//        currentVehicleList = listOf()
//    }
//
//    override fun onCancelled(databaseError: DatabaseError) {
//        // Getting Post failed, log a message
//        Log.w("Vehicle", "loadPost:onCancelled", databaseError.toException())
//    }
//}

@Immutable
data class Vehicle(
    val id: Long,
    val name: String,
    val country: String,
    val type: String,
    val registrationYear: Date,
    val environmentalSticker: String,
    var enabled: Boolean,
    var stickerImage: Int,
    var typeImage: Int,
    var typeImageWhite: Int,
    var owner: String,
) {
    constructor() : this(0,"Test","Spain","Private Car",Date(),"None", false, 2131165462, 2131165469, 2131165470, "test@test.com")
}

data class VehicleType(val type: VehicleTypeEnum?, val typeImage: Int, val typeImageWhite: Int) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type!!.name.startsWith(query)
    }
}

data class EnvironmentalSticker(val type: EnvironmentalStickerEnum?, val stickerImage: Int) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type!!.name.startsWith(query)
    }
}

data class Country(val type: CountryEnum?) : PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type!!.name.startsWith(query)
    }
}

fun noEnabledVehicleInDatabase(currentVehicle: Vehicle) {
    var vehiclesKeys = listOf<String>()
    var vehicleEnabledKey = ""
    Log.i("noEnabledVehicleInDatabase", "noEnabledVehicleInDatabase")
    vehiclesDatabase.get().addOnSuccessListener {
        it.forEach { snapshot ->
            val dbVehicle = snapshot.toObject<Vehicle>()
            Log.i("noEnabledVehicleInDatabase", dbVehicle.toString())
            if (dbVehicle.id == currentVehicle.id) {
                vehiclesDatabase.document(snapshot.id).update("enabled", true)
            } else {
                if (dbVehicle.owner == userEmail) {
                    vehiclesDatabase.document(snapshot.id).update("enabled", false)
                }
            }
        }
    }
}
fun createIdOnDatabase() {
    Log.i("createIdOnDatabase", "llamada")
    idDatabase.document(userEmail).get().addOnSuccessListener {
        if (it.data == null) {
            idDatabase.document(userEmail).set(hashMapOf("id" to 0L))
        }
    }
}

enum class CountryEnum(type: String) {
    Spain("Spain"),
}

enum class VehicleTypeEnum(type: String) {
    PrivateCar("Private Car"),
    MotorHome("Motor Home"),
    Truck("Truck"),
    MotorBike("Motor Bike"),
    Bus("Bus"),
    Van("Van"),
    Tractor("Tractor");
}

enum class EnvironmentalStickerEnum(type: String) {
    Zero("Zero"),
    ECO("ECO"),
    C("C"),
    B("B"),
    None("None");
}

fun getVehicle(vehicleId: String): Vehicle {
    return vehicles.value.first { it.id == vehicleId.toLong() }
}
fun getVehicleCommunity(vehicleId: String): Vehicle {
    return communityVehicles.value.first { it.id == vehicleId.toLong() }
}

fun getCurrentVehicle(): Vehicle? {
    if (vehicles.value.isNotEmpty()) {
        return vehicles.value.first { it.enabled }
    }
    return null
}

@Immutable
data class Comment(
    val title: String,
    val commentText: String,
    var owner: String,
    var zoneName: String
) {
    constructor() : this("","","", "")
}