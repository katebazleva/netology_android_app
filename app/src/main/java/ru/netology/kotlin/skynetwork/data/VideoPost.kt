package ru.netology.kotlin.skynetwork.data

import java.util.*

data class VideoPost(
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
    val video: Video,
    val postType: PostType = PostType.VIDEO_POST
) : PostInterface
