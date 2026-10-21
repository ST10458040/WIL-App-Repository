package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tilFullName = findViewById<TextInputLayout>(R.id.tilFullName)
        val etFullName = findViewById<TextInputEditText>(R.id.etFullName)
        
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)

        findViewById<Button>(R.id.btnSignUp)?.setOnClickListener {
            val fullName = etFullName?.text?.toString()?.trim() ?: ""
            val email = etEmail?.text?.toString()?.trim() ?: ""
            val password = etPassword?.text?.toString() ?: ""
            val confirmPassword = etConfirmPassword?.text?.toString() ?: ""

            tilFullName.error = null
            tilEmail.error = null
            tilPassword.error = null
            tilConfirmPassword.error = null

            var isValid = true

            if (fullName.isEmpty()) {
                tilFullName.error = "Name cannot be empty"
                isValid = false
            }

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                tilEmail.error = "Invalid email format"
                isValid = false
            }

            val passPattern = "^(?=.*[0-9!@#\\\$%^&*]).{8,}\$"
            if (!password.matches(passPattern.toRegex())) {
                tilPassword.error = "At least 8 chars + 1 number/special char"
                isValid = false
            }

            if (password != confirmPassword) {
                tilConfirmPassword.error = "Passwords do not match"
                isValid = false
            }

            if (isValid) {
                try {
                    UserPrefs.saveUser(this, fullName, email)
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } catch (e: Exception) {
                    Toast.makeText(this, "Registration Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<TextView>(R.id.tvLogin)?.setOnClickListener {
            finish()
        }
    }
}