package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UserDocListResponse
import retrofit2.Response


class MyDocRepository(private val apiService: ApiService) {

    suspend fun getMyDocList(): Response<ApiResponse<UserDocListResponse>> {
        return apiService.getMyDocList()
    }
}
