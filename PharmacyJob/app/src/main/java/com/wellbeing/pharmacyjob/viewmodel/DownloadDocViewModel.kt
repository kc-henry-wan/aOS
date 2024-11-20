package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.repository.DownloadDocRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class DownloadDocViewModel(private val repository: DownloadDocRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<MultipartBody.Part>>()

    fun downloadDoc(id: String) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.downloadDoc(id)
            }

            AppLogger.d(
                "DownloadDocViewModel",
                "downloadDoc > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("DownloadDocViewModel", "downloadDoc API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("DownloadDocViewModel", "downloadDoc API Failed")
                }
            }
            liveData.value = result
        }
    }
}
