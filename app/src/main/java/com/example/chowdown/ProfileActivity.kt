package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val name = UserPrefs.getUserName(this)
        val email = UserPrefs.getUserEmail(this)
        val initials = UserPrefs.getInitials(name)

        findViewById<TextView>(R.id.tvProfileInitials)?.text = initials
        findViewById<TextView>(R.id.tvProfileName)?.text = name
        findViewById<TextView>(R.id.tvProfileEmail)?.text = email

        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        findViewById<CardView>(R.id.btnOrderHistory)?.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnLogOut)?.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}