package com.example.tiktaktoe_jet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.PreferencesRepository

class GameViewModelFactory(
    private val preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




