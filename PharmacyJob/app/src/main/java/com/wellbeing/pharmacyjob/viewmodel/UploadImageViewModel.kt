package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.repository.UploadImageRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadImageViewModel(private val repository: UploadImageRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<String>>>()

    fun uploadDoc(imageType: String, imageFile: MultipartBody.Part) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.uploadDoc(imageType, imageFile)
            }

            AppLogger.d(
                "UploadImageViewModel",
                "uploadImage > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("UploadImageViewModel", "uploadImage API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("UploadImageViewModel", "uploadImage API Failed")
                }
            }
            liveData.value = result
        }
    }
}
