package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post_card.view.*
import kotlinx.android.synthetic.main.repost_original_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post

class RepostViewHolder(view: View, listener: (Int) -> Unit) : BaseViewHolder(view, listener) {
    override fun bind(post: Post) {
        super.bind(post)
        val sourceCard = itemView.sourceCard
        sourceCard.isVisible = true

        post.source?.let {
            with(sourceCard) {
                sourceDateTv.text = it.created
                sourceAuthorTv.text = it.author
                sourceContentTV.text = it.content

                it.advertising?.let {
                    sourceAdsTv.isVisible = true
                    setOriginalPostPicture(this, it.adsUrl, it.imageUrl)
                }

                it.video?.let {
                    sourcePictureIv.isVisible = true
                    setOriginalPostPicture(this, it.url)
                }
            }
        }
    }

    private fun setOriginalPostPicture(
        sourceCard: View,
        linkUrl: String,
        imageUrl: String = VIDEO_PREVIEW_STUB_URL
    ) {
        val pictureView = sourceCard.sourcePictureIv
        pictureView.isVisible = true
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .fitCenter()
            .into(pictureView)

        pictureView.isVisible = true
        pictureView.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(linkUrl)
            }
            itemView.context.startActivity(intent)
        }
    }
}
