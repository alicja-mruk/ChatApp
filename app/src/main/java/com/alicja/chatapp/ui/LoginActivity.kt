package com.alicja.chatapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alicja.chatapp.R
import com.alicja.chatapp.data.firebase.FirebaseAuthLoginHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener{
            val email = emailTextLogin.text.toString()
            val password = passwordTextLogin.text.toString()
            val firebaseAuthLoginHelper = FirebaseAuthLoginHelper(this, email, password)
            firebaseAuthLoginHelper.performLogin()
        }

        goToRegisterBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}
