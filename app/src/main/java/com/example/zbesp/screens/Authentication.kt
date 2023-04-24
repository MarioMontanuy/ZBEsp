package com.example.zbesp.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.R
import com.example.zbesp.navigation.authentication.AuthenticationScreens
import com.example.zbesp.ui.theme.BigTitleText
import com.example.zbesp.ui.theme.MediumTitleText
import com.example.zbesp.ui.theme.TitleText

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LogInScreen(navController: NavController) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    val passwordIcon = if (passwordVisibility.value)
        Icons.Default.Visibility
    else
        Icons.Default.VisibilityOff

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 60.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top ) {
        Image(
                painter = painterResource(id = R.drawable.zbeg),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.small)
                    .size(200.dp)
            )
        TitleText("Login to your account", TextAlign.Center)
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(stringResource(id = R.string.email)) },
            placeholder = { Text(stringResource(id = R.string.enter_email)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (username.value.isEmpty()) {
                    Icon(Icons.Default.Person, contentDescription = "Person" )
                } else {
                    IconButton(
                        onClick = { username.value = "" }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear" )
                    }
                } },
        )
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(id = R.string.password)) },
            placeholder = { Text(stringResource(id = R.string.enter_password)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisibility.value = !passwordVisibility.value }
                ) {
                    Icon(passwordIcon, "Visibility")
                }
            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = { /* TODO */
                navController.navigate(AuthenticationScreens.MainScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.sign_in))
        }
        TextButton(onClick = { /*TODO*/
            navController.navigate(AuthenticationScreens.RegisterScreen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }}) {
            TitleText(text = stringResource(id = R.string.register), TextAlign.Center)
        }
    }
}

@Composable
fun RegisterScreen(navController: NavController) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    val passwordIcon = if (passwordVisibility.value)
        Icons.Default.Visibility
    else
        Icons.Default.VisibilityOff

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 60.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top ){
        Image(
            painter = painterResource(id = R.drawable.zbeg),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .size(200.dp)
        )
        TitleText("Create your account", TextAlign.Center)
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(stringResource(id = R.string.email)) },
            placeholder = { Text(stringResource(id = R.string.enter_email)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (username.value.isEmpty()) {
                    Icon(Icons.Default.Person, contentDescription = "Person" )
                } else {
                    IconButton(
                        onClick = { username.value = "" }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear" )
                    }
                } }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(id = R.string.password)) },
            placeholder = { Text(stringResource(id = R.string.enter_password)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisibility.value = !passwordVisibility.value }
                ) {
                    Icon(passwordIcon, "Visibility")
                }
            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = { /* TODO */
                navController.navigate(AuthenticationScreens.LogInScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.sign_up))
        }
    }
}

