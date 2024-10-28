package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.JobListResponse
import retrofit2.Response

class MyjobRepository(private val apiService: ApiService) {

    // A suspend function to perform the login request via API
    suspend fun getMyJob(favoriteIds: String): Response<JobListResponse> {
        // Calls the API to perform login and return the response
        return apiService.getMyJob(favoriteIds)
    }
}
