package com.example.rekapp.model

import androidx.room.*

@Dao
interface RekAppDao {

    //Ini untuk Wallet
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallet(vararg wallet:Wallet) //Bisa banyak object

    @Query("SELECT * FROM wallet WHERE jeniswallet= :jeniswallet")
    fun selectAllWallet(jeniswallet:String): List<Wallet>

    @Query("SELECT * FROM wallet ")
    fun selectAllOfWallet(): List<Wallet>

    @Delete
    fun deleteWallet(wallet:Wallet)

    //Ini untuk Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(vararg transaction: Transaction)

    @Query("SELECT * FROM 'transaction' WHERE wallet1= :wallet or wallet2= :wallet")
    fun selectAllTransaction(wallet:Int): List<Transaction>

    @Query("DELETE FROM 'transaction' WHERE wallet1= :wallet or wallet2= :wallet")
    fun deleteTransaction(wallet:Int)

    @Query("UPDATE 'wallet' SET sisasaldo= sisasaldo+:saldo WHERE idwallet= :idwallet")
    fun updateWallet(saldo:Int, idwallet:Int)
}