package com.example.zbesp.screens.vehicles

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import ch.benlu.composeform.validators.MinLengthValidator
import ch.benlu.composeform.validators.NotEmptyValidator
import com.example.zbesp.R
import com.example.zbesp.data.*
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.*
import java.util.*

class FormScreen : Form() {
    override fun self(): Form {
        return this
    }

    fun checkFields(): Boolean {
        return username.isValid.value!! && country.isValid.value!! && vehicleType.isValid.value!!
                && environmentalSticker.isValid.value!! && registrationYear.isValid.value!!
    }

    fun showError() {
        if (!username.isValid.value!!) {
            username.errorText
        }
    }

    @FormField
    val username = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(
            NotEmptyValidator(), MinLengthValidator(
                4,
                "The name must be at least 4 characters"
            )
        )
    )

    @FormField
    val country = FieldState(
        state = mutableStateOf<Country?>(null),
        options = mutableListOf(
            Country(CountryEnum.Spain)
        ),
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val vehicleType = FieldState(
        state = mutableStateOf<VehicleType?>(null),
        options = mutableListOf(
            VehicleType(VehicleTypeEnum.PrivateCar),
            VehicleType(VehicleTypeEnum.MotorHome),
            VehicleType(VehicleTypeEnum.Truck),
            VehicleType(VehicleTypeEnum.MotorBike),
            VehicleType(VehicleTypeEnum.Bus),
            VehicleType(VehicleTypeEnum.Van),
            VehicleType(VehicleTypeEnum.Tractor),
        ),
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val environmentalSticker = FieldState(
        state = mutableStateOf<EnvironmentalSticker?>(null),
        options = mutableListOf(
            EnvironmentalSticker(EnvironmentalStickerEnum.Zero),
            EnvironmentalSticker(EnvironmentalStickerEnum.ECO),
            EnvironmentalSticker(EnvironmentalStickerEnum.C),
            EnvironmentalSticker(EnvironmentalStickerEnum.B),
            EnvironmentalSticker(EnvironmentalStickerEnum.None),
        ),
        optionItemFormatter = { "${it?.type}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val registrationYear = FieldState(
        state = mutableStateOf<Date?>(null),
        validators = mutableListOf(
            NotEmptyValidator(),
            PreviousDateValidator(
                { Calendar.getInstance().time.time },
                "Date should not be in the future"
            )
        )
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

// TODO change color in some fields in dark mode
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FormScreen(viewModel: MainViewModel, navController: NavController) {
    // TODO Marcar los fields obligatorios
    val error = remember { mutableStateOf(false) }
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.form_screen_title)) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.padding(20.dp))
                TitleText(
                    text = stringResource(id = R.string.create_your_vehicle),
                    alignment = TextAlign.Justify,
                    style = MaterialTheme.typography.h5
                )
            }
            item {
                Spacer(modifier = Modifier.padding(20.dp))
                TextField(
                    label = stringResource(id = R.string.vehicle_name),
                    form = viewModel.form,
                    fieldState = viewModel.form.username,
                ).Field()
            }
            item {
                DateField(
                    label = stringResource(id = R.string.registration_year),
                    form = viewModel.form,
                    fieldState = viewModel.form.registrationYear,
                    formatter = ::dateShort
                ).Field()
            }
            item {
                PickerField(
                    label = stringResource(id = R.string.country),
                    form = viewModel.form,
                    fieldState = viewModel.form.country
                ).Field()
            }
            item {
                PickerField(
                    label = stringResource(id = R.string.type),
                    form = viewModel.form,
                    fieldState = viewModel.form.vehicleType
                ).Field()
            }
            item {
                PickerField(
                    label = stringResource(id = R.string.environmental_sticker),
                    form = viewModel.form,
                    fieldState = viewModel.form.environmentalSticker
                ).Field()
            }
            item {
                CheckboxField(
                    fieldState = viewModel.form.enableVehicle,
                    label = stringResource(id = R.string.mark_current_vehicle),
                    form = viewModel.form
                ).Field()
            }
            item {
                Button(
                    onClick = {
                        if (viewModel.form.checkFields()) {
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
                            )
                            currentId += 1L
                            newVehicle.setImage(viewModel.form.vehicleType.state.value!!)
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
                    TopBarTittle(stringResource(id = R.string.create_vehicle), TextAlign.Justify)
                }
            }
            item {
                if (error.value) {
                    TitleTextRed(stringResource(id = R.string.fields_not_empty), TextAlign.Justify)
                }
            }
        }
    }
}
