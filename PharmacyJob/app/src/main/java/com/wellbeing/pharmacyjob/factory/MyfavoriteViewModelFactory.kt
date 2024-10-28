package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.MyfavoriteRepository
import com.wellbeing.pharmacyjob.viewmodel.MyfavoriteViewModel


class MyfavoriteViewModelFactory(private val repository: MyfavoriteRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyfavoriteViewModel::class.java)) {
            return MyfavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
