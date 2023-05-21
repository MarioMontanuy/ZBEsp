package com.example.zbesp.domain

import android.util.Log
import androidx.compose.runtime.Immutable
import ch.benlu.composeform.fields.PickerValue
import com.example.zbesp.screens.userEmail
import com.example.zbesp.screens.vehicles.communityVehicles
import com.example.zbesp.screens.vehicles.vehicles
import com.example.zbesp.screens.zones.comments
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.Date

var vehiclesDatabase = Firebase.firestore.collection("vehicles")
lateinit var commentsDatabase: CollectionReference
val idDatabase = Firebase.firestore.collection("id")

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
    constructor() : this(
        0,
        "Test",
        "Spain",
        "Private Car",
        Date(),
        "None",
        false,
        2131165462,
        2131165469,
        2131165470,
        "test@test.com"
    )
}

data class VehicleType(val type: VehicleTypeEnum?, val typeImage: Int, val typeImageWhite: Int) :
    PickerValue() {
    override fun searchFilter(query: String): Boolean {
        return this.type!!.name.startsWith(query)
    }
}

data class EnvironmentalSticker(val type: EnvironmentalStickerEnum?, val stickerImage: Int) :
    PickerValue() {
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
    vehiclesDatabase.get().addOnSuccessListener {
        it.forEach { snapshot ->
            val dbVehicle = snapshot.toObject<Vehicle>()
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

fun getVehicle(vehicleId: String?): Vehicle {
    return try {
        vehicles.value.first { it.id == vehicleId!!.toLong() }
    } catch (e: NoSuchElementException) {
        Vehicle()
    }
}

fun getVehicleCommunity(vehicleId: String?): Vehicle {
    return communityVehicles.value.first { it.id == vehicleId!!.toLong() }
}

fun getCurrentVehicle(): Vehicle? {
    if (vehicles.value.isNotEmpty()) {
        return vehicles.value.first { it.enabled }
    }
    return null
}

