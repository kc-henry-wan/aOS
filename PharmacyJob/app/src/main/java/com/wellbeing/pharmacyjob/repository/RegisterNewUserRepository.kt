package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.RegUserRequest
import retrofit2.Response


class RegisterNewUserRepository(private val apiService: ApiService) {

    // A suspend function to perform the login request via API
    suspend fun registerNewUser(
        request: RegUserRequest
    ): Response<ApiResponse<String>> {
        return apiService.registerNewUser(request)
    }
}
