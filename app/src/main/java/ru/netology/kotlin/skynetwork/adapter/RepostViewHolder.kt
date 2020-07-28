package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.post_card.view.*
import kotlinx.android.synthetic.main.repost_original_card.view.*
import ru.netology.kotlin.skynetwork.R
import ru.netology.kotlin.skynetwork.data.Post
import java.text.SimpleDateFormat
import java.util.*

class RepostViewHolder(view: View) : BaseViewHolder(view) {
    override fun bind(post: Post) {
        super.bind(post)
        val sourceCard = itemView.sourceCard
        sourceCard.visibility = View.VISIBLE

        post.source?.let {
            with(sourceCard) {
                val sourceSimpleDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                sourceDateTv.text = sourceSimpleDate.format(it.created).toString()
                sourceAuthorTv.text = it.author
                sourceContentTV.text = it.content

                it.advertising?.let {
                    sourceAdsTv.visibility = View.VISIBLE
                    setOriginalPostPicture(this, it.adsUrl, it.imageUrl)
                }

                it.video?.let {
                    sourcePictureIv.visibility = View.VISIBLE
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
        pictureView.visibility = View.VISIBLE
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .fitCenter()
            .into(pictureView)

        pictureView.visibility = View.VISIBLE
        pictureView.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(linkUrl)
            }
            itemView.context.startActivity(intent)
        }
    }
}
