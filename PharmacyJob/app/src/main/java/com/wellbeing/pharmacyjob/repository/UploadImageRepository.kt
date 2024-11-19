package com.wellbeing.pharmacyjob.repository

import com.wellbeing.pharmacyjob.api.ApiService
import com.wellbeing.pharmacyjob.model.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response


class UploadImageRepository(private val apiService: ApiService) {

    suspend fun uploadImage(
        imageType: String,
        imageFile: MultipartBody.Part
    ): Response<ApiResponse<String>> {
        return apiService.uploadImage(imageType, imageFile)
    }
}
