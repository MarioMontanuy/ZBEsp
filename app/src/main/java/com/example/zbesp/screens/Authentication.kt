package com.example.zbesp.screens

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.R
import com.example.zbesp.data.createIdOnDatabase
import com.example.zbesp.navigation.authentication.AuthenticationScreens
import com.example.zbesp.ui.theme.BigTitleText
import com.example.zbesp.ui.theme.MediumTitleText
import com.example.zbesp.ui.theme.SapphireBlueTransparent
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.ui.theme.errorColor
import com.example.zbesp.ui.theme.getOutlinedTextFieldColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


var userEmail = ""
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LogInScreen(navController: NavController, context: Context) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val passwordIcon = if (passwordVisibility.value)
        Icons.Default.Visibility
    else
        Icons.Default.VisibilityOff

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.padding(40.dp))
        Image(
            painter = painterResource(id = R.drawable.zbeg),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .size(200.dp)
        )
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it ; emailError.value = false },
            label = { Text(stringResource(id = R.string.email)) },
            placeholder = { Text(stringResource(id = R.string.enter_email)) },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError.value,
            trailingIcon = {
                if (email.value.isEmpty()) {
                    Icon(Icons.Default.Person, contentDescription = "Person")
                } else if (emailError.value) {
                    Icon(Icons.Default.Error, contentDescription = "Error", tint = errorColor)
                } else {
                    IconButton(
                        onClick = { email.value = "" }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            supportingText = {
                if (emailError.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Incorrect credentials",
                        color = MaterialTheme.colors.error
                    )
                }
            },
            colors = getOutlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it ; passwordError.value = false},
            label = { Text(stringResource(id = R.string.password)) },
            placeholder = { Text(stringResource(id = R.string.enter_password)) },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.value,
            trailingIcon = {
                if (passwordError.value) {
                    IconButton(
                        onClick = { passwordVisibility.value = !passwordVisibility.value }
                    ) {
                        Icon(passwordIcon, "Visibility", tint = errorColor)
                    }
                } else {
                    IconButton(
                        onClick = { passwordVisibility.value = !passwordVisibility.value }
                    ) {
                        Icon(passwordIcon, "Visibility")
                    }
                }

            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            supportingText = {
                if (passwordError.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Incorrect credentials",
                        color = MaterialTheme.colors.error
                    )
                }
            },
            colors = getOutlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = {
                emailError.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                passwordError.value = (password.value.length < 6)
                if (!emailError.value && !passwordError.value) {
                    val auth: FirebaseAuth = Firebase.auth
                    auth.signInWithEmailAndPassword(
                        email.value,
                        password.value
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            userEmail = email.value
                            goToApp(navController)
                        } else {
                            emailError.value = true
                            passwordError.value = true
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.sign_in))
        }
        TextButton(interactionSource = NoRippleInteractionSource(), onClick = {
            val auth: FirebaseAuth = Firebase.auth
            auth.signInAnonymously().addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("TestSignIn", "Successful")
                    goToApp(navController)
                } else {
                    Log.i("TestSignIn", "Error")
                    showDialog(context, "Anonymous user cannot be registered")
                }
            }
        }) {
            TitleText(text = "Sign in as Anonymous", TextAlign.Center)
        }
        TextButton(interactionSource = NoRippleInteractionSource(), onClick = {
            goToResetPassword(navController)
        }) {
            TitleText(text = "Forgot password?", TextAlign.Start)
        }
        Spacer(modifier = Modifier.padding(65.dp))
        Row {
            Text(modifier = Modifier.padding(vertical = 13.dp),text = "Don't have an account?", textAlign = TextAlign.Center)
            TextButton(interactionSource = NoRippleInteractionSource(),onClick = {
                navController.navigate(AuthenticationScreens.RegisterScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }) {
                TitleText(text = stringResource(id = R.string.register), TextAlign.Center)
            }
        }
        
    }
}
// TODO integrar restaurar contraseña
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, context: Context) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val rePasswordError = remember { mutableStateOf(false) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val rePassword = remember { mutableStateOf("") }
    val rePasswordVisibility = remember { mutableStateOf(false) }

    val passwordIcon = if (passwordVisibility.value)
        Icons.Default.Visibility
    else
        Icons.Default.VisibilityOff

    val rePasswordIcon = if (rePasswordVisibility.value)
        Icons.Default.Visibility
    else
        Icons.Default.VisibilityOff
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.zbeg),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .size(200.dp)
        )
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it ; emailError.value = false },
            label = { Text(stringResource(id = R.string.email)) },
            placeholder = { Text(stringResource(id = R.string.enter_email)) },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError.value,
            trailingIcon = {
                if (email.value.isEmpty()) {
                    Icon(Icons.Default.Person, contentDescription = "Person")
                } else if (emailError.value) {
                    Icon(Icons.Default.Error, contentDescription = "Error", tint = errorColor)
                } else {
                    IconButton(
                        onClick = { email.value = "" }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            supportingText = {
                if (emailError.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Invalid email",
                        color = MaterialTheme.colors.error
                    )
                }
            },
            colors = getOutlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it ; passwordError.value = false},
            label = { Text(stringResource(id = R.string.password)) },
            placeholder = { Text(stringResource(id = R.string.enter_password)) },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.value,
            trailingIcon = {
                if (passwordError.value) {
                    IconButton(
                        onClick = { passwordVisibility.value = !passwordVisibility.value }
                    ) {
                        Icon(passwordIcon, "Visibility", tint = errorColor)
                    }
                } else {
                    IconButton(
                        onClick = { passwordVisibility.value = !passwordVisibility.value }
                    ) {
                        Icon(passwordIcon, "Visibility")
                    }
                }

            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            supportingText = {
                if (passwordError.value && password.value.length < 6) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Invalid password, at least 6 characters",
                        color = MaterialTheme.colors.error
                    )
                } else if (passwordError.value && password.value != rePassword.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Passwords mismatch",
                        color = MaterialTheme.colors.error
                    )
                }
            },
            colors = getOutlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = rePassword.value,
            onValueChange = { rePassword.value = it ; rePasswordError.value = false},
            label = { Text(stringResource(id = R.string.confirm_password)) },
            placeholder = { Text(stringResource(id = R.string.enter_password)) },
            modifier = Modifier.fillMaxWidth(),
            isError = rePasswordError.value,
            trailingIcon = {
                if (rePasswordError.value) {
                    IconButton(
                        onClick = { rePasswordVisibility.value = !rePasswordVisibility.value }
                    ) {
                        Icon(rePasswordIcon, "Visibility", tint = errorColor)
                    }
                } else {
                    IconButton(
                        onClick = { rePasswordVisibility.value = !rePasswordVisibility.value }
                    ) {
                        Icon(rePasswordIcon, "Visibility")
                    }
                }

            },
            visualTransformation = if (rePasswordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            supportingText = {
                if (rePasswordError.value && rePassword.value.length < 6) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Invalid password, at least 6 characters",
                        color = MaterialTheme.colors.error
                    )
                } else if (passwordError.value && password.value != rePassword.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Passwords mismatch",
                        color = MaterialTheme.colors.error
                    )
                }
            },
            colors = getOutlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = {
                emailError.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                passwordError.value = password.value.length < 6
                rePasswordError.value = rePassword.value.length < 6
                if (password.value != rePassword.value) {
                    passwordError.value = true
                    rePasswordError.value = true
                }
                if (!emailError.value && !passwordError.value && !rePasswordError.value) {
                    val auth: FirebaseAuth = Firebase.auth
                    auth.createUserWithEmailAndPassword(
                        email.value,
                        password.value
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            goToLogIn(navController)
                        } else {
                            emailError.value = true
                            passwordError.value = true
                            rePasswordError.value = true
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.sign_up))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPassword(navController: NavController, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val email = remember { mutableStateOf("") }
        val emailError = remember { mutableStateOf(false) }
        Image(
            painter = painterResource(id = R.drawable.zbeg),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .size(200.dp)
        )
        Text("Enter your email address to change your password")
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it ; emailError.value = false },
            label = { Text(stringResource(id = R.string.email)) },
            placeholder = { Text(stringResource(id = R.string.enter_email)) },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError.value,
            trailingIcon = {
                if (email.value.isEmpty()) {
                    Icon(Icons.Default.Person, contentDescription = "Person")
                } else if (emailError.value) {
                    Icon(Icons.Default.Error, contentDescription = "Error", tint = errorColor)
                } else {
                    IconButton(
                        onClick = { email.value = "" }
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            supportingText = {
                if (emailError.value) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Invalid email",
                        color = MaterialTheme.colors.error
                    )
                }
            },
            colors = getOutlinedTextFieldColors()
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = {
                emailError.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                if (!emailError.value) {
                    val auth: FirebaseAuth = Firebase.auth
                    auth.sendPasswordResetEmail(email.value)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                goToLogIn(navController)
                            } else {
                                emailError.value = true
                                showDialog(context, "Error sending email" )
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.send))
        }
    }
}

fun showDialog(context: Context, text: String) {
    MaterialAlertDialogBuilder(context)
        .setTitle("Alert")
        .setMessage(text)
        .setPositiveButton("Accept", null)
        .show()
}

fun goToLogIn(navController: NavController) {
    navController.navigate(AuthenticationScreens.LogInScreen.route) {
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
    }
}

fun goToApp(navController: NavController) {
    navController.navigate(AuthenticationScreens.MainScreen.route) {
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
    }
}

fun goToResetPassword(navController: NavController) {
    navController.navigate(AuthenticationScreens.ResetPassword.route) {
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
    }
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}