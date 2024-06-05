package com.example.tiktaktoe_jet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel : ViewModel() {

    private val _time = MutableLiveData(true)
    val time: LiveData<Boolean> get() = _time

    private val _aliasText = MutableLiveData("Default Alias")
    val aliasText: LiveData<String> get() = _aliasText

    private val _difficulty = MutableLiveData("individual")
    val difficulty: LiveData<String> get() = _difficulty

    fun setTime(isActive: Boolean) {
        _time.value = isActive
        println(isActive)
    }

    fun setAliasText(newAlias: String) {
        _aliasText.value = newAlias
    }

    fun setDifficulty(newDifficulty: String) {
        _difficulty.value = newDifficulty
    }
}
