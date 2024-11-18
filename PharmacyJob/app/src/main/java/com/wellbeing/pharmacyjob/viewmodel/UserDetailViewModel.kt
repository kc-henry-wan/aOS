package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UserDetail
import com.wellbeing.pharmacyjob.repository.UserDetailRepository
import kotlinx.coroutines.launch

class UserDetailViewModel(private val repository: UserDetailRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<UserDetail>>>()

    fun getUserDetail(id: String) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.getUserDetail(id)
            }

            AppLogger.d(
                "UserDetailViewModel",
                "getUserDetail > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("UserDetailViewModel", "getUserDetail API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("UserDetailViewModel", "getUserDetail API Failed")
                }
            }
            liveData.value = result
        }
    }
}
