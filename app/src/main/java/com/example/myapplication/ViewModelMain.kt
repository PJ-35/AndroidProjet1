package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class ViewModelMain : ViewModel() {
    private val _administrateur :MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        _administrateur.value = false

    }
    val administrateur: LiveData<Boolean> get() = _administrateur

    // Méthode pour mettre à jour la variable et notifier les observateurs
    fun setVariableChanged(changed: Boolean) {
        _administrateur.value = changed
    }






}

