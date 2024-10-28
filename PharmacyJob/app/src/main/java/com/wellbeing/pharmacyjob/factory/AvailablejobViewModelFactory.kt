package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.AvailablejobRepository
import com.wellbeing.pharmacyjob.viewmodel.AvailablejobViewModel


class AvailablejobViewModelFactory(private val repository: AvailablejobRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvailablejobViewModel::class.java)) {
            return AvailablejobViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
