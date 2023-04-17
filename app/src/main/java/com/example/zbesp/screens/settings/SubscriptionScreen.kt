package com.example.zbesp.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.zbesp.R
import com.example.zbesp.navigation.settings.SettingsScreens
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.ui.theme.TitleText

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SubscriptionScreen(){
    Scaffold(topBar = { ZBEspTopBar("Subscription") }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 80.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.zbeg),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small)
                        .size(250.dp)
                )
            }
            item {
                Text(text = "Here you can select your subscription mode and enjoy new features",
                    style = MaterialTheme.typography.body1)
            }
        }
    }

}