package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.NegotiationResponse
import retrofit2.Response

class NegotiationRepository(private val apiService: ApiService) {

    // A suspend function to perform the login request via API
    suspend fun getNegotiation(): Response<NegotiationResponse> {
        // Calls the API to perform login and return the response
        return apiService.getNegotiation()
    }
}
