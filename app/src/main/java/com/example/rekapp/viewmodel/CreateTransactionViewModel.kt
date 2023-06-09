package com.example.rekapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rekapp.model.Transaction
import com.example.rekapp.model.Wallet
import com.example.rekapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CreateTransactionViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val job = Job()

    fun addTransaction(transaction: Transaction){
        launch {
            val db = buildDb(getApplication())
            db.RekAppDao().insertTransaction(transaction)
        }
    }

    fun updateWallet(saldo:Int, idwallet:Int){
        launch {
            val db = buildDb(getApplication())
            db.RekAppDao().updateWallet(saldo, idwallet)
        }
    }
}