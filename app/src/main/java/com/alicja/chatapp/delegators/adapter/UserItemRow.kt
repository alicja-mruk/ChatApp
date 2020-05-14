package com.alicja.chatapp.delegators.adapter

import com.alicja.chatapp.R
import com.alicja.chatapp.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_messages_row.view.*

class UserItemRow (private val user: User): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.activity_new_messages_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.usernameTextViewNewMessageActivity.text = user.username
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.photoNewMessageActivity);
    }

}