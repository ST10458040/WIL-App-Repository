package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)

        findViewById<Button>(R.id.btnSignIn)?.setOnClickListener {
            val email = etEmail?.text?.toString()?.trim() ?: ""
            val password = etPassword?.text?.toString() ?: ""
            
            tilEmail.error = null
            tilPassword.error = null

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                tilEmail.error = "Invalid email format"
                return@setOnClickListener
            }
            
            if (password.isEmpty()) {
                tilPassword.error = "Please enter your password"
                return@setOnClickListener
            }

            try {
                // Simulate Login
                UserPrefs.saveUser(this, "John Shezi", email)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<TextView>(R.id.tvDemo1)?.setOnClickListener {
            UserPrefs.saveUser(this, "John Shezi", "john@example.com")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<View>(R.id.llBack)?.setOnClickListener {
            startActivity(Intent(this, GetStartedActivity::class.java))
            finish()
        }

        findViewById<TextView>(R.id.tvCreateAccount)?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}