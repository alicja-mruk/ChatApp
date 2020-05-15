package com.alicja.chatapp.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alicja.chatapp.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        title="Chat"

        val adapter = GroupAdapter<ViewHolder>()
        recyclerView_chat.adapter  = adapter
    }
}
