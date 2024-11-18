package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.NegotiateAddRequest
import com.wellbeing.pharmacyjob.repository.NegotiateAddRepository
import kotlinx.coroutines.launch

class NegotiateAddViewModel(private val repository: NegotiateAddRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<String>>>()

    fun addNegotiation(request: NegotiateAddRequest) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.addNegotiation(request)
            }

            AppLogger.d(
                "NegotiateAddViewModel",
                "addNegotiation > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("NegotiateAddViewModel", "addNegotiation API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("NegotiateAddViewModel", "addNegotiation API Failed")
                }
            }
            liveData.value = result
        }
    }
}
