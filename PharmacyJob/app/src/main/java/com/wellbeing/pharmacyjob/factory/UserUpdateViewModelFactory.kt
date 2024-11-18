package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.UserUpdateRepository
import com.wellbeing.pharmacyjob.viewmodel.UserUpdateViewModel

class UserUpdateViewModelFactory(private val repository: UserUpdateRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserUpdateViewModel::class.java)) {
            return UserUpdateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
