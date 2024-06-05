package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet

import android.content.Context
import android.content.SharedPreferences

class PreferencesRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

    fun getSeconds(): Int {
        return sharedPreferences.getInt("seconds", 0)
    }

    fun saveSeconds(seconds: Int) {
        sharedPreferences.edit().putInt("seconds", seconds).apply()
    }

    fun getBoardState(): Array<Array<String>> {
        val boardString = sharedPreferences.getString("boardState", null) ?: return Array(3) { Array(3) { "" } }
        return boardString.split(",").chunked(3).map { it.toTypedArray() }.toTypedArray()
    }

    fun saveBoardState(board: Array<Array<String>>) {
        val boardString = board.joinToString(",") { it.joinToString(",") }
        sharedPreferences.edit().putString("boardState", boardString).apply()
    }

    fun getDifficulty(): String {
        return sharedPreferences.getString("difficulty", "Easy") ?: "Easy"
    }

    fun saveDifficulty(difficulty: String) {
        sharedPreferences.edit().putString("difficulty", difficulty).apply()
    }

    fun getWinner(): String? {
        return sharedPreferences.getString("winner", null)
    }

    fun saveWinner(winner: String?) {
        sharedPreferences.edit().putString("winner", winner).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
