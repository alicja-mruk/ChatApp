package com.alicja.chatapp.data.firebase

import android.net.Uri
import android.util.Log
import com.alicja.chatapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class FirebaseDatabaseHelper (private val selectedPhotoUri: Uri?, private val username: String) {

    fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri)
            .addOnSuccessListener {
                Log.d("Register", "Successfully uploaded image: ${it.metadata?.path}")


                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Register", "File location ${it}")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User( uid, username, profileImageUrl )
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Register", "Successfully saved user with $uid to the database")
            }
            .addOnFailureListener{
                Log.d("Register", "Error occurred while saving the user to the database")
            }
    }
}

