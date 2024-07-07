package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SharedViewModel : ViewModel() {

    private val _time = MutableStateFlow(false)
    val time: StateFlow<Boolean> = _time

    private val _aliasText = MutableStateFlow("Default Alias")
    val aliasText: StateFlow<String> = _aliasText

    private val _difficulty = MutableStateFlow("individual")
    val difficulty: StateFlow<String> = _difficulty

    fun setTime(newTime: Boolean) {
        _time.value = newTime
    }

    fun setAliasText(newAlias: String) {
        _aliasText.value = newAlias
    }

    fun setDifficulty(newDifficulty: String) {
        _difficulty.value = newDifficulty
    }
}
