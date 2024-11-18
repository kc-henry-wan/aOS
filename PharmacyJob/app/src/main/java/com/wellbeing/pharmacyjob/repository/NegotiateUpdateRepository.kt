package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.NegotiateUpdateRequest
import retrofit2.Response


class NegotiateUpdateRepository(private val apiService: ApiService) {

    suspend fun updateNegotiation(
        id: String,
        request: NegotiateUpdateRequest
    ): Response<ApiResponse<String>> {
        // Calls the API to perform login and return the response
        return apiService.updateNegotiation(id, request)
    }
}
