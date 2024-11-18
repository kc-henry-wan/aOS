package com.wellbeing.pharmacyjob.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbeing.pharmacyjob.repository.NegotiateUpdateRepository
import com.wellbeing.pharmacyjob.viewmodel.NegotiateUpdateViewModel

class NegotiateUpdateViewModelFactory(private val repository: NegotiateUpdateRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NegotiateUpdateViewModel::class.java)) {
            return NegotiateUpdateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
