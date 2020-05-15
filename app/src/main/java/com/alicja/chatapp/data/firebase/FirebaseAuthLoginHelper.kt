package com.alicja.chatapp.data.firebase

import android.content.Context
import android.util.Log
import com.alicja.chatapp.delegators.StartActivityHelper
import com.alicja.chatapp.delegators.Toaster
import com.alicja.chatapp.ui.message.LatestMessagesActivity
import com.alicja.chatapp.ui.registerlogin.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class FirebaseAuthLoginHelper (private val context: Context, private val email: String, private val password:String){

    fun performLogin(){
        Log.d(LoginActivity.TAG, "Attempting to login with email: $email and password: $password")

        if(emailOrPasswordIsEmpty()){
            Toaster.toast(context, "Enter correct data and try again")
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(!it.isSuccessful)  return@addOnCompleteListener

                Log.d(LoginActivity.TAG, "Successfully logged as uid: ${it.result!!.user!!.uid}")
                Toaster.toast(context , "Successfully logged in")

                val startActivity = StartActivityHelper (context, LatestMessagesActivity::class.java)
                startActivity.startActivityWithClearTaskFlag()
            }

            .addOnFailureListener{
                Log.d(LoginActivity.TAG, "Failed to log in ${it.message}")
                Toaster.toast(context, "Error occured. Try again")
            }
    }

    private fun emailOrPasswordIsEmpty() : Boolean{
        return email.isEmpty() || password.isEmpty()
    }
}