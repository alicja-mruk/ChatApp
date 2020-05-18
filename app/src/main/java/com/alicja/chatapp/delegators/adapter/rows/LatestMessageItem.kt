package com.alicja.chatapp.delegators.adapter.rows

import com.alicja.chatapp.R
import com.alicja.chatapp.data.chat.ChatProperties
import com.alicja.chatapp.model.ChatMessage
import com.alicja.chatapp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*
import java.util.*


class LatestMessageItem(private val chatMessage : ChatMessage): Item<ViewHolder>(){
    var userTo : User? = null

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.messageLatestMessage.text = chatMessage.text

        val userToId: String = if (chatMessage.fromId == ChatProperties.getCurrentUserId()) chatMessage.toId else chatMessage.fromId

        val ref = FirebaseDatabase.getInstance().getReference("/users/$userToId")
        ref.addListenerForSingleValueEvent(object  : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot) {
                userTo = p0.getValue(User::class.java)
                     if (userTo != null) {
                        viewHolder.itemView.usernameLatestMessage.text = userTo!!.username
                        Picasso.get().load(userTo!!.photoUrl).into(viewHolder.itemView.imageLatestMessage)

                         val stamp = chatMessage.timestamp.toLong()
                         val date = getDateTimeFromEpocLongOfSeconds(stamp)
                         viewHolder.itemView.timestamp.text = date
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getDateTimeFromEpocLongOfSeconds(epoc: Long): String? {
        return try {
            val netDate = Date(epoc *1000)
            netDate.toString()
        } catch (e: Exception) {
            e.toString()
        }
    }
}