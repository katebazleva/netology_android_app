package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.SimplePost

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<SimplePost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_card,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.bind(items[position])
        }
    }

    fun submitPosts(postsList: List<SimplePost>) {
        items = postsList
    }

    private class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.dateTv
        val author = itemView.authorTv
        val content = itemView.contentTV
        val likesCount = itemView.likeCountTv
        val commentsCount = itemView.commentsCountTv
        val shareCount = itemView.shareCountTv
        val video = itemView.videoIv

        fun bind(post: SimplePost) {
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(itemView.videoIv)
                .into(video)

            date.text = post.created.toString()
            author.text = post.author
            content.text = post.content

            content.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))

            likesCount.text = if (post.likesCount > 0) post.likesCount.toString() else null
            commentsCount.text = if (post.commentsCount > 0) post.commentsCount.toString() else null
            shareCount.text = if (post.shareCount > 0) post.shareCount.toString() else null

            if (post.likedByMe) {
                setPostLiked()
            }

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

                post.sharedByMe = !post.sharedByMe
                if (post.sharedByMe) post.shareCount += 1 else post.shareCount -= 1
                shareCount.text = if (post.shareCount > 0) post.shareCount.toString() else null

                if (post.sharedByMe) setPostShared() else setPostNotShared()
            }
        }

        private fun setPostLiked() {
            likesCount.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_active_24dp,
                0,
                0,
                0
            )
            likesCount.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
        }


        private fun setPostShared() {
            shareCount.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_share_active_24dp,
                0,
                0,
                0
            )
            shareCount.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
        }

        private fun setPostNotLiked() {
            likesCount.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_favorite_inactive_24dp,
                0,
                0,
                0
            )
            likesCount.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
        }

        private fun setPostNotShared() {
            shareCount.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_share_inactive_24dp,
                0,
                0,
                0
            )
            shareCount.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
        }
    }
}