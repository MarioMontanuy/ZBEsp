package com.example.zbesp.screens.vehicles

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.Validator
import ch.benlu.composeform.fields.*
import ch.benlu.composeform.formatters.dateShort
import ch.benlu.composeform.validators.DateValidator
import ch.benlu.composeform.validators.IsEqualValidator
import ch.benlu.composeform.validators.MinLengthValidator
import ch.benlu.composeform.validators.NotEmptyValidator
import com.example.zbesp.R
import com.example.zbesp.data.*
import com.example.zbesp.ui.theme.TitleTextRed
import com.example.zbesp.ui.theme.TitleTextWhite
import com.example.zbesp.ui.theme.getButtonColorsReversed
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class FormScreen: Form() {
    override fun self(): Form {
        return this
    }

    fun checkFields(): Boolean{
        return username.isValid.value!! && country.isValid.value!! && vehicleType.isValid.value!!
                && environmentalSticker.isValid.value!! && registrationYear.isValid.value!!
    }

    fun showError(){

        if (!username.isValid.value!!) {
            username.errorText
        }

    }
    @FormField
    val username = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(NotEmptyValidator(), MinLengthValidator(4,
            "The name must be at least 4 characters") )
    )

    @FormField
    val country = FieldState(
        state = mutableStateOf<Country?>(null),
        // define your selectable options (countries)
        options = mutableListOf(
            Country(CountryEnum.Spain)
        ),
        // format each option with the following lambda function
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val vehicleType = FieldState(
        state = mutableStateOf<VehicleType?>(null),
        // define your selectable options (countries)
        options = mutableListOf(
            VehicleType(VehicleTypeEnum.PrivateCar),
            VehicleType(VehicleTypeEnum.MotorHome),
            VehicleType(VehicleTypeEnum.Truck),
            VehicleType(VehicleTypeEnum.MotorBike),
            VehicleType(VehicleTypeEnum.Bus),
            VehicleType(VehicleTypeEnum.Van),
            VehicleType(VehicleTypeEnum.Tractor),
        ),
        // format each option with the following lambda function
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val environmentalSticker = FieldState(
        state = mutableStateOf<EnvironmentalSticker?>(null),
        // define your selectable options (countries)
        options = mutableListOf(
            EnvironmentalSticker(EnvironmentalStickerEnum.Zero),
            EnvironmentalSticker(EnvironmentalStickerEnum.ECO),
            EnvironmentalSticker(EnvironmentalStickerEnum.C),
            EnvironmentalSticker(EnvironmentalStickerEnum.B),
            EnvironmentalSticker(EnvironmentalStickerEnum.None),
        ),
        // format each option with the following lambda function
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val registrationYear = FieldState(
        state = mutableStateOf<Date?>(null),
        validators = mutableListOf(NotEmptyValidator(), PreviousDateValidator({ Calendar.getInstance().time.time},"Date should not be in the future"))
    )

    @FormField
    val enableVehicle = FieldState(
        state = mutableStateOf<Boolean?>(false)
    )

}
// TODO Move to another folder
class PreviousDateValidator(minDateTime: () -> Long, errorText: String? = null) : Validator<Date?>(
    validate = {
        (it?.time ?: -1) < minDateTime()
    },
    errorText = errorText ?: "This field is not valid."
)

class MainViewModel : ViewModel() {
    var form = FormScreen()
}

@Composable
fun SingleTextField(viewModel: MainViewModel, navController: NavController) {
    // TODO Marcar los fields obligatorios
    val error = remember { mutableStateOf(false) }
    Header(text = "New Vehicle")
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp, vertical = 80.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            label = "Vehicle name",
            form = viewModel.form,
            fieldState = viewModel.form.username,
        ).Field()

        DateField(
            label = "Registration Year",
            form = viewModel.form,
            fieldState = viewModel.form.registrationYear,
            formatter = ::dateShort
        ).Field()

        PickerField(
            label = "Country",
            form = viewModel.form,
            fieldState = viewModel.form.country
        ).Field()

        PickerField(
            label = "Vehicle Type",
            form = viewModel.form,
            fieldState = viewModel.form.vehicleType
        ).Field()

        PickerField(
            label = "Environmental Sticker",
            form = viewModel.form,
            fieldState = viewModel.form.environmentalSticker
        ).Field()

        CheckboxField(
            fieldState = viewModel.form.enableVehicle,
            label = "Mark as current vehicle",
            form = viewModel.form
        ).Field()
        Button(
            onClick = {
                if (viewModel.form.checkFields()){
                    val image = "R.drawable.vehicle"/*+viewModel.form.vehicleType.state.value!!.type*/
                    Log.i("FormScreen", "image:$image")
                    if (viewModel.form.enableVehicle.state.value!!) {
                        noEnabledVehicle()
                    }
                    if (vehicles.isEmpty()) {
                        viewModel.form.enableVehicle.state.value = true
                    }

                    val newVehicle = Vehicle(
                        currentId,
                        viewModel.form.username.state.value!!,
                        viewModel.form.country.state.value!!,
                        viewModel.form.vehicleType.state.value!!,
                        viewModel.form.registrationYear.state.value!!,
                        viewModel.form.environmentalSticker.state.value!!,
                        viewModel.form.enableVehicle.state.value!!,
                        R.drawable.private_car,
                        R.drawable.private_car,
                    )
                    currentId += 1L
                    newVehicle.setImage(newVehicle, viewModel.form.vehicleType.state.value!!)
                    vehicles = vehicles + newVehicle
                    navController.popBackStack()
                } else {
                    // TODO show error in the field
                    error.value = true
                }
            },
            colors = getButtonColorsReversed(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            TitleTextWhite("Create vehicle", TextAlign.Start)
        }
        if (error.value) {
            TitleTextRed("Fields should not be empty", TextAlign.Start)
        }
    }
}
