package com.wellbeing.pharmacyjob.api

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREF_NAME = "session_pref"
    private const val KEY_TOKEN = "jwt_token"
    private const val KEY_USER_ID = "login_user_id"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    fun createSession(
        context: Context,
        token: String,
        userId: String
    ) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_USER_ID, userId)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserId(context: Context): String {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(KEY_USER_ID, null).toString()
    }

    fun getToken(context: Context): String {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(KEY_TOKEN, null).toString()
    }

    fun logout(context: Context) {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear() // Clear all session data
        editor.apply()
    }
}
