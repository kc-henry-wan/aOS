package com.wellbeing.pharmacyjob.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.JobListResponse
import com.wellbeing.pharmacyjob.repository.AvailablejobRepository
import kotlinx.coroutines.launch

class AvailablejobViewModel(private val repository: AvailablejobRepository) : ViewModel() {
    val availablejobLiveData = MutableLiveData<ApiResult<JobListResponse>>()

    fun getAvailablejob(startDate: String, endDate: String, apiDataSortBy: String, context: Context) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.getAvailableJob(
                    startDate,
                    endDate,
                    apiDataSortBy
                )
            }
            AppLogger.d(
                "AvailablejobViewModel",
                "viewModelScope.launch > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    // Handle success
                    AppLogger.d(
                        "AvailablejobViewModel",
                        "Availablejob API Successful"
                    )
                }

                is ApiResult.Error -> {
                    // Handle error
                    AppLogger.d(
                        "AvailablejobViewModel",
                        "Availablejob API Failed"
                    )
                }
            }
            availablejobLiveData.value = result
        }

    }

}