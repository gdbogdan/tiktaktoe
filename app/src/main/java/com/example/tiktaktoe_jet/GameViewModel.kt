package com.example.tiktaktoe_jet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.PreferencesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private var _seconds = MutableStateFlow(0)
    val seconds: StateFlow<Int> get() = _seconds
    private var timerJob: Job? = null

    private var _boardState = MutableStateFlow(Array(3) { Array(3) { "" } })
    val boardState: StateFlow<Array<Array<String>>> get() = _boardState

    private val _difficulty = MutableStateFlow(preferencesRepository.getDifficulty())
    val difficulty: StateFlow<String> get() = _difficulty

    private val _winner = MutableStateFlow<String?>(null)
    val winner: StateFlow<String?> get() = _winner

    private var _moves = MutableStateFlow<List<String>>(emptyList())
    val moves: StateFlow<List<String>> get() = _moves

    private var currentPlayer = "X"

    init {
        resetBoard()
        startTimer()
    }

    private fun startTimer() {
        stopTimer()
        _seconds.value = 0
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

        makeMove(row, col, "X")
        if (_winner.value == null && !isBoardFull(_boardState.value)) {
            viewModelScope.launch {
                delay(1000L)  // Retraso de 1 segundo
                makeRandomMoveForAI()
            }
        }
    }

    private fun makeMove(row: Int, col: Int, player: String) {
        val newBoardState = _boardState.value.map { it.copyOf() }.toTypedArray()
        newBoardState[row][col] = player
        _boardState.value = newBoardState
        _moves.value = _moves.value + "Player $player moved to ($row, $col) at ${_seconds.value} seconds"
        preferencesRepository.saveBoardState(newBoardState)

        if (checkWinner(player, newBoardState)) {
            _winner.value = player
            preferencesRepository.saveWinner(player)
            stopTimer()
        } else if (isBoardFull(newBoardState)) {
            _winner.value = "isTie"
            preferencesRepository.saveWinner("isTie")
            stopTimer()
        }
    }

    private fun makeRandomMoveForAI() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in _boardState.value.indices) {
            for (j in _boardState.value[i].indices) {
                if (_boardState.value[i][j].isEmpty()) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }
        if (emptyCells.isNotEmpty()) {
            val (row, col) = emptyCells.random()
            makeMove(row, col, "O")
        }
    }

    private fun checkWinner(player: String, board: Array<Array<String>>): Boolean {
        for (i in 0..2) {
            if (board[i].all { it == player }) return true
            if (board.all { it[i] == player }) return true
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true
        return false
    }

    private fun isBoardFull(board: Array<Array<String>>): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    fun resetBoard() {
        _boardState.value = Array(3) { Array(3) { "" } }
        preferencesRepository.saveBoardState(_boardState.value)
        _winner.value = null
        preferencesRepository.saveWinner(null)
        _seconds.value = 0
        _moves.value = emptyList()
        currentPlayer = "X"
        startTimer()
    }
}
