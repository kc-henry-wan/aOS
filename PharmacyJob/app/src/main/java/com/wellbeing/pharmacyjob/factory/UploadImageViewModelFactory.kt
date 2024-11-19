package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.UploadImageRepository
import com.wellbeing.pharmacyjob.viewmodel.UploadImageViewModel

class UploadImageViewModelFactory(private val repository: UploadImageRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UploadImageViewModel::class.java)) {
            return UploadImageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
