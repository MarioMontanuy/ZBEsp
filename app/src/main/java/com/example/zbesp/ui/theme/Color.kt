package com.example.zbesp.ui.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Navy = Color(0xFF073042)
val Blue = Color(0xFF4285F4)
val LightBlue = Color(0xFFD7EFFE)
val Chartreuse = Color(0xFFEFF7CF)
val SapphireBlue = Color(0xFF0F52BA)
val BrightBlue = Color(0xFF0096FF)
val RoyalBlue = Color(0xFF4169E1)
val Verdigris = Color(0xFF40B5AD)
val Turquoise = Color(0xFF40E0D0)

@Composable
fun formTextFieldColors(
    textColor: Color = Color.Black,
    disabledTextColor: Color = RoyalBlue,
    backgroundColor: Color = Color.White,
    cursorColor: Color = SapphireBlue,
    errorCursorColor: Color = Color.Red,
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor,
    focusedIndicatorColor = SapphireBlue
)
@Composable
fun getButtonColors(): ButtonColors{
    return ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        contentColor = SapphireBlue,
        disabledBackgroundColor = Color.White,
        disabledContentColor = SapphireBlue
    )
}