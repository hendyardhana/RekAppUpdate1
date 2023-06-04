package com.example.rekapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rekapp.model.Transaction
import com.example.rekapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TransactionViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    var transactionLD = MutableLiveData<List<Transaction>>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val job = Job()

    fun refresh(wallet:Int, bulanTransaksi:String){
        launch {
            val db = buildDb(getApplication())
            db.RekAppDao().selectAllTransaction(wallet, bulanTransaksi)

            transactionLD.postValue(db.RekAppDao().selectAllTransaction(wallet, bulanTransaksi))
        }
    }
}