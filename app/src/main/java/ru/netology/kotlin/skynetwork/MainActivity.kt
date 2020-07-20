package ru.netology.kotlin.skynetwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createdTime = Calendar.getInstance()
        createdTime.set(2020, Calendar.JULY, 20, 9, 35, 0)

        val post = Post(
            1,
            "kate bazleva",
            "Something very interesting",
            createdTime.time,
            likesCount = 10,
            shareCount = 2,
            sharedByMe = true
        )

        dateTv.text = getPostedAgoHumanReadable((System.currentTimeMillis() - createdTime.timeInMillis)/1000)

        authorTv.text = post.author

        contentTV.text = post.content
        contentTV.setTextColor(resources.getColor(R.color.black))

        likeCountTv.text = if (post.likesCount >  0) post.likesCount.toString() else null
        commentsCountTv.text = if (post.commentsCount >  0) post.commentsCount.toString() else null
        shareCountTv.text = if (post.shareCount >  0) post.shareCount.toString() else null

        if (post.likedByMe) {
            likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
            likeCountTv.setTextColor(resources.getColor(R.color.red))
        }

        if (post.commentedByMe) {
            commentsBtn.setImageResource(R.drawable.ic_comment_active_24dp)
            commentsCountTv.setTextColor(resources.getColor(R.color.blue))
        }

        if (post.sharedByMe) {
            shareBtn.setImageResource(R.drawable.ic_share_active_24dp)
            shareCountTv.setTextColor(resources.getColor(R.color.green))
        }
    }
}
