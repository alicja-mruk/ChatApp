package com.alicja.chatapp.delegators

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.alicja.chatapp.ui.message.LatestMessagesActivity
import com.alicja.chatapp.ui.registerlogin.LoginActivity
import com.alicja.chatapp.ui.message.NewMessagesActivity
import com.alicja.chatapp.ui.registerlogin.RegisterActivity

class StartActivityHelper (private val context: Context, private val className : Class<out Activity>  ){
        fun startActivityWithClearTaskFlag() {
            val intent = Intent(context, className)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

         fun startNewActivity() {
            val intent = Intent(context, className)
            context.startActivity(intent)
        }


}