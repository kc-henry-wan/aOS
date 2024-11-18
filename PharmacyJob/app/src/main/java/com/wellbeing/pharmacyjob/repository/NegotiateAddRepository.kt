package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.NegotiateAddRequest
import retrofit2.Response


class NegotiateAddRepository(private val apiService: ApiService) {

    suspend fun addNegotiation(
        request: NegotiateAddRequest
    ): Response<ApiResponse<String>> {
        // Calls the API to perform login and return the response
        return apiService.addNegotiation(request)
    }
}
