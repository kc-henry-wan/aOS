package com.wellbeing.pharmacyjob.api

import AppLogger
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.wellbeing.pharmacyjob.view.LoginActivity
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

object ApiHelper {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            AppLogger.d("ApiHelper", "apiCall: $apiCall")
            val response = apiCall()
            AppLogger.d("ApiHelper", "safeApiCall-response: " + response)

            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error(
                    httpStatusCode = null,
                    errorCode = null,
                    errorMessage = "Empty Response",
                    rawResponse = response.toString()
                )
            } else {
                // Parse error body for backend-specific error code and message
                val errorBody = response.errorBody()?.string()
                val (errorCode, errorMessage) = parseBackendError(errorBody)

                ApiResult.Error(
                    httpStatusCode = response.code(),
                    errorCode = errorCode,
                    errorMessage = errorMessage ?: response.message(),
                    rawResponse = response.toString()
                )
            }
        } catch (e: Exception) {
            ApiResult.Error(
                httpStatusCode = null,
                errorCode = null,
                errorMessage = e.localizedMessage ?: "An error occurred"
            )
        }
    }

    // Function to parse backend-specific error code and message
    private fun parseBackendError(errorBody: String?): Pair<String?, String?> {
        return try {
            errorBody?.let {
                val jsonObject = JSONObject(it)
                val errorCode =
                    jsonObject.optString("errorCode", null) // Backend-specific error code
                val errorMessage =
                    jsonObject.optString("errorMessage", null)       // Backend error message
                Pair(errorCode, errorMessage)
            } ?: Pair(null, null)
        } catch (e: JSONException) {
            Pair(null, null) // Return null if parsing fails
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
        val sharedPreferences =
            context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
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
