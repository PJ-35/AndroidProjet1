package com.example.myapplication.modele

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.data.categorie

@Entity(tableName = "vendeurs")
data class Vendeur(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "nom")
    val nom: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name="prix")
    val prix: Double,

    @ColumnInfo("categorie")
    val categorie: categorie,

    @ColumnInfo("quantite")
    val quantite: Int,
)




