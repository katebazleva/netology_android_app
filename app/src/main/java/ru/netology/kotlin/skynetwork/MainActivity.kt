package ru.netology.kotlin.skynetwork

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.kotlin.skynetwork.data.Event
import ru.netology.kotlin.skynetwork.data.Post
import ru.netology.kotlin.skynetwork.data.Video
import ru.netology.kotlin.skynetwork.data.VideoPost
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createdTime = Calendar.getInstance()
        createdTime.set(2020, Calendar.JULY, 20, 9, 35, 0)

        val post: Post = VideoPost(
            1,
            "kate bazleva",
            "Something very interesting",
            createdTime.time,
            likesCount = 1,
            shareCount = 2,
            likedByMe = true,
            sharedByMe = true,
            video = Video("https://www.youtube.com/watch?v=WhWc3b3KhnY")
        )

        dateTv.text = getPostedAgoHumanReadable((System.currentTimeMillis() - createdTime.timeInMillis)/1000)

        authorTv.text = post.author

        contentTV.text = post.content
        contentTV.setTextColor(ContextCompat.getColor(this, R.color.black))

        if (post is VideoPost) {
            videoIv.visibility = View.VISIBLE

            videoIv.setOnClickListener {
                startActivity(Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.video.url)
                })
            }
        }

        if (post is Event) {
            locationTv.visibility = View.VISIBLE
            locationTv.text = post.address

            locationTv.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("geo:${post.location.lat},${post.location.lng}")
                }
                startActivity(intent)
            }
        }

        likeCountTv.text = if (post.likesCount >  0) post.likesCount.toString() else null
        commentsCountTv.text = if (post.commentsCount >  0) post.commentsCount.toString() else null
        shareCountTv.text = if (post.shareCount >  0) post.shareCount.toString() else null

        if (post.likedByMe) {
            likeCountTv.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_active_24dp,
                0,
                0,
                0
            )
            likeCountTv.setTextColor(ContextCompat.getColor(this, R.color.red))
        }

        if (post.commentedByMe) {
            commentsCountTv.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_comment_active_24dp,
                0,
                0,
                0
            )
            commentsCountTv.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }

        if (post.sharedByMe) {
            shareCountTv.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_share_active_24dp,
                0,
                0,
                0
            )
            shareCountTv.setTextColor(ContextCompat.getColor(this, R.color.green))
        }

        likeBtn.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) post.likesCount += 1 else post.likesCount -= 1
            likeCountTv.text = if (post.likesCount >  0) post.likesCount.toString() else null

            if (post.likedByMe) {
                likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
                likeCountTv.setTextColor(resources.getColor(R.color.red))
            } else {
                likeBtn.setImageResource(R.drawable.ic_favorite_inactive_24dp)
                likeCountTv.setTextColor(resources.getColor(R.color.gray))
            }
        }
    }
}
