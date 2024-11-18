package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.NegotiationResponse
import com.wellbeing.pharmacyjob.repository.NegotiationRepository
import kotlinx.coroutines.launch

class NegotiationViewModel(private val repository: NegotiationRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<NegotiationResponse>>()

    fun getNegotiation() {

        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.getNegotiation()
            }
            AppLogger.d(
                "NegotiationViewModel",
                "viewModelScope.launch > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    // Handle success
                    AppLogger.d(
                        "NegotiationViewModel",
                        "getNegotiation API Successful"
                    )
                }

                is ApiResult.Error -> {
                    // Handle error
                    AppLogger.d(
                        "NegotiationViewModel",
                        "getNegotiation API Failed"
                    )
                }
            }
            liveData.value = result
        }
    }
}
