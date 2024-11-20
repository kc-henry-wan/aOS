package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import okhttp3.MultipartBody
import retrofit2.Response


class DownloadDocRepository(private val apiService: ApiService) {

    suspend fun downloadDoc(
        id: String,
    ): Response<MultipartBody.Part> {
        return apiService.downloadDoc(id)
    }
}
