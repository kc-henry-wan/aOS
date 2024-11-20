package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.UploadDocRepository
import com.wellbeing.pharmacyjob.viewmodel.UploadDocViewModel

class UploadDocViewModelFactory(private val repository: UploadDocRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadDocViewModel::class.java)) {
            return UploadDocViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
