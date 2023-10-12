package com.example.myapplication.data

enum class categorie(val etat: String) {
    EN_STOCK("en stock"),
    EN_RUPTURE_DE_STOCK("en rupture de stock"),
    EN_COMMANDE("en commande");
    companion object {
        fun fromEtat(etat: String): categorie? {
            return values().find { it.etat == etat }
        }
    }
}

