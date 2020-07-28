package ru.netology.kotlin.skynetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post
import ru.netology.kotlin.skynetwork.data.PostType

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.SIMPLE_POST -> SimplePostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_card,
                    parent,
                    false
                )
            )
            ViewType.EVENT_POST -> EventPostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_card,
                    parent,
                    false
                )
            )
            ViewType.VIDEO_POST -> VideoPostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_card,
                    parent,
                    false
                )
            )
            ViewType.ADVERTISING_POST -> AdsPostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_card,
                    parent,
                    false
                )
            )
            ViewType.REPOST -> RepostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_card,
                    parent,
                    false
                )
            )
            else -> throw Exception("ViewHolder with type $viewType not exist")
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SimplePostViewHolder -> holder.bind(items[position])
            is EventPostViewHolder -> holder.bind(items[position])
            is VideoPostViewHolder -> holder.bind(items[position])
            is AdsPostViewHolder -> holder.bind(items[position])
            is RepostViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int = when (items[position].postType) {
        PostType.VIDEO_POST -> ViewType.VIDEO_POST
        PostType.EVENT_POST -> ViewType.EVENT_POST
        PostType.ADVERTISING -> ViewType.ADVERTISING_POST
        PostType.REPOST -> ViewType.REPOST
        else -> ViewType.SIMPLE_POST
    }

    fun submitPosts(postsList: List<Post>) {
        items = postsList
    }

    private object ViewType {
        const val SIMPLE_POST = 0
        const val EVENT_POST = 1
        const val VIDEO_POST = 2
        const val ADVERTISING_POST = 3
        const val REPOST = 4
    }
}