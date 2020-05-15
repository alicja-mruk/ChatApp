package com.alicja.chatapp.ui.registerlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alicja.chatapp.R
import com.alicja.chatapp.data.firebase.FirebaseAuthLoginHelper
import com.alicja.chatapp.delegators.StartActivityHelper
import com.alicja.chatapp.ui.message.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    companion object{
        const val TAG = "LoginActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        loginBtn.setOnClickListener{
            val email = emailTextLogin.text.toString()
            val password = passwordTextLogin.text.toString()
            FirebaseAuthLoginHelper(this, email, password).performLogin()
        }

        goToRegisterBtn.setOnClickListener{
          StartActivityHelper(this, RegisterActivity::class.java).startActivityWithClearTaskFlag()
        }

    }

}
