package com.example.tiktaktoe_jet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {
    private val _seconds = MutableStateFlow(0)
    val seconds: StateFlow<Int> = _seconds

    fun incrementSeconds() {
        _seconds.value++
    }

    fun resetSeconds() {
        _seconds.value = 0
    }
}