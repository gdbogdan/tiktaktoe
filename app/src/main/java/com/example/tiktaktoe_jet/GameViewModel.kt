package com.example.tiktaktoe_jet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.PreferencesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private var _seconds = MutableStateFlow(preferencesRepository.getSeconds())
    val seconds: StateFlow<Int> get() = _seconds
    private var timerJob: Job? = null

    private var _boardState = MutableStateFlow(preferencesRepository.getBoardState())
    val boardState: StateFlow<Array<Array<String>>> get() = _boardState

    private val _difficulty = MutableStateFlow(preferencesRepository.getDifficulty())
    val difficulty: StateFlow<String> get() = _difficulty

    private val _winner = MutableStateFlow(preferencesRepository.getWinner())
    val winner: StateFlow<String?> get() = _winner

    init {
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)  // Espera 1 segundo
                _seconds.value += 1
                preferencesRepository.saveSeconds(_seconds.value)
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    fun setDifficulty(value: String) {
        viewModelScope.launch {
            _difficulty.value = value
            preferencesRepository.saveDifficulty(value)
        }
    }

    fun onCellClick(row: Int, col: Int) {
        if (_winner.value != null || _boardState.value[row][col].isNotEmpty()) return

        val newBoardState = _boardState.value.map { it.copyOf() }.toTypedArray()
        newBoardState[row][col] = "X"  // Current player move
        // Game logic omitted for brevity
        _boardState.value = newBoardState
        preferencesRepository.saveBoardState(newBoardState)
    }

    fun resetBoard() {
        _boardState.value = Array(3) { Array(3) { "" } }
        preferencesRepository.saveBoardState(_boardState.value)
        _winner.value = null
        preferencesRepository.saveWinner(null)
        _seconds.value = 0
        startTimer()
    }
}
