package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import retrofit2.Response


class RequestPwResetRepository(private val apiService: ApiService) {

    // A suspend function to perform the login request via API
    suspend fun requestPwReset(
        email: String
    ): Response<ApiResponse<String>> {
        return apiService.requestPwReset(email)
    }
}
