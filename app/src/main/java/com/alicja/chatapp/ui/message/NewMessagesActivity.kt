package com.alicja.chatapp.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alicja.chatapp.R
import com.alicja.chatapp.delegators.StartActivityHelper
import com.alicja.chatapp.delegators.adapter.rows.UserItem
import com.alicja.chatapp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_messages.*

class NewMessagesActivity : AppCompatActivity() {
    private val ref = FirebaseDatabase.getInstance().getReference("/users")

    companion object{
        const val TAG = "NewMessagesActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_messages)
        title = "Select user"

        fetch()

    }

    private fun adapterOnItemClickListener(adapter :GroupAdapter<ViewHolder>){
        adapter.setOnItemClickListener{item, view->
           startChatActivity()
        }
    }

    private fun fetch() {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d(TAG, "Fetching users from the database:  $it")
                    val user = it.getValue(User::class.java)
                    if(user !=null){
                        adapter.add(
                            UserItem(
                                user
                            )
                        )
                    }
                }
                adapterOnItemClickListener(adapter)
                recyclerView_newMessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun startChatActivity(){
        StartActivityHelper(this, ChatActivity::class.java).startNewActivity()
        finish()
    }
}
