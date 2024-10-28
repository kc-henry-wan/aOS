package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.JobListResponse
import retrofit2.Response

class AvailablejobRepository(private val apiService: ApiService) {

    // A suspend function to perform the login request via API
    suspend fun getAvailableJob(startDate: String, endDate: String, apiDataSortBy: String): Response<JobListResponse> {
        // Calls the API to perform login and return the response
        return apiService.getAvailableJob(startDate, endDate, apiDataSortBy)
    }
}
