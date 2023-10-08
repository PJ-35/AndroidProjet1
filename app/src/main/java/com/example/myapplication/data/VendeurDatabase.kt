package com.example.myapplication.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.modele.Vendeur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.random.Random

enum class categorie {
    EN_STOCK,
    EN_RUPTURE_DE_STOCK,
    EN_COMMANDE
}
@Database(entities = [Vendeur::class], version = 0)
abstract class VendeurDatabase:RoomDatabase() {
    // Méthode abstraite qui retourne un objet de type UserDao
    abstract fun vendeurDao(): VendeurDao

    // Singleton
    companion object {
        // @Volatile permet de garantir que les modifications sur cette variable
        // sont immédiatement visibles par tous les threads
        @Volatile
        // INSTANCE est un singleton, c'est-à-dire qu'il n'y a qu'une seule instance
        // de cette variable dans l'application
        private var INSTANCE: VendeurDatabase? = null

        // Méthode qui retourne l'instance de la base de données
        // Si la base de données n'existe pas, elle est créée
        // Cette méthode est thread-safe (synchronized), c'est-à-dire qu'elle peut être appelée
        // par plusieurs threads en même temps
        fun getInstance(context: Context): VendeurDatabase {
            // Si l'instance n'existe pas, on la crée
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    VendeurDatabase::class.java,
                    "database"
                )
                    // Fonction de rappel optionnelle
                    // Ajouter des données à la base de données à la création afin de tests
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            Log.d("TAG", "onCreate: BD")

                            INSTANCE?.let { database ->
                                // CoroutineScope : permet d'assigner une portée aux fils d'exécutions lancés
                                CoroutineScope(Dispatchers.IO).launch {
                                    (1..20).forEach {
                                        database.vendeurDao().insertVendeur(
                                            Vendeur(0, "Vendeur $it", "description $it",
                                                BigDecimal(45.45),categorie.values()[Random.nextInt(categorie.values().size)].toString(),1)
                                        )
                                    }
                                }
                            }
                        }
                    })
                    .build()

                // On assigne l'instance à la variable INSTANCE
                INSTANCE = instance
                // On retourne l'instance
                instance
            }
        }
    }
}