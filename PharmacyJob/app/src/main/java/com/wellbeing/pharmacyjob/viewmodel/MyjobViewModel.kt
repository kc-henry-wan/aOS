package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.JobListResponse
import com.wellbeing.pharmacyjob.repository.MyjobRepository
import kotlinx.coroutines.launch

class MyjobViewModel(private val repository: MyjobRepository) : ViewModel() {

    val myjobLiveData = MutableLiveData<ApiResult<JobListResponse>>()

    fun getMyJob() {

        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.getMyJob()
            }
            AppLogger.d(
                "MyjobViewModel",
                "viewModelScope.launch > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    // Handle success
                    AppLogger.d(
                        "MyjobViewModel",
                        "myjobjob API Successful"
                    )
                }

                is ApiResult.Error -> {
                    // Handle error
                    AppLogger.d(
                        "MyjobViewModel",
                        "myjobjob API Failed"
                    )
                }
            }
            myjobLiveData.value = result
        }
    }
}
