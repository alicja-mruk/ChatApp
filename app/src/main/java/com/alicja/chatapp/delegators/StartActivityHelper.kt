package com.alicja.chatapp.delegators

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.alicja.chatapp.ui.LoginActivity

class StartActivityHelper (private val context: Context){

    fun startLoginActivity(){
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

}