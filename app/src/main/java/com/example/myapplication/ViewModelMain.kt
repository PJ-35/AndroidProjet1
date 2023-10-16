package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.modele.Vendeur
class ViewModelMain : ViewModel() {
    private val _administrateur :MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val _articles :MutableLiveData<MutableList<Vendeur>> = MutableLiveData<MutableList<Vendeur>>()

    init {
        _administrateur.value = false
    }
    val administrateur: LiveData<Boolean> get() = _administrateur
    val articles: LiveData<MutableList<Vendeur>> get() = _articles

    // Méthode pour mettre à jour la variable et notifier les observateurs
    fun setVariableChanged(changed: Boolean) {
        _administrateur.value = changed
    }

    //Méthode qui retourne les vendeurs
    fun getArticlesDuPanier(): LiveData<MutableList<Vendeur>> {
        return _articles
    }

    // Méthode pour mettre à jour la liste d'articles
    fun setArticles(articlesList: MutableList<Vendeur>) {
        _articles.value = articlesList
    }


    //Méthode pour ajouter un vendeur
    fun addVendeur(vendeur: Vendeur) {

        val currentList = _articles.value ?: mutableListOf() // Obtenez la liste actuelle ou créez une nouvelle liste vide si elle est nulle
        currentList.add(vendeur) // Ajoutez le vendeur à la liste

        _articles.value=currentList

    }

}

