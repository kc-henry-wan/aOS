package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.ApiResponse
import com.wellbeing.pharmacyjob.model.UpdateJobRequest
import com.wellbeing.pharmacyjob.repository.JobUpdateRepository
import kotlinx.coroutines.launch

class JobUpdateViewModel(private val repository: JobUpdateRepository) : ViewModel() {

    val liveData = MutableLiveData<ApiResult<ApiResponse<String>>>()

    fun updateJobStatus(id: String, request: UpdateJobRequest) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.updateJobStatus(id, request)
            }

            AppLogger.d(
                "JobUpdateViewModel",
                "updateJobStatus > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("JobUpdateViewModel", "updateJobStatus API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("JobUpdateViewModel", "updateJobStatus API Failed")
                }
            }
            liveData.value = result
        }
    }
}
