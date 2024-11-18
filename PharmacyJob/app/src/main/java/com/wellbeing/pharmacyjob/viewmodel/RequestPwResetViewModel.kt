package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.repository.RequestPwResetRepository
import kotlinx.coroutines.launch

class RequestPwResetViewModel(private val repository: RequestPwResetRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<String>>>()

    fun requestPwReset(email: String) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.requestPwReset(email)
            }

            AppLogger.d(
                "RequestPwResetViewModel",
                "requestPwReset > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("RequestPwResetViewModel", "requestPwReset API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("RequestPwResetViewModel", "requestPwReset API Failed")
                }
            }
            liveData.value = result
        }
    }
}
