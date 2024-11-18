package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.RegUserRequest
import com.wellbeing.pharmacyjob.repository.RegisterNewUserRepository
import kotlinx.coroutines.launch

class RegisterNewUserViewModel(private val repository: RegisterNewUserRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<String>>>()

    fun registerNewUser(request: RegUserRequest) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.registerNewUser(request)
            }

            AppLogger.d(
                "RegisterNewUserViewModel",
                "registerNewUser > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("RegisterNewUserViewModel", "registerNewUser API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("RegisterNewUserViewModel", "registerNewUser API Failed")
                }
            }
            liveData.value = result
        }
    }
}
