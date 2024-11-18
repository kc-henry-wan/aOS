package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.NegotiateAddRepository
import com.wellbeing.pharmacyjob.viewmodel.NegotiateAddViewModel

class NegotiateAddViewModelFactory(private val repository: NegotiateAddRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NegotiateAddViewModel::class.java)) {
            return NegotiateAddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
