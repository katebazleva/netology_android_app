package ru.netology.kotlin.skynetwork.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import kotlinx.android.synthetic.main.post_card.view.*
import ru.netology.kotlin.skynetwork.data.Post

class EventPostViewHolder(view: View, listener: (Int) -> Unit) : BaseViewHolder(view, listener) {
    override fun bind(post: Post) {
        super.bind(post)

        val address = itemView.locationTv

        address.visibility = View.VISIBLE
        address.text = post.address
        address.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("geo:${post.location?.lat},${post.location?.lng}")
            }
            itemView.context.startActivity(intent)
        }
    }
}
