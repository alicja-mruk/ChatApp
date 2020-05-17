package com.alicja.chatapp.data.chat

import android.content.Context
import com.google.firebase.auth.FirebaseAuth


class ChatProperties (){
    companion object {
        const val TAG = "ChatActivity"

        fun getCurrentUserId(): String? {
            return FirebaseAuth.getInstance().uid
        }

        fun getTimeStamp(): String {
            val tsLong = System.currentTimeMillis() / 1000
            return tsLong.toString()
        }
    }

}