package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.repository.PreparationRepository
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.PreparationGameViewModel

class PreparationGameViewModelFactory(private val repository: PreparationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreparationGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PreparationGameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}