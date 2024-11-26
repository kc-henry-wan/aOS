package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.LoginResponse
import com.wellbeing.pharmacyjob.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    val loginLiveData = MutableLiveData<ApiResult<ApiResponse<LoginResponse>>>()

    fun login(username: String, password: String) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall { repository.login(username, password) }
            AppLogger.d(
                "LoginViewModel",
                "viewModelScope.launch > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    // Handle success
                    AppLogger.d("LoginViewModel", "Login API Successful")
                }

                is ApiResult.Error -> {
                    // Handle error
                    AppLogger.d("LoginViewModel", "Login API Failed")
                }
            }
            loginLiveData.value = result
        }

    }

}
