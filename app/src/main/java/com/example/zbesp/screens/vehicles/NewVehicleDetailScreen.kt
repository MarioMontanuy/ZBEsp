package com.example.zbesp.screens.vehicles

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zbesp.MainActivity
import com.example.zbesp.R
import com.example.zbesp.data.EnvironmentalSticker
import com.example.zbesp.data.Vehicle
import com.example.zbesp.data.VehicleType
import com.example.zbesp.data.noEnabledVehicle
import com.example.zbesp.screens.MainScreen
import com.example.zbesp.ui.theme.*
import java.util.*
import kotlin.reflect.typeOf


private lateinit var mDatePickerDialog: DatePickerDialog
private lateinit var mDate: MutableState<String>
private lateinit var selectedCountry: String
private lateinit var selectedYear: String
private lateinit var selectedType: VehicleType
private lateinit var selectedSticker: EnvironmentalSticker
@Composable
fun NewVehicleDetailScreen() {
    DatePicker()
    Header(text = "New Vehicle")
    Column(
        modifier = Modifier
            .padding(20.dp)
            .padding(top = 50.dp)
            .fillMaxWidth()
    ) {
        var vehicleName = AddTextFieldRow("Enter name")
        DropDownMenu(arrayOf("Spain"), "Enter country")
        CalendarButton("Enter Registration Year")
        DropDownMenu(VehicleType.values(), "Enter Type")
        DropDownMenu(EnvironmentalSticker.values(), "Enter Environmental Sticker")
        Spacer(modifier = Modifier.padding(50.dp))
        Button(
            onClick = {
//                      val vehicle = Vehicle(1L,vehicleName,)
            },
            colors = getButtonColorsReversed(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            TitleTextWhite("Create Vehicle", TextAlign.Start)
        }
    }
}

@Composable
fun AddTextFieldRow(label: String): String {
    var textState by remember { mutableStateOf("") }
    TextField(
        value = textState,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { textState = it },
        label = { TitleText(label, TextAlign.Start) },
        colors = formTextFieldColors()
    )
    return textState
}
@Composable
fun CalendarButton(text: String){
    Box(
        contentAlignment = Alignment.CenterStart, modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                mDatePickerDialog.show()
            },
            colors = getButtonColors(),
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleText(text, TextAlign.Start)
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
        contentAlignment = Alignment.CenterStart, modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
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
fun DatePicker(){
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()
    mDate = remember { mutableStateOf("") }
    mDatePickerDialog = DatePickerDialog(
        mContext,
        R.style.ThemeOverlay_MyApp_DatePicker,
        { _: DatePicker, currentYear: Int, currentMonth: Int, currentDayOfMonth: Int ->
            mDate.value = "$currentDayOfMonth/${currentMonth + 1}/$currentYear"
        }, mYear, mMonth, mDay
    )
}


//@Composable
//fun ComposeDatePicker(){
//    val calendar = Calendar.getInstance()
//    calendar.set(Calendar.YEAR, 1999)
//    calendar.set(Calendar.MONTH, 1)
//    calendar.set(Calendar.DAY_OF_MONTH, 1)
//
//    val calendarMax = Calendar.getInstance()
//    calendarMax.set(Calendar.YEAR, 2044)
//    calendarMax.set(Calendar.MONTH, 0)
//    calendarMax.set(Calendar.DAY_OF_MONTH, 1)
//
//    val initialCalendar = Calendar.getInstance()
//    initialCalendar.set(Calendar.YEAR, 2022)
//    initialCalendar.set(Calendar.MONTH, 3)
//    initialCalendar.set(Calendar.DAY_OF_MONTH, 1)
//
//    open = false
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.Gray),
//        contentAlignment = Alignment.Center
//    ) {
//        if (open) {
//            Box(
//                Modifier
//                    .fillMaxWidth(0.8f)
//                    .fillMaxHeight(0.7f)) {
//                ComposeCalendar(
//                    minDate = calendar.time,
//                    maxDate = calendarMax.time,
//                    initialDate = initialCalendar.time,
//                    locale = Locale("en"),
//                    title = "Select Date",
//                    buttonTextSize = 15.sp,
//                    calendarType = CalendarType.ONE_SCREEN_MONTH_AND_YEAR,
//                    monthViewType = MonthViewType.ONLY_NUMBER_ONE_COLUMN,
//                    listener = object : SelectDateListener {
//                        override fun onDateSelected(date: Date) {
//                            Log.i("Selected Date: ", date.toString())
//                        }
//
//                        override fun onCanceled() {
//                            open = false
//                        }
//                    })
//            }
//        }
//    }
//}


