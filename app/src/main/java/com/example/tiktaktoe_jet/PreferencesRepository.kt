package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException

class PreferencesRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    companion object {
        const val BOARD_STATE_KEY = "board_state"
        const val DIFFICULTY_KEY = "difficulty"
        const val WINNER_KEY = "winner"
        const val SECONDS_KEY = "seconds"
    }

    fun getBoardState(): Array<Array<String>> {
        val boardStateJson = sharedPreferences.getString(BOARD_STATE_KEY, null) ?: return Array(3) { Array(3) { "" } }
        return try {
            val jsonArray = JSONArray(boardStateJson)
            Array(3) { i ->
                Array(3) { j ->
                    jsonArray.getJSONArray(i).getString(j)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Array(3) { Array(3) { "" } }
        }
    }

    fun saveBoardState(boardState: Array<Array<String>>) {
        val jsonArray = JSONArray()
        for (i in boardState.indices) {
            val rowArray = JSONArray()
            for (j in boardState[i].indices) {
                rowArray.put(boardState[i][j])
            }
            jsonArray.put(rowArray)
        }
        with(sharedPreferences.edit()) {
            putString(BOARD_STATE_KEY, jsonArray.toString())
            apply()
        }
    }

    fun getDifficulty(): String {
        return sharedPreferences.getString(DIFFICULTY_KEY, "individual") ?: "individual"
    }

    fun saveDifficulty(difficulty: String) {
        with(sharedPreferences.edit()) {
            putString(DIFFICULTY_KEY, difficulty)
            apply()
        }
    }

    fun getWinner(): String? {
        return sharedPreferences.getString(WINNER_KEY, null)
    }

    fun saveWinner(winner: String?) {
        with(sharedPreferences.edit()) {
            putString(WINNER_KEY, winner)
            apply()
        }
    }

    fun getSeconds(): Int {
        return sharedPreferences.getInt(SECONDS_KEY, 0)
    }

    fun saveSeconds(seconds: Int) {
        with(sharedPreferences.edit()) {
            putInt(SECONDS_KEY, seconds)
            apply()
        }
    }
}

