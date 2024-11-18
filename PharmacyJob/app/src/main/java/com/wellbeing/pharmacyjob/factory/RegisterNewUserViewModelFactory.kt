package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.RegisterNewUserRepository
import com.wellbeing.pharmacyjob.viewmodel.RegisterNewUserViewModel

class RegisterNewUserViewModelFactory(private val repository: RegisterNewUserRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterNewUserViewModel::class.java)) {
            return RegisterNewUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
