package com.wellbeing.pharmacyjob.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.repository.LoginRepository
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.api.SessionManager
import com.wellbeing.pharmacyjob.model.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    val loginLiveData = MutableLiveData<ApiResult<LoginResponse>>()

    fun login(username: String, password: String, context: Context) {
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
                    SessionManager.createSession(context, result.data?.sessionKey.toString(), result.data?.userId.toString())
                }

                is ApiResult.Error -> {
                    // Handle error
                    AppLogger.d("LoginViewModel", "Login API Failed" )
                    SessionManager.logout(context)
                }
            }
            loginLiveData.value = result
        }

    }

}
