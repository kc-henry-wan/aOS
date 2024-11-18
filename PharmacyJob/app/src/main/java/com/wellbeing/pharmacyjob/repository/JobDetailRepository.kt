package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.JobDetail
import retrofit2.Response


class JobDetailRepository(private val apiService: ApiService) {

    suspend fun getJobDetail(
        id: String
    ): Response<ApiResponse<JobDetail>> {
        // Calls the API to perform login and return the response
        return apiService.getJobDetail(id)
    }
}
