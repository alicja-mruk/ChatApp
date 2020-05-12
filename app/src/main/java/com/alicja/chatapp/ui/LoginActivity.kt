package com.alicja.chatapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alicja.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        Log.d("Login", "Attempting to login with email: $emailTextLogin and password: $passwordTextLogin")
        loginBtn.setOnClickListener{
            performLogin()
        }

        goToRegisterBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
    private fun emailOrPasswordIsEmpty() : Boolean{
        return emailTextLogin.text.toString().isEmpty() || passwordTextLogin.text.toString().isEmpty()
    }

    private fun performLogin(){
        if(emailOrPasswordIsEmpty()){
            Toast.makeText(this, "Enter correct data and try again", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailTextLogin.text.toString(), passwordTextLogin.text.toString())
            .addOnCompleteListener{
                if(!it.isSuccessful)  return@addOnCompleteListener

                Log.d("Login", "Successfully logged as uid: ${it.result!!.user!!.uid}")
                Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener{
                Log.d("Login", "Failed to log in ${it.message}")
                Toast.makeText(this, "Error occured. Try again", Toast.LENGTH_SHORT).show()
            }
    }

}
