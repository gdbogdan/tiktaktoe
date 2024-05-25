package com.example.tiktaktoe_jet

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import kotlin.random.Random
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var _seconds = MutableStateFlow(0)
    val seconds: StateFlow<Int> get() = _seconds
    private var timerJob: Job? = null

    init {
        startTimer()
        savedStateHandle.get<Array<Array<String>>>("boardState")?.let {
            _boardState.value = it
        }
        savedStateHandle.get<String>("dificulty")?.let {
            _dificulty.value = it
        }
        savedStateHandle.get<String>("winner")?.let {
            _winner.value = it
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)  // Espera 1 segundo
                _seconds.value += 1
                savedStateHandle["seconds"] = _seconds.value
            }
        }
    }
    private fun stopTimer() {
        timerJob?.cancel()
    }
    private var _boardState = MutableStateFlow(Array(3) { Array(3) { "" } })
    val boardState: StateFlow<Array<Array<String>>> get() = _boardState

    private val _dificulty = MutableStateFlow("individual")
    val dificulty: StateFlow<String> get() = _dificulty

    fun setDifficulty(value: String) {
        viewModelScope.launch {
            _dificulty.value = value
            savedStateHandle["dificulty"] = _dificulty.value
        }
    }
    private var currentPlayer ="X"



    private val _winner = MutableStateFlow<String?>(null)
    val winner: StateFlow<String?> get() = _winner

    fun onCellClick(row: Int, col: Int) {
        if (_winner.value != null || _boardState.value[row][col].isNotEmpty()) return

        val newBoardState = _boardState.value.map { it.copyOf() }.toTypedArray()
        newBoardState[row][col] = currentPlayer  // Current player move

        if (dificulty.value == "individual") {
            if (isGameOver(newBoardState)) {
                _winner.value = currentPlayer
                savedStateHandle["winner"] = currentPlayer
                stopTimer()
            } else {
                val computerMove = getComputerMove(newBoardState)
                newBoardState[computerMove.first][computerMove.second] = "O"
                if (isGameOver(newBoardState)) {
                    _winner.value = "O"
                    savedStateHandle["winner"] = "O"
                    stopTimer()
                }
            }
        } else {
            if (isGameOver(newBoardState)) {
                _winner.value = currentPlayer
                savedStateHandle["winner"] = newBoardState[row][col]
                stopTimer()
            }
            else {
                currentPlayer = if (currentPlayer == "X") "O" else "X" // Cambiar jugador
            }
        }

        _boardState.value = newBoardState
        savedStateHandle["boardState"] = newBoardState
    }

    fun resetBoard() {
        _boardState.value = Array(3) { Array(3) { "" } }
        savedStateHandle["boardState"] = _boardState.value
        currentPlayer = "X"
        _winner.value = null
        savedStateHandle["winner"] = null
        _seconds = MutableStateFlow(0)
        startTimer()
    }

    private fun isGameOver(boardState: Array<Array<String>>): Boolean {
        // Check rows and columns for a winner
        for (i in 0..2) {
            if (boardState[i][0] != "" && boardState[i][0] == boardState[i][1] && boardState[i][1] == boardState[i][2]) {
                return true
            }
            if (boardState[0][i] != "" && boardState[0][i] == boardState[1][i] && boardState[1][i] == boardState[2][i]) {
                return true
            }
        }

        // Check diagonals for a winner
        if (boardState[0][0] != "" && boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2]) {
            return true
        }
        if (boardState[2][0] != "" && boardState[2][0] == boardState[1][1] && boardState[1][1] == boardState[0][2]) {
            return true
        }

        // Check for a tie
        if (boardState.all { row -> row.all { it.isNotEmpty() } }) {
            _winner.value = "Tie"
            savedStateHandle["winner"] = "Tie"
            return true
        }
        return false
    }

    private fun getComputerMove(boardState: Array<Array<String>>): Pair<Int, Int> {
        // Get a list of empty cells
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0..2) {
            for (j in 0..2) {
                if (boardState[i][j] == "") {
                    emptyCells.add(Pair(i, j))
                }
            }
        }

        // If there are no empty cells, the game is over
        if (emptyCells.isEmpty()) {
            return Pair(-1, -1)
        }

        // Choose a random empty cell
        val randomIndex = Random.nextInt(emptyCells.size)
        return emptyCells[randomIndex]
    }
}
