package com.alicja.chatapp.ui.message

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alicja.chatapp.R
import com.alicja.chatapp.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setTitle()


        val adapter = GroupAdapter<ViewHolder>()
        recyclerView_chat.adapter  = adapter
    }
    private fun setTitle(){
        val user  = intent.getParcelableExtra<User>(NewMessagesActivity.USER_KEY)
        if(user!=null) {
            title = user.username
        }
    }
}
