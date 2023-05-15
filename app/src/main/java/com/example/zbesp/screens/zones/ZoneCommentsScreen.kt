package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.zbesp.R
import com.example.zbesp.data.Comment
import com.example.zbesp.data.GeofenceItem
import com.example.zbesp.data.Vehicle
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.screens.VehiclesTopBar
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.vehicles.CommentsFloatingActionButton
import com.example.zbesp.screens.vehicles.PostItem
import com.example.zbesp.screens.vehicles.VehiclesFloatingActionButton
import com.example.zbesp.screens.vehicles.communityVehicles
import com.example.zbesp.screens.vehicles.vehicles
import com.example.zbesp.ui.theme.OwnerTitle
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.ui.theme.TitleText
import okhttp3.internal.wait

var comments = mutableStateOf(listOf<Comment>())

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneCommentsScreen(zone: GeofenceItem, context: Context, navController: NavController) {
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.comments), navController) }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val zoneComments = comments.value.filter { it.zoneName == zone.name }
            if (zoneComments.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.padding(30.dp))
                    SubtitleText(
                        text = stringResource(id = R.string.no_comments),
                        alignment = TextAlign.Center,
                        MaterialTheme.typography.body1
                    )
                }
            } else {
                zoneComments.groupBy { it.owner }.map {
                    var title = true
                    items(it.value) { comment ->
                        if (title) {
                            title = false
                            OwnerTitle(it.value.first().owner)
                        }
                        PostComment(comment = comment, navController = navController)
                        Divider(startIndent = 50.dp)

                    }
                }
                //vehicles.value.groupBy { it.owner }.map { Log.i("vehiclesGrouped", "it.value" + it.value) }

            }

        }
        CommentsFloatingActionButton(zone = zone, navController = navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostComment(
    comment: Comment,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ListItem(
        modifier = modifier,
        text = {
            TitleText(text = comment.title, alignment = TextAlign.Justify)
        },
        secondaryText = {
                SubtitleText(comment.commentText, TextAlign.Justify)
        }
    )
}