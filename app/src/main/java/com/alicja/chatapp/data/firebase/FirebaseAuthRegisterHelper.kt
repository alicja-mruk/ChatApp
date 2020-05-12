package com.alicja.chatapp.data.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.alicja.chatapp.delegators.StartActivityHelper
import com.alicja.chatapp.delegators.Toaster
import com.google.firebase.auth.FirebaseAuth


class FirebaseAuthRegisterHelper (private val context: Context, private val email: String, private val password: String,
                          private val selectedPhotoUri: Uri?, private val username: String) {

    fun performRegister(){

        if(isEmailOrPasswordEmpty()){
            Toaster.toast(context, "Enter correct data and try again")
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener
                //if register succesful
                Log.d("Register", "Successfully created user with uid: ${it.result?.user?.uid}")
                Toaster.toast(context, "Registered successfully")

                val firebaseDatabaseHelper = FirebaseDatabaseHelper(selectedPhotoUri, username)

                firebaseDatabaseHelper.uploadImageToFirebaseStorage()

                val startActivityHelper = StartActivityHelper (context)
                startActivityHelper.startLoginActivity()
            }

            .addOnFailureListener{
                Log.d("Register", "Failed to create user ${it.message}")
                Toaster.toast(context, "Error has occurred. Try again")
            }
    }

    private fun isEmailOrPasswordEmpty() : Boolean{
        return email.isEmpty() || password.isEmpty()
    }

}