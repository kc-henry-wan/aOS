package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.NegotiateUpdateRequest
import com.wellbeing.pharmacyjob.repository.NegotiateUpdateRepository
import kotlinx.coroutines.launch

class NegotiateUpdateViewModel(private val repository: NegotiateUpdateRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<String>>>()

    fun updateNegotiation(id: String, request: NegotiateUpdateRequest) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.updateNegotiation(id, request)
            }

            AppLogger.d(
                "NegotiateUpdateViewModel",
                "updateNegotiation > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("NegotiateUpdateViewModel", "updateNegotiation API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("NegotiateUpdateViewModel", "updateNegotiation API Failed")
                }
            }
            liveData.value = result
        }
    }
}
