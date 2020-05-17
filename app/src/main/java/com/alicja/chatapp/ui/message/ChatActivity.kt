package com.alicja.chatapp.ui.message

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alicja.chatapp.R
import com.alicja.chatapp.data.chat.ChatProperties
import com.alicja.chatapp.delegators.adapter.rows.ChatReceivedItem
import com.alicja.chatapp.delegators.adapter.rows.ChatSendItem
import com.alicja.chatapp.model.ChatMessage
import com.alicja.chatapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    companion object{
        const val TAG = "ChatActivity"
    }

    var currentUserPhotoUrl: String = ""
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setCurrentUserPhotoUrl()

        val userTo  = intent.getParcelableExtra<User>(NewMessagesActivity.USER_KEY)
        recyclerView_chat.adapter  = adapter

        setTitle(userTo)
        listenForMessages(userTo)

        sendMessageBtn.setOnClickListener{
            Log.d(TAG, "Sending message")
            sendMessage(userTo)
        }
    }

    private fun setTitle(userTo: User?){
        if(userTo!=null) {
            title = userTo.username
        }
    }

    private fun sendMessage(userTo: User?) {
        val messageText = enterMessageEditText.text.toString()
        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()

        if(userTo?.uid == null) return

        val chatMessage = ChatMessage(reference.key!!, messageText, ChatProperties.getCurrentUserId()!!,
            userTo.uid, ChatProperties.getTimeStamp())
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully saved a message to the database")
                enterMessageEditText.text.clear()
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed while saving message")
            }
    }

    private fun listenForMessages(userTo: User?) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("/messages")

        reference.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null && userTo != null) {

                    Log.d(TAG, "New message added")
                    if (chatMessage.fromId == ChatProperties.getCurrentUserId() && chatMessage.toId == userTo.uid) {
                        adapter.add(ChatSendItem(chatMessage.text, currentUserPhotoUrl))
                    }
                }
                else {
                    adapter.add(ChatReceivedItem(chatMessage!!.text, userTo!!.photoUrl))
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
        })
    }

    fun setCurrentUserPhotoUrl(){
        val reference : DatabaseReference = FirebaseDatabase.getInstance().getReference("/users")
            .child(FirebaseAuth.getInstance().uid!!)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                Log.d(ChatActivity.TAG, "Successfully retrieved current photo url")
                val user : User? = p0.getValue(User::class.java)

                if (user != null) {
                    currentUserPhotoUrl = user.photoUrl.toString()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Database error while retrieving current photo url")
            }
        })
    }

}
