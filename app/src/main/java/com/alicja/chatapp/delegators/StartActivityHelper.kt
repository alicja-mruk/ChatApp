package com.alicja.chatapp.delegators

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.alicja.chatapp.ui.LatestMessagesActivity
import com.alicja.chatapp.ui.LoginActivity
import com.alicja.chatapp.ui.NewMessagesActivity
import com.alicja.chatapp.ui.RegisterActivity

class StartActivityHelper (private val context: Context){

    fun startLoginActivity(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun startRegisterActivity(){
        val intent = Intent(context, RegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun startLatestMessagesActivity(){
        val intent = Intent(context, LatestMessagesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun startNewMessagesActivity(){
        val intent = Intent(context, NewMessagesActivity::class.java)
        context.startActivity(intent)
    }

}