package com.example.tiktaktoe_jet

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _seconds = MutableStateFlow(0)
    val seconds: StateFlow<Int> get() = _seconds

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)  // Espera 1 segundo
                _seconds.value += 1
            }
        }
    }
    var boardState by mutableStateOf(Array(3) { Array(3) { "" } })
        private set

    private val difficulty = getDificulty()
    var currentPlayer by mutableStateOf("X")
        private set

    var gameOver by mutableStateOf(false)
        private set

    var winner by mutableStateOf<String?>(null)
        private set

    fun onCellClick(row: Int, col: Int) {
        if (!gameOver && boardState[row][col] == "") {
            boardState = boardState.copyOf().apply {
                this[row][col] = currentPlayer
            }
            if(difficulty == "individual") {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                gameOver = isGameOver(boardState)
                if (!gameOver) {
                    val computerMove = getComputerMove(boardState)
                    boardState = boardState.copyOf().apply {
                        this[computerMove.first][computerMove.second] = "O"
                    }
                    gameOver = isGameOver(boardState)
                }
            }else{
                currentPlayer = "X"
                gameOver = isGameOver(boardState)
            }

        }
    }

    fun resetBoard() {
        boardState = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameOver = false
        winner = null
    }

    private fun isGameOver(boardState: Array<Array<String>>): Boolean {
        // Check rows and columns for a winner
        for (i in 0..2) {
            if (boardState[i][0] != "" && boardState[i][0] == boardState[i][1] && boardState[i][1] == boardState[i][2]) {
                winner = boardState[i][0]
                return true
            }
            if (boardState[0][i] != "" && boardState[0][i] == boardState[1][i] && boardState[1][i] == boardState[2][i]) {
                winner = boardState[0][i]
                return true
            }
        }

        // Check diagonals for a winner
        if (boardState[0][0] != "" && boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2]) {
            winner = boardState[0][0]
            return true
        }
        if (boardState[2][0] != "" && boardState[2][0] == boardState[1][1] && boardState[1][1] == boardState[0][2]) {
            winner = boardState[2][0]
            return true
        }

        // Check for a tie
        var isTie = true
        for (row in boardState) {
            for (cell in row) {
                if (cell == "") {
                    isTie = false
                }
            }
        }
        winner = if (isTie) "Tie" else null
        return isTie
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
