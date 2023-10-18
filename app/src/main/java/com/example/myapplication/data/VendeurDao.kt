package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.modele.Vendeur

@Dao
interface VendeurDao {

    /**
     * dao.insertVendeur(vendeur)
     */
    @Insert
    fun insertVendeur(vendeur: Vendeur)

    /**
     * val id = dao.insertVendeurReturnId(Vendeur)
     */
    @Insert
    fun insertVendeurReturnId(vendeur: Vendeur): Long

    /**
     * vendeur = arrayOf(vendeur1, vendeur2, vendeur3)
     * val ids = dao.insertUsers(users)
     */
    @Insert
    fun insertVendeur(vendeur: List<Vendeur>): List<Long>

    /**
     * dao.updateVendeur(vendeur)
     */
    @Update
    fun updateVendeur(vendeur: Vendeur)

    @Delete
    fun deleteVendeur(vendeur: Vendeur)

    /**
     * val vendeur = dao.getVendeurById(1).observe(this, { user -> ... })
     */
    @Query("SELECT * FROM vendeurs WHERE id = :id")
    fun getVendeurById(id: Int): LiveData<Vendeur>

    /**
     * Version de getAllUsers retournant un live data
     */
    @Query("SELECT * FROM vendeurs")
    fun getAllVendeurs(): LiveData<List<Vendeur>>

    /**
     * delete all vendeurs
     */
    @Query("DELETE FROM vendeurs")
    fun deleteAllVendeurs()


    @Query("SELECT * FROM vendeurs WHERE id IN (:listId)")
    fun getListVendeurInId(listId: List<Int>): LiveData<List<Vendeur>>

}