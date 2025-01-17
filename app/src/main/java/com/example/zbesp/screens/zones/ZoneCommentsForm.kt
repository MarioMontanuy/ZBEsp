package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.domain.Comment
import com.example.zbesp.domain.GeofenceItem
import com.example.zbesp.domain.commentsDatabase
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.showDialog
import com.example.zbesp.screens.userEmail
import com.example.zbesp.ui.theme.CommentField
import com.example.zbesp.ui.theme.TitleText
import com.example.zbesp.ui.theme.TopBarTittle
import com.example.zbesp.ui.theme.getButtonColorsReversed
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneCommentsForm(zone: GeofenceItem, context: Context, navController: NavController) {
    var commentText by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.new_comment), navController) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.padding(30.dp))
                TitleText(
                    text = stringResource(id = R.string.leave_comment),
                    alignment = TextAlign.Justify,
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.padding(30.dp))
            }
            item {
                CommentField(
                    value = title,
                    onValueChanged = { title = it },
                    hintText = "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5))
                        .background(Color.White),
                    maxLines = 2,
                    trailingIcon = {
                        if (title.isNotEmpty()) {
                            IconButton(
                                onClick = { title = "" }
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
            }
            item {
                CommentField(
                    value = commentText,
                    onValueChanged = { commentText = it },
                    hintText = "Comment",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5))
                        .background(Color.White),
                    maxLines = 10,
                    trailingIcon = {
                        if (commentText.isNotEmpty()) {
                            IconButton(
                                onClick = { commentText = "" }
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        if (connectivityEnabled()) {
                            addCommentToDatabase(zone.name, title, commentText, context)
                            navController.popBackStack()
                        } else {
                            showDialog(context, context.getString(R.string.network_error))
                        }
                    },
                    colors = getButtonColorsReversed(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                ) {
                    TopBarTittle(stringResource(id = R.string.post), TextAlign.Justify)
                }
            }
        }
    }
}

private fun addCommentToDatabase(
    zoneName: String,
    title: String,
    commentText: String,
    context: Context
) {
    val comment = Comment(Date().hashCode(), title, commentText, userEmail, zoneName)
    commentsDatabase = Firebase.firestore.collection("comments")
    commentsDatabase.add(comment).addOnCompleteListener {
        if (it.isSuccessful) {
            Log.i("vehicleadded", "successful")
        } else {
            Log.i("vehicleadded", "error")
            showDialog(context = context, "Comment cloud not be created")
        }
    }
}