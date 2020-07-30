package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseViewHolder(view: View, val listener: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    open fun bind(post: Post) {
        val date = itemView.dateTv
        val author = itemView.authorTv
        val content = itemView.contentTV
        val likesCount = itemView.likeCountTv
        val commentsCount = itemView.commentsCountTv
        val shareCount = itemView.shareCountTv
        val closeBtn = itemView.closeBtn

        val simpleDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        date.text = simpleDate.format(post.created).toString()
        author.text = post.author
        content.text = post.content

        closeBtn.setOnClickListener {
            post.isHidden = true
            listener(adapterPosition)
        }

        likesCount.text = if (post.likesCount > 0) post.likesCount.toString() else null
        commentsCount.text = if (post.commentsCount > 0) post.commentsCount.toString() else null
        shareCount.text = if (post.shareCount > 0) post.shareCount.toString() else null

        if (post.likedByMe) setPostLiked()

        if (post.commentedByMe) {
            commentsCount.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_comment_active_24dp,
                0,
                0,
                0
            )
            commentsCount.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
        }

        if (post.sharedByMe) setPostShared()

        likesCount.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) post.likesCount += 1 else post.likesCount -= 1
            likesCount.text = if (post.likesCount > 0) post.likesCount.toString() else null

            if (post.likedByMe) setPostLiked() else setPostNotLiked()
        }

        shareCount.setOnClickListener {
            post.sharedByMe = !post.sharedByMe
            if (post.sharedByMe) {
                post.shareCount += 1
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, """
                                ${post.author} (${post.created})
    
                                ${post.content}
                            """.trimIndent()
                    )
                    type = "text/plain"
                }
                itemView.context.startActivity(intent)
            } else {
                post.shareCount -= 1
            }
            shareCount.text = if (post.shareCount > 0) post.shareCount.toString() else null

            if (post.sharedByMe) setPostShared() else setPostNotShared()
        }
    }

    private fun setPostLiked() {
        itemView.likeCountTv.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_favorite_active_24dp,
            0,
            0,
            0
        )
        itemView.likeCountTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
    }

    private fun setPostNotLiked() {
        itemView.likeCountTv.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_favorite_inactive_24dp,
            0,
            0,
            0
        )
        itemView.likeCountTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
    }

    private fun setPostShared() {
        itemView.shareCountTv.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_share_active_24dp,
            0,
            0,
            0
        )
        itemView.shareCountTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
    }

    private fun setPostNotShared() {
        itemView.shareCountTv.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_share_inactive_24dp,
            0,
            0,
            0
        )
        itemView.shareCountTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
    }
}
