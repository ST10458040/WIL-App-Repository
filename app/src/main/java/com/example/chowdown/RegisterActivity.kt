package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)

        findViewById<Button>(R.id.btnSignUp)?.setOnClickListener {
            val fullName = etFullName?.text?.toString()?.trim() ?: ""
            val email = etEmail?.text?.toString()?.trim() ?: ""

            if (fullName.isNotEmpty() && email.isNotEmpty()) {
                UserPrefs.saveUser(this, fullName, email)
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
            } else {
                Toast.makeText(this, "Please enter your name and email", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<TextView>(R.id.tvLogin)?.setOnClickListener {
            finish()
        }
    }
}