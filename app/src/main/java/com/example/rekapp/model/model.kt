package com.example.rekapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallet(
    @ColumnInfo(name="namawallet")
    var namawallet:String,
    @ColumnInfo(name="jeniswallet")
    var jeniswallet:String,
    @ColumnInfo(name="sisasaldo")
    var sisasaldo:Int,
) {
    @PrimaryKey(autoGenerate = true)
    var idwallet:Int = 0
}

@Entity
data class Transaction(
    @ColumnInfo(name="wallet1")
    var wallet1:Int,
    @ColumnInfo(name="wallet2")
    var wallet2:Int,
    @ColumnInfo(name="nominal")
    var nominal:Int,
    @ColumnInfo(name="jenisTransaksi")
    var jenisTransaksi:String,
    @ColumnInfo(name="deskripsi")
    var deskripsi:String?,
    @ColumnInfo(name="tanggaltransaksi")
    var tanggaltransaksi:String
) {
    @PrimaryKey(autoGenerate = true)
    var idtransaction:Int = 0
}