package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.repository.PreferencesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private var _seconds = MutableStateFlow(0)
    val seconds: StateFlow<Int> get() = _seconds
    private var timerJob: Job? = null

    private var _boardState = MutableStateFlow(Array(3) { Array(3) { "" } })
    val boardState: StateFlow<Array<Array<String>>> get() = _boardState

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

    fun onCellClick(row: Int, col: Int) {
        if (_winner.value != null || _boardState.value[row][col].isNotEmpty()) return

        if (currentPlayer == "X") {
            makeMove(row, col, "X")
            if (_winner.value == null && !isBoardFull(_boardState.value)) {
                viewModelScope.launch {
                    delay(1000L)  // Retraso de 1 segundo
                    makeBestMoveForAI()
                }
            }
        }
    }

    private fun makeMove(row: Int, col: Int, player: String) {
        val newBoardState = _boardState.value.map { it.copyOf() }.toTypedArray()
        newBoardState[row][col] = player
        _boardState.value = newBoardState
        _moves.value += "Player $player moved to ($row, $col) at ${_seconds.value} seconds"
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

        // Cambiar el jugador después de cada movimiento
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }

    private fun makeBestMoveForAI() {
        val bestMove = findBestMove(_boardState.value)
        if (bestMove != null) {
            makeMove(bestMove.first, bestMove.second, "O")
        }
    }

    private fun findBestMove(board: Array<Array<String>>): Pair<Int, Int>? {
        var bestVal = Int.MIN_VALUE
        var bestMove: Pair<Int, Int>? = null

        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j].isEmpty()) {
                    board[i][j] = "O"
                    val moveVal = minimax(board, 0, false)
                    board[i][j] = ""

                    if (moveVal > bestVal) {
                        bestMove = Pair(i, j)
                        bestVal = moveVal
                    }
                }
            }
        }
        return bestMove
    }

    private fun minimax(board: Array<Array<String>>, depth: Int, isMax: Boolean): Int {
        val score = evaluateBoard(board)

        if (score == 10) return score - depth
        if (score == -10) return score + depth
        if (isBoardFull(board)) return 0

        return if (isMax) {
            var best = Int.MIN_VALUE
            for (i in board.indices) {
                for (j in board[i].indices) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = "O"
                        best = maxOf(best, minimax(board, depth + 1, !isMax))
                        board[i][j] = ""
                    }
                }
            }
            best
        } else {
            var best = Int.MAX_VALUE
            for (i in board.indices) {
                for (j in board[i].indices) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = "X"
                        best = minOf(best, minimax(board, depth + 1, !isMax))
                        board[i][j] = ""
                    }
                }
            }
            best
        }
    }

    private fun evaluateBoard(board: Array<Array<String>>): Int {
        // Revisar filas y columnas
        for (i in 0..2) {
            if (board[i].all { it == "O" }) return 10
            if (board[i].all { it == "X" }) return -10
            if (board.all { it[i] == "O" }) return 10
            if (board.all { it[i] == "X" }) return -10
        }
        // Revisar diagonales
        if (board[0][0] == "O" && board[1][1] == "O" && board[2][2] == "O") return 10
        if (board[0][0] == "X" && board[1][1] == "X" && board[2][2] == "X") return -10
        if (board[0][2] == "O" && board[1][1] == "O" && board[2][0] == "O") return 10
        if (board[0][2] == "X" && board[1][1] == "X" && board[2][0] == "X") return -10
        return 0
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
