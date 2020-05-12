package com.alicja.chatapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alicja.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerBtn.setOnClickListener{
           performRegister()
        }

        alreadyHaveAnAccountBtn.setOnClickListener{
           startLoginActivity()
        }

    }

    private fun emailOrPasswordIsEmpty() : Boolean{
        val email = emailTextRegister.text.toString()
        val password = passwordTextRegister.text.toString()
        return email.isEmpty() || password.isEmpty()
    }

    private fun startLoginActivity(){
        Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun resetFields(){
        emailTextRegister.setText("")
        passwordTextRegister.setText("")
    }

    private fun performRegister(){
        if(emailOrPasswordIsEmpty()){
            Toast.makeText(this, "Enter correct data and try again", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailTextRegister.text.toString(), passwordTextRegister.text.toString())
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener
                //if register succesful
                Log.d("Register", "Successfully created user with uid: ${it.result!!.user!!.uid}")
                startLoginActivity()
            }

            .addOnFailureListener{
                Log.d("Register", "Failed to create user ${it.message}")
                Toast.makeText(this, "Error occured. Try again", Toast.LENGTH_SHORT).show()
            }
    }

}


