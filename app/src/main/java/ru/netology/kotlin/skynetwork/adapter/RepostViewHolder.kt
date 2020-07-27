package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post_card.view.*
import kotlinx.android.synthetic.main.repost_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post
import java.text.SimpleDateFormat
import java.util.*

class RepostViewHolder(view: View) : BaseViewHolder(view) {
    override fun bind(post: Post) {
        val date = itemView.postDateTv
        val author = itemView.postAuthorTv
        val content = itemView.postContentTV
        val likesCount = itemView.postLikeCountTv
        val commentsCount = itemView.postCommentsCountTv
        val shareCount = itemView.postShareCountTv

        val simpleDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        date.text = simpleDate.format(post.created).toString()
        author.text = post.author
        content.text = post.content

        likesCount.text = if (post.likesCount > 0) post.likesCount.toString() else null
        commentsCount.text = if (post.commentsCount > 0) post.commentsCount.toString() else null
        shareCount.text = if (post.shareCount > 0) post.shareCount.toString() else null

        if (post.likedByMe) likesCount.setPostLiked()

        if (post.commentedByMe) {
            commentsCount.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_comment_active_24dp,
                0,
                0,
                0
            )
            commentsCount.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
        }

        if (post.sharedByMe) shareCount.setPostShared()

        likesCount.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) post.likesCount += 1 else post.likesCount -= 1
            likesCount.text = if (post.likesCount > 0) post.likesCount.toString() else null

            if (post.likedByMe) likesCount.setPostLiked() else likesCount.setPostNotLiked()
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

            if (post.sharedByMe) shareCount.setPostShared() else shareCount.setPostNotShared()
        }

        val sourceCard = itemView.sourceCard

        post.source?.let {
            with(sourceCard) {
                val sourceDate = dateTv
                val sourceAuthor = authorTv
                val sourceContent = contentTV

                val sourceSimpleDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                sourceDate.text = sourceSimpleDate.format(it.created).toString()
                sourceAuthor.text = it.author
                sourceContent.text = it.content

                closeBtn.visibility = View.GONE
                likeCountTv.visibility = View.GONE
                commentsCountTv.visibility = View.GONE
                shareCountTv.visibility = View.GONE

                // TODO можно сделать также для видео-постов

                it.advertising?.let {
                    val video = videoIv
                    val adsTextView = adsTv

                    adsTextView.visibility = View.VISIBLE

                    val requestOptions = RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)

                    Glide.with(itemView.context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(it.imageUrl)
                        .fitCenter()
                        .into(video)

                    video.visibility = View.VISIBLE
                    video.setOnClickListener {
                        val intent = Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse(post.source.advertising?.adsUrl)
                        }
                        itemView.context.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun TextView.setPostLiked() {
        setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_favorite_active_24dp,
            0,
            0,
            0
        )
        setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
    }

    private fun TextView.setPostNotLiked() {
        setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_favorite_inactive_24dp,
            0,
            0,
            0
        )
        setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
    }

    private fun TextView.setPostShared() {
        setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_share_active_24dp,
            0,
            0,
            0
        )
        setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
    }

    private fun TextView.setPostNotShared() {
        setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_share_inactive_24dp,
            0,
            0,
            0
        )
        setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
    }
}
