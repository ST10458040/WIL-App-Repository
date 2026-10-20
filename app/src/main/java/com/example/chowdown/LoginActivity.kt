package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)

        findViewById<Button>(R.id.btnSignIn)?.setOnClickListener {
            val email = etEmail?.text?.toString()?.trim() ?: "john@example.com"
            if (email.isNotEmpty()) {
                UserPrefs.saveUser(this, "John Shezi", email)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<TextView>(R.id.tvDemo1)?.setOnClickListener {
            UserPrefs.saveUser(this, "John Shezi", "john@example.com")
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        findViewById<View>(R.id.llBack)?.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }

        findViewById<TextView>(R.id.tvCreateAccount)?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}