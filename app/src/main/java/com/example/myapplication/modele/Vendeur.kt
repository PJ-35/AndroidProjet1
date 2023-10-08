package com.example.myapplication.modele

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "vendeurs")
data class Vendeur(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "nom")
    val nom: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name="prix")
    val prix: BigDecimal,

    @ColumnInfo("categorie")
    val categorie: String,

    @ColumnInfo("quantite")
    val quantite: Int,
)




