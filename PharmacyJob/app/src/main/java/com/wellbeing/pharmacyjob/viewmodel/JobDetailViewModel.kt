package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.JobDetail
import com.wellbeing.pharmacyjob.repository.JobDetailRepository
import kotlinx.coroutines.launch

class JobDetailViewModel(private val repository: JobDetailRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<JobDetail>>>()

    fun getJobDetail(id: String) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.getJobDetail(id)
            }

            AppLogger.d(
                "JobDetailViewModel",
                "getJobDetail > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("JobDetailViewModel", "getJobDetail API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("JobDetailViewModel", "getJobDetail API Failed")
                }
            }
            liveData.value = result
        }
    }
}
