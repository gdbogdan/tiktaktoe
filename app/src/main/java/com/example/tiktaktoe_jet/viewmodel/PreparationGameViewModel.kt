package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.model.PreparationData
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.repository.PreparationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PreparationGameViewModel(private val repository: PreparationRepository) : ViewModel() {

    private val _aliasText = MutableStateFlow("Default Alias")
    val aliasText: StateFlow<String> get() = _aliasText

    private val _time = MutableStateFlow(false)
    val time: StateFlow<Boolean> get() = _time

    private val _difficulty = MutableStateFlow("Easy")
    val difficulty: StateFlow<String> get() = _difficulty

    init {
        // Load initial data from repository
        viewModelScope.launch {
            val data = repository.getPreparationData(1)
            _aliasText.value = data?.aliasText ?: "Default Alias"
            _time.value = data?.time ?: true
            _difficulty.value = data?.difficulty ?: "individual"
        }
    }

    fun setAliasText(newAlias: String) {
        _aliasText.value = newAlias
    }

    fun setTime(isActive: Boolean) {
        _time.value = isActive
    }

    fun setDifficulty(newDifficulty: String) {
        _difficulty.value = newDifficulty
    }

    fun saveData() {
        viewModelScope.launch {
            val data = PreparationData(
                id = 1,
                aliasText = _aliasText.value,
                time = _time.value,
                difficulty = _difficulty.value
            )
            repository.savePreparationData(data)
        }
    }
}
