package com.wellbeing.pharmacyjob.api

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.wellbeing.pharmacyjob.view.LoginActivity
import retrofit2.Response

object ApiHelper {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error("Empty Response >>> " + response.toString())
            } else {
                ApiResult.Error(response.message() + " >>> " + response.toString())
            }
        } catch (e: Exception) {
            ApiResult.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    fun getSessionKey(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("sessionKey", null)
    }

    private fun handleSessionTimeout(context: Context) {
        // Clear session data and navigate to login page
        val sharedPreferences =
            context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)

        Toast.makeText(context, "Session timeout, please log in again.", Toast.LENGTH_LONG).show()
    }

    private fun saveSessionKey(sessionKey: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("sessionKey", sessionKey).apply()
    }

    private fun handleSessionTimeout() {
        // Logic to handle session timeout, e.g., navigate to login screen
        AppLogger.d("LoginViewModel", "Session timeout, redirecting to login.")
    }

    private fun showNoNetworkMessage() {
        // Logic to show no network message, e.g., show a toast
        AppLogger.d("LoginViewModel", "No internet connection.")
    }
}