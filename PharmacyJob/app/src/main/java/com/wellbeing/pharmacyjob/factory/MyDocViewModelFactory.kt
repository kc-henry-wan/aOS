package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.MyDocRepository
import com.wellbeing.pharmacyjob.viewmodel.MyDocViewModel

class MyDocViewModelFactory(private val repository: MyDocRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyDocViewModel::class.java)) {
            return MyDocViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
