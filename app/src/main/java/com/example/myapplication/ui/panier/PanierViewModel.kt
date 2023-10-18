package com.example.myapplication.ui.panier

import androidx.lifecycle.ViewModel
import com.example.myapplication.modele.Vendeur

class PanierViewModel : ViewModel() {
    private val list_article_id: MutableList<Int> = mutableListOf()

    //Méthode qui retourne les vendeurs
    fun getIdArticlesDuPanier(): MutableList<Int> {

        return list_article_id
    }


    //Méthode pour ajouter un vendeur
    fun addVendeur(vendeur: Vendeur) {
        list_article_id.add(vendeur.id)

    }
}