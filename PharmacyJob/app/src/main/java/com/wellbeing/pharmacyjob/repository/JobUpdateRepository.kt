package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UpdateJobRequest
import retrofit2.Response


class JobUpdateRepository(private val apiService: ApiService) {

    // A suspend function to perform the login request via API
    suspend fun updateJobStatus(
        id: String,
        request: UpdateJobRequest
    ): Response<ApiResponse<String>> {
        // Calls the API to perform login and return the response
        return apiService.updateJobStatus(id, request)
    }
}
