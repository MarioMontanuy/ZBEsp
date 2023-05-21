package com.example.zbesp.screens.zones

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zbesp.R
import com.example.zbesp.domain.Comment
import com.example.zbesp.domain.commentsDatabase
import com.example.zbesp.domain.vehiclesDatabase
import com.example.zbesp.screens.ZBEspTopBar
import com.example.zbesp.screens.userEmail
import com.example.zbesp.screens.vehicles.AddTextRow
import com.example.zbesp.ui.theme.TopBarTittle
import com.example.zbesp.ui.theme.getButtonColorsReversed

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZoneCommentsDetail(comment: Comment, navController: NavController) {
    Scaffold(topBar = { ZBEspTopBar(stringResource(id = R.string.comment_detail_screen_title), navController) }) {
        LazyColumn {

            item {
                AddTextRow(
                    title = "Title",
                    subtitle = comment.title
                )
            }
            item {
                AddTextRow(
                    title = "Text",
                    subtitle = comment.commentText
                )
            }
            item {
                AddTextRow(
                    title = "Owner",
                    subtitle = comment.owner
                )
            }
            item {
                if (comment.owner == userEmail) {
                    Button(
                        onClick = {
                            deleteComment(comment)
                            navController.popBackStack()
                        },
                        colors = getButtonColorsReversed(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    ) {
                        TopBarTittle(
                            stringResource(id = R.string.delete_comment),
                            TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}

private fun deleteComment(comment: Comment) {
    val commentToDelete = commentsDatabase.whereEqualTo("id", comment.id)
    commentToDelete.get().addOnSuccessListener { it.forEach { value ->
        commentsDatabase.document(value.id).delete()
    } }
}