package com.example.chowdown

import android.content.Context

object UserPrefs {
    private const val PREF_NAME = "chowdown_prefs"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    fun saveUser(context: Context, name: String, email: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USER_NAME, name).putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserName(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_NAME, "Mlondi Shezi") ?: "Mlondi Shezi"
    }

    fun getUserEmail(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_EMAIL, "mlondi@example.com") ?: "mlondi@example.com"
    }

    fun getInitials(name: String): String {
        if (name.isBlank()) return "MS"
        val parts = name.trim().split("\\s+".toRegex())
        return when {
            parts.size >= 2 -> "${parts[0].first().uppercaseChar()}${parts[1].first().uppercaseChar()}"
            parts.size == 1 && parts[0].isNotEmpty() -> "${parts[0].first().uppercaseChar()}"
            else -> "MS"
        }
    }
}