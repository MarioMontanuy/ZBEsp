package com.example.zbesp.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.screens.ZBEspTopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SubscriptionScreen(navController: NavController) {
    Scaffold(topBar = {
        ZBEspTopBar(
            stringResource(id = R.string.subscription_screen_title),
            navController
        )
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
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
                Text(
                    text = stringResource(id = R.string.subscription_screen_text),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}