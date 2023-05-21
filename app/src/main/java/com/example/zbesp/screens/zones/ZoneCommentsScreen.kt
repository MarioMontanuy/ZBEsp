package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.domain.Comment
import com.example.zbesp.domain.GeofenceItem
import com.example.zbesp.navigation.vehicles.VehiclesScreens
import com.example.zbesp.navigation.zones.ZonesScreens
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.vehicles.CommentsFloatingActionButton
import com.example.zbesp.ui.theme.OwnerTitle
import com.example.zbesp.ui.theme.SubtitleText
import com.example.zbesp.ui.theme.TitleText

var comments = mutableStateOf(listOf<Comment>())

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneCommentsScreen(zone: GeofenceItem, navController: NavController) {
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.comments), navController) }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val zoneComments = comments.value.filter { it.zoneName == zone.name }
            if (zoneComments.isEmpty() || !connectivityEnabled()) {
                item {
                    Spacer(modifier = Modifier.padding(30.dp))
                    SubtitleText(
                        text = stringResource(id = R.string.no_comments) + "\nor\n" +
                                stringResource(id = R.string.network_error),
                        alignment = TextAlign.Center,
                        MaterialTheme.typography.body1
                    )
                }
            } else {
                zoneComments.groupBy { it.owner }.map {
//                    var title = true
                    items(it.value) { comment ->
//                        if (title) {
//                            title = false
//                            OwnerTitle(it.value.first().owner)
//                        }
                        PostComment(comment = comment, navController)
                        Divider(startIndent = 50.dp)
                    }
                }
            }
        }
        CommentsFloatingActionButton(zone = zone, navController = navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostComment(
    comment: Comment,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .clickable {
                navController.navigate(ZonesScreens.ZoneCommentsDetail.withArgs(comment.id.toString()))
            },
        text = {
            TitleText(text = comment.owner, alignment = TextAlign.Justify)
        },
        secondaryText = {
            SubtitleText(comment.title, TextAlign.Justify)
        }
    )
}