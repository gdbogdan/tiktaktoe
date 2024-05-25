package com.example.tiktaktoe_jet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory(
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

