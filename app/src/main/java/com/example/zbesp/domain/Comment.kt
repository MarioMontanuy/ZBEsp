package com.example.zbesp.domain

import androidx.compose.runtime.Immutable
import com.example.zbesp.screens.vehicles.vehicles
import com.example.zbesp.screens.zones.comments

@Immutable
data class Comment(
    val id: Int,
    val title: String,
    val commentText: String,
    var owner: String,
    var zoneName: String
) {
    constructor() : this(0, "", "", "", "")
}

fun getComment(commentId: String): Comment {
    return try {
        comments.value.first { it.id == commentId.toInt() }
    } catch (e: NoSuchElementException) {
        Comment()
    }
}