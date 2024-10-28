package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.LoginRequest
import com.wellbeing.pharmacyjob.model.LoginResponse
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {

    // A suspend function to perform the login request via API
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        // Calls the API to perform login and return the response
        return apiService.login(LoginRequest("1", username, password))
    }
}
