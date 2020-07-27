package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post

class RepostViewHolder(view: View) : BaseViewHolder(view) {
    override fun bind(post: Post) {
        super.bind(post)

        val video = itemView.videoIv

    }
}
