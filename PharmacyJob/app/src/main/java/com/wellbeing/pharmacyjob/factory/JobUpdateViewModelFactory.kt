package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.JobUpdateRepository
import com.wellbeing.pharmacyjob.viewmodel.JobUpdateViewModel

class JobUpdateViewModelFactory(private val repository: JobUpdateRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobUpdateViewModel::class.java)) {
            return JobUpdateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
