package ru.netology.kotlin.skynetwork.data

import java.util.*

class Event(
    id: Int,
    author: String,
    content: String,
    created: Date,
    likesCount: Int = 0,
    commentsCount: Int = 0,
    shareCount: Int = 0,
    likedByMe: Boolean = false,
    commentedByMe: Boolean = false,
    sharedByMe: Boolean = false,
    var address: String,
    var location: Location
) : Post(
    id,
    author,
    content,
    created,
    likesCount,
    commentsCount,
    shareCount,
    likedByMe,
    commentedByMe,
    sharedByMe
) {
}
