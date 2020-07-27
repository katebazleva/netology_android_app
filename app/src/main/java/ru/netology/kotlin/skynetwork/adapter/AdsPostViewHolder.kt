package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post

class AdsPostViewHolder(view: View) : BaseViewHolder(view) {
    override fun bind(post: Post) {
        super.bind(post)

        val video = itemView.videoIv
        val adsTextView = itemView.adsTv

        adsTextView.visibility = View.VISIBLE

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(post.advertising?.imageUrl)
            .fitCenter()
            .into(video)

        video.visibility = View.VISIBLE
        video.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(post.advertising?.adsUrl)
            }
            itemView.context.startActivity(intent)
        }
    }
}
