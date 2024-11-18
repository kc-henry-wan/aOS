package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UserUpdateRequest
import com.wellbeing.pharmacyjob.repository.UserUpdateRepository
import kotlinx.coroutines.launch

class UserUpdateViewModel(private val repository: UserUpdateRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<String>>>()

    fun updateUserDetail(id: String, request: UserUpdateRequest) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.updateUserDetail(id, request)
            }

            AppLogger.d(
                "UserUpdateViewModel",
                "updateUserDetail > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("UserUpdateViewModel", "updateUserDetail API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("UserUpdateViewModel", "updateUserDetail API Failed")
                }
            }
            liveData.value = result
        }
    }
}
