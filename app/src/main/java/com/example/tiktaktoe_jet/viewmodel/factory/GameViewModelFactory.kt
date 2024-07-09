package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.repository.PreferencesRepository
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel.GameViewModel

class GameViewModelFactory(private val preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}





