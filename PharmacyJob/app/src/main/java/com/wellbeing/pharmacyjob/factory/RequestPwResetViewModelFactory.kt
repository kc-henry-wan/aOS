package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.RequestPwResetRepository
import com.wellbeing.pharmacyjob.viewmodel.RequestPwResetViewModel

class RequestPwResetViewModelFactory(private val repository: RequestPwResetRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestPwResetViewModel::class.java)) {
            return RequestPwResetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
