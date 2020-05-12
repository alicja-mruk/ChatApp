package com.alicja.chatapp.delegators

import android.content.Context
import android.widget.Toast

class Toaster (){
    companion object {
        fun toast(context : Context, message : String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun Toaster() {}
}