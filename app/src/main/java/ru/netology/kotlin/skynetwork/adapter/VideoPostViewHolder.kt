package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post

const val VIDEO_PREVIEW_STUB_URL = "https://victor-mochere.com/wp-content/uploads/2019/08/How-to-download-a-video-on-YouTube.jpg"

class VideoPostViewHolder(view: View, listener: (Int) -> Unit) : BaseViewHolder(view, listener) {
    override fun bind(post: Post) {
        super.bind(post)

        val video = itemView.videoIv

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(VIDEO_PREVIEW_STUB_URL)
            .fitCenter()
            .into(video)

        video.isVisible = true
        video.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(post.video?.url)
            }
            itemView.context.startActivity(intent)
        }
    }
}
