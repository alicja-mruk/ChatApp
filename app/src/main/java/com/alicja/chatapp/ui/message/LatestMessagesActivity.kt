package com.alicja.chatapp.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.alicja.chatapp.R
import com.alicja.chatapp.delegators.StartActivityHelper
import com.alicja.chatapp.ui.registerlogin.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class LatestMessagesActivity : AppCompatActivity() {

    companion object{
        const val TAG = "LatestMessagesActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)
        title = ""
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
}
