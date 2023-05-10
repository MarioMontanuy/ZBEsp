package com.example.zbesp.data

import android.util.Log
import androidx.compose.runtime.Immutable
import ch.benlu.composeform.fields.PickerValue
import com.example.zbesp.screens.userEmail
import com.example.zbesp.screens.vehicles.communityVehicles
import com.example.zbesp.screens.vehicles.vehicles
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date


const val DATABASE = "https://zbesp-a6692-default-rtdb.europe-west1.firebasedatabase.app/"
//val vehiclesDatabase: DatabaseReference = Firebase.database(DATABASE).getReference("Vehicles")
lateinit var vehiclesDatabase: CollectionReference
val idDatabase = Firebase.firestore.collection("id")
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
    constructor() : this(0,"","","",Date(),"", false, 0, 0, 0, "")
//    fun setImage(type: VehicleType) {
//        if (type.type == VehicleTypeEnum.PrivateCar) {
//            this.imageId = R.drawable.private_car
//        }
//        if (type.type == VehicleTypeEnum.MotorHome) {
//            this.imageId = R.drawable.motor_home
//        }
//        if (type.type == VehicleTypeEnum.Truck) {
//            this.imageId = R.drawable.truck
//        }
//        if (type.type == VehicleTypeEnum.MotorBike) {
//            this.imageId = R.drawable.motor_bike
//        }
//        if (type.type == VehicleTypeEnum.Bus) {
//            this.imageId = R.drawable.bus
//        }
//        if (type.type == VehicleTypeEnum.Van) {
//            this.imageId = R.drawable.van
//        }
//        if (type.type == VehicleTypeEnum.Tractor) {
//            this.imageId = R.drawable.tractor
//        }
//    }
//
//    fun changeToWhite(type: VehicleType): Int {
//        if (type.type == VehicleTypeEnum.PrivateCar) {
//            return R.drawable.private_car_white
//        }
//        if (type.type == VehicleTypeEnum.MotorHome) {
//            return R.drawable.motor_home_white
//        }
//        if (type.type == VehicleTypeEnum.Truck) {
//            return R.drawable.truck_white
//        }
//        if (type.type == VehicleTypeEnum.MotorBike) {
//            return R.drawable.motor_bike_white
//        }
//        if (type.type == VehicleTypeEnum.Bus) {
//            return R.drawable.bus_white
//        }
//        if (type.type == VehicleTypeEnum.Van) {
//            return R.drawable.van_white
//        }
//        if (type.type == VehicleTypeEnum.Tractor) {
//            return R.drawable.tractor_white
//        }
//        return 0
//    }
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

//
//val vehicleNone: Vehicle =
//    Vehicle(
//        id = 1L,
//        name = "Car1",
//        country = Country(CountryEnum.Spain),
//        type = VehicleType(VehicleTypeEnum.PrivateCar),
//        registrationYear = Date(101, 5, 5),
//        environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.None, R.drawable.pegatinanone),
//        enabled = true,
//        imageId = R.drawable.private_car,
//    )
//
//private val vehicleB: Vehicle = Vehicle(
//    id = 2L,
//    name = "Car2",
//    country = Country(CountryEnum.Spain),
//    type = VehicleType(VehicleTypeEnum.PrivateCar),
//    registrationYear = Date(101, 5, 5),
//    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.B, R.drawable.pegatinab),
//    enabled = false,
//    imageId = R.drawable.private_car
//)
//
//
//private val vehicleC: Vehicle = Vehicle(
//    id = 3L,
//    name = "Car3",
//    country = Country(CountryEnum.Spain),
//    type = VehicleType(VehicleTypeEnum.PrivateCar),
//    registrationYear = Date(101, 5, 5),
//    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.C, R.drawable.pegatinac),
//    enabled = false,
//    imageId = R.drawable.private_car,
//)
//
//private val vehicleECO: Vehicle = Vehicle(
//    id = 4L,
//    name = "Car4",
//    country = Country(CountryEnum.Spain),
//    type = VehicleType(VehicleTypeEnum.PrivateCar),
//    registrationYear = Date(101, 5, 5),
//    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.ECO, R.drawable.pegatinaeco),
//    enabled = false,
//    imageId = R.drawable.private_car,
//)
//
//private val vehicleZero: Vehicle = Vehicle(
//    id = 5L,
//    name = "Car5",
//    country = Country(CountryEnum.Spain),
//    type = VehicleType(VehicleTypeEnum.PrivateCar),
//    registrationYear = Date(101, 5, 5),
//    environmentalSticker = EnvironmentalSticker(EnvironmentalStickerEnum.Zero, R.drawable.pegatinazero),
//    enabled = false,
//    imageId = R.drawable.private_car,
//)
//
//object VehiclesRepo {
//    fun getVehicles(): List<Vehicle> = vehicles
//}

fun noEnabledVehicleInDatabase(currentVehicle: Vehicle) {
    var vehiclesKeys = listOf<String>()
    var vehicleEnabledKey = ""
    vehiclesDatabase.get().addOnSuccessListener { it.forEach {
            value ->
        if (value.data["id"] == currentVehicle.id) {
            vehicleEnabledKey = value.id
        }
        vehiclesKeys = vehiclesKeys + value.id
        vehiclesKeys.forEach { id ->
            if (id == vehicleEnabledKey) {
                vehiclesDatabase.document(id).update("enabled", true)
            } else {
                vehiclesDatabase.document(id).update("enabled", false)
            }
        }
    } }
}
fun createIdOnDatabase() {
    Log.i("createIdOnDatabase", "llamada")
    idDatabase.document(userEmail).get().addOnSuccessListener {
        if (it.data == null) {
            idDatabase.document(userEmail).set(hashMapOf("id" to 0L))
        }
    }
}
/*fun getIdFromDatabase() : Long {


    Log.i("getIdFromDatabase", "post $id")
}*/


//    listOf(vehicleNone,
//        vehicleB,
//        vehicleC,
//        vehicleECO,
//        vehicleZero,
//)

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