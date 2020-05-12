package com.alicja.chatapp.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alicja.chatapp.R
import com.alicja.chatapp.data.firebase.FirebaseAuthRegisterHelper
import com.alicja.chatapp.delegators.StartActivityHelper
import com.alicja.chatapp.delegators.Toaster
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private var selectedPhotoUri:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerBtn.setOnClickListener{
            val username = usernameRegister.text.toString()
            val email = emailTextRegister.text.toString()
            val password =  passwordTextRegister.text.toString()
            val firebaseAuthRegisterHelper = FirebaseAuthRegisterHelper(this, email , password, selectedPhotoUri, username)
            firebaseAuthRegisterHelper.performRegister()
        }

        val startActivityHelper = StartActivityHelper (this)
            alreadyHaveAnAccountBtn.setOnClickListener{
                startActivityHelper.startLoginActivity()
        }

        selectPhotoRegisterBtn.setOnClickListener{
            startImageIntent()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == 0 && resultCode == Activity.RESULT_OK && data != null ){
            setImage(data)
        }
    }

    private fun startImageIntent(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, 0)
    }

    private fun setImage(data: Intent?){
        Log.d("Register", "Photo was selected")

        if (data != null) selectedPhotoUri = data.data
        try {
            selectPhotoRegisterBtn.setImageURI(selectedPhotoUri)
        } catch (e: Exception) {
            Toaster.toast(this, "Failed to upload image")
            e.printStackTrace()
        }
    }
}


