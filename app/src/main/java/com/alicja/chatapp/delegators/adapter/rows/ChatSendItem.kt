package com.alicja.chatapp.delegators.adapter.rows

import com.alicja.chatapp.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_send_row.view.*

class ChatSendItem (val text : String, val  photoUrl : String?) : Item<ViewHolder> () {

    override fun getLayout(): Int {
        return R.layout.chat_send_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
       viewHolder.itemView.textViewSend.text = text
        if (photoUrl != null && photoUrl.isNotEmpty()) {
                Picasso.get().load(photoUrl).into(viewHolder.itemView.imageViewSend)
        }

    }
}