package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UserDetail
import retrofit2.Response


class UserDetailRepository(private val apiService: ApiService) {

    suspend fun getUserDetail(
        id: String
    ): Response<ApiResponse<UserDetail>> {
        // Calls the API to perform login and return the response
        return apiService.getUserDetail(id)
    }
}
