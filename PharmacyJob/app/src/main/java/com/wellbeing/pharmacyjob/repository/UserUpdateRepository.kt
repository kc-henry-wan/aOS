package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UserUpdateRequest
import retrofit2.Response


class UserUpdateRepository(private val apiService: ApiService) {

    suspend fun updateUserDetail(
        request: UserUpdateRequest
    ): Response<ApiResponse<String>> {
        // Calls the API to perform login and return the response
        return apiService.updateUserDetail(request)
    }
}
