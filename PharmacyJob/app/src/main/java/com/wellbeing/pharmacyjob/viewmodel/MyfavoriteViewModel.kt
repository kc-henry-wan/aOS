package com.wellbeing.pharmacyjob.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbeing.pharmacyjob.api.ApiHelper
import com.wellbeing.pharmacyjob.api.ApiResult
import com.wellbeing.pharmacyjob.model.JobListResponse
import com.wellbeing.pharmacyjob.repository.MyfavoriteRepository
import kotlinx.coroutines.launch

class MyfavoriteViewModel(private val repository: MyfavoriteRepository) : ViewModel() {
    val myfavoriteLiveData = MutableLiveData<ApiResult<JobListResponse>>()

    fun getMyFavoriteJob(favoriteIds: String) {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch {
            val result = ApiHelper.safeApiCall {
                repository.getMyFavoriteJob(favoriteIds)
            }
            AppLogger.d(
                "MyfavoriteViewModel",
                "viewModelScope.launch > safeApiCall: " + result.data.toString()
            )
            when (result) {
                is ApiResult.Success -> {
                    AppLogger.d("MyfavoriteViewModel", "myfavoritejob API Successful")
                }

                is ApiResult.Error -> {
                    AppLogger.d("MyfavoriteViewModel", "myfavoritejob API Failed")
                }
            }
            myfavoriteLiveData.value = result
        }

    }

}
