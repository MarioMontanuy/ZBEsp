package com.example.zbesp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.awt.font.TextAttribute

val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
)
)

@Composable
fun TitleText(text: String, alignment: TextAlign){
    Text(
        text = text,
        color = SapphireBlue,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body1,
        textAlign = alignment
    )
}

@Composable
fun SubtitleText(text: String, alignment: TextAlign){
    Text(
        text = text,
        style = MaterialTheme.typography.body2,
        textAlign = alignment
    )
}
