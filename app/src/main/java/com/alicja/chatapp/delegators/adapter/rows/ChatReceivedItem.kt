package com.alicja.chatapp.delegators.adapter.rows

import com.alicja.chatapp.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_received_row.view.*

class ChatReceivedItem (val text : String, val photoUrl : String?): Item<ViewHolder> () {

    override fun getLayout(): Int {
        return R.layout.chat_received_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewReceived.text = text
        if (photoUrl != null && photoUrl.isNotEmpty()) {
            Picasso.get().load(photoUrl).into(viewHolder.itemView.imageViewReceived)
        }
    }
}