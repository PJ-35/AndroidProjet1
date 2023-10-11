package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class ViewModelMain:ViewModel() {
    private val _isVariableChanged :MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        _isVariableChanged.value = false
    }
    val isVariableChanged: LiveData<Boolean> get() = _isVariableChanged

    // Méthode pour mettre à jour la variable et notifier les observateurs
    fun setVariableChanged(changed: Boolean) {
        _isVariableChanged.value = changed
    }
}