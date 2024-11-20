package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.DownloadDocRepository
import com.wellbeing.pharmacyjob.viewmodel.DownloadDocViewModel

class DownloadDocViewModelFactory(private val repository: DownloadDocRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DownloadDocViewModel::class.java)) {
            return DownloadDocViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
