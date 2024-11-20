package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UserDocListResponse
import com.wellbeing.pharmacyjob.repository.MyDocRepository
import kotlinx.coroutines.launch

class MyDocViewModel(private val repository: MyDocRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<UserDocListResponse>>>()

    fun getMyDocList() {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.getMyDocList()
            }

            AppLogger.d(
                "MyDocViewModel",
                "myDoc > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("MyDocViewModel", "myDoc API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("MyDocViewModel", "myDoc API Failed")
                }
            }
            liveData.value = result
        }
    }
}
