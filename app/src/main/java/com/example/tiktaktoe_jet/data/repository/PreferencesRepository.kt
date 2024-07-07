package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.repository

import android.content.Context
import android.content.SharedPreferences

class PreferencesRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)


    fun saveSeconds(seconds: Int) {
        sharedPreferences.edit().putInt("seconds", seconds).apply()
    }

    fun saveBoardState(board: Array<Array<String>>) {
        val boardString = board.joinToString(",") { it.joinToString(",") }
        sharedPreferences.edit().putString("boardState", boardString).apply()
    }


    fun saveWinner(winner: String?) {
        sharedPreferences.edit().putString("winner", winner).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
