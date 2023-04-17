package com.example.zbesp.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.font.TextAttribute

//val Typography = Typography(
//    body1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 18.sp,
//        lineHeight = 24.sp,
//    ),
//
//    body2 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//)
//)
@Composable
fun BigTitleText(text: String, alignment: TextAlign){
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h3,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h3,
            textAlign = alignment
        )
    }
}

@Composable
fun TitleText(text: String, alignment: TextAlign){
    if (isSystemInDarkTheme()) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = alignment
        )
    } else {
        Text(
            text = text,
            color = SapphireBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1,
            textAlign = alignment
        )
    }
}

@Composable
fun TopBarTittle(text: String, alignment: TextAlign){
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.body1,
        textAlign = alignment
    )
}

@Composable
fun TitleTextRed(text: String, alignment: TextAlign){
    Text(
        text = text,
        color = Color.Red,
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


@Composable
fun CommonText(){
    //TODO make a common text for info screens like about us
}
