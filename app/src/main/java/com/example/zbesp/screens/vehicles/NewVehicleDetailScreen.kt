package com.example.zbesp.screens.vehicles

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zbesp.R
import com.example.zbesp.data.EnvironmentalSticker
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.VehicleType
import com.example.zbesp.ui.theme.SapphireBlue
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.ui.theme.formTextFieldColors
import com.example.zbesp.ui.theme.getButtonColors
import java.util.*


@Composable
fun NewVehicleDetailScreen() {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        AddTextFieldRow("Enter name")
        DropDownMenu(arrayOf("Spain"), "Enter country")
        DropDownMenu("Enter Registration Year")
        DropDownMenu(VehicleType.values(), "Enter Type")
        DropDownMenu(EnvironmentalSticker.values(), "Enter Environmental Sticker")

    }
}

@Composable
fun AddTextFieldRow(label: String) {
    var textState by remember { mutableStateOf("") }

    TextField(
        value = textState,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { textState = it },
        label = { TitleText(label, TextAlign.Start) },
        colors = formTextFieldColors()
    )
}
@Composable
fun DropDownMenu(text: String){
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.CenterStart, modifier = Modifier.background(Color.White).fillMaxWidth()
    ) {
        Button(
            onClick = {
                expanded = true
            },
            colors = getButtonColors(),
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleText(text, TextAlign.Start)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier,
//            offset = DpOffset(x = 130.dp, y = 0.dp)
        ) {
            datePicker()
        }
    }
}

@Composable
fun <T> DropDownMenu(items: Array<T>, text: String) {
    val disabledItem = 1
    val contextForToast = LocalContext.current.applicationContext
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.CenterStart, modifier = Modifier.background(Color.White).fillMaxWidth()
    ) {
        Button(
            onClick = {
                expanded = true
            },
            colors = getButtonColors(),
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleText(text, TextAlign.Start)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier,
//            offset = DpOffset(x = 130.dp, y = 0.dp)
        ) {
            items.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        Toast.makeText(
                            contextForToast,
                            itemValue.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        expanded = false
                    },
                    enabled = (itemIndex != disabledItem)
                ) {
                    Text(text = itemValue.toString())
                }
            }
        }
    }
}

@Composable
fun datePicker(){

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        R.style.ThemeOverlay_MyApp_DatePicker,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )
    mDatePickerDialog.show()
}


