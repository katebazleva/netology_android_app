package ru.netology.kotlin.skynetwork.data

import java.util.*

data class EventPost(
    val id: Int,
    val author: String,
    val content: String,
    val created: Date,
    var likesCount: Int = 0,
    var commentsCount: Int = 0,
    var shareCount: Int = 0,
    var likedByMe: Boolean = false,
    var commentedByMe: Boolean = false,
    var sharedByMe: Boolean = false,
    var address: String,
    var location: Location,
    val postType: PostType = PostType.EVENT_POST
) : PostInterface
