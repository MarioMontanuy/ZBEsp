package com.example.zbesp.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.example.zbesp.R

@Immutable
data class Post(
    val id: Long,
    val title: String,
    val subtitle: String? = null,
    val url: String,
    val metadata: Metadata,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int,
    val tags: Set<String>
)

@Immutable
data class Metadata(
    val author: PostAuthor,
    val date: String,
    val readTimeMinutes: Int
)

@Immutable
data class PostAuthor(
    val name: String,
    val url: String? = null
)


private val pietro = PostAuthor("Pietro Maggi", "https://medium.com/@pmaggi")

private val post1 = Post(
    id = 1L,
    title = "A Little Thing about Android Module Paths",
    subtitle = "How to configure your module paths, instead of using Gradle’s default.",
    url = "https://medium.com/androiddevelopers/gradle-path-configuration-dc523f0ed25c",
    metadata = Metadata(
        author = pietro,
        date = "August 02",
        readTimeMinutes = 1
    ),
    imageId = R.drawable.ic_launcher_background,
    imageThumbId = R.drawable.ic_launcher_background,
    tags = setOf("Modularization", "Gradle")
)

private val post2 = Post(
    id = 1L,
    title = "A Little Thing about Android Module Paths",
    subtitle = "How to configure your module paths, instead of using Gradle’s default.",
    url = "https://medium.com/androiddevelopers/gradle-path-configuration-dc523f0ed25c",
    metadata = Metadata(
        author = pietro,
        date = "August 02",
        readTimeMinutes = 1
    ),
    imageId = R.drawable.ic_launcher_background,
    imageThumbId = R.drawable.ic_launcher_background,
    tags = setOf("Modularization", "Gradle")
)
object PostRepo {
    fun getPosts(): List<Post> = posts
    fun getFeaturedPost(): Post = posts.random()
}

private val posts = listOf(
    post1,
    post2,
    post1.copy(id = 6L),
    post1.copy(id = 7L),
    post1.copy(id = 8L),
    post1.copy(id = 9L),
    post1.copy(id = 10L),
    post1.copy(id = 11L),
    post1.copy(id = 12L),
)