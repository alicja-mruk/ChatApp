package com.alicja.chatapp.ui.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.alicja.chatapp.R
import com.alicja.chatapp.data.chat.ChatProperties
import com.alicja.chatapp.delegators.StartActivityHelper
import com.alicja.chatapp.delegators.adapter.rows.LatestMessageItem
import com.alicja.chatapp.delegators.adapter.rows.UserItem
import com.alicja.chatapp.model.ChatMessage
import com.alicja.chatapp.model.User
import com.alicja.chatapp.ui.message.NewMessagesActivity.Companion.USER_KEY
import com.alicja.chatapp.ui.registerlogin.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_latest_messages.*

class LatestMessagesActivity : AppCompatActivity() {

    companion object{
        const val TAG = "LatestMessagesActivity"
        var currentUser : User? = null
    }

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)
        title = "Chats"

        recyclerView_latestMessages.adapter = adapter
        recyclerView_latestMessages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapterOnItemClickListener(adapter)

        listenForLatestMessages()
        isUserLoggedIn()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_new_message->{
                StartActivityHelper(this, NewMessagesActivity::class.java).startNewActivity()
            }

            R.id.menu_sign_out->{
                signOut()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun isUserLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid

        if (uid == null){
            StartActivityHelper(this, LoginActivity::class.java).startActivityWithClearTaskFlag()
        }
    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        StartActivityHelper(this, LoginActivity::class.java).startActivityWithClearTaskFlag()
    }

    val latestMessagesMap = HashMap<String, ChatMessage>()

    private fun listenForLatestMessages(){
        val fromId = ChatProperties.getCurrentUserId()
        val ref  = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object : ChildEventListener{

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                //notify everytime new message incoming
                val chatMessage: ChatMessage = p0.getValue(ChatMessage::class.java) ?:return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                //change latest message text
                val chatMessage: ChatMessage = p0.getValue(ChatMessage::class.java) ?:return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}

        })
    }

    private fun refreshRecyclerViewMessages(){
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageItem(it))
        }
    }

    private fun adapterOnItemClickListener(adapter :GroupAdapter<ViewHolder>){
        adapter.setOnItemClickListener{item, view->
            val intent = Intent(this, ChatActivity::class.java)
            val row = item as LatestMessageItem
            intent.putExtra(USER_KEY, row.userTo)
            startActivity(intent)
        }

    }

}
