package com.alicja.chatapp.delegators.adapter.rows

import com.alicja.chatapp.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatReceivedItem : Item<ViewHolder> () {

    override fun getLayout(): Int {
        return R.layout.chat_received_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}