package com.example.rekapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rekapp.model.*
import com.example.rekapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WalletViewModel(application: Application):AndroidViewModel(application), CoroutineScope {

    val walletLD = MutableLiveData<List<Wallet>>()
    val allWallet = MutableLiveData<List<Wallet>>()

    var jeniswallet = ""

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(jeniswallets:String){
        launch {
            jeniswallet = jeniswallets
            val db = buildDb(getApplication())
            db.RekAppDao().selectAllWallet(jeniswallet)

            walletLD.postValue(db.RekAppDao().selectAllWallet(jeniswallet))
            allWallet.postValue(db.RekAppDao().selectAllOfWallet())
        }
    }

    fun deleteWallet(wallet: Wallet){
        launch {
            val db = buildDb(getApplication())
            db.RekAppDao().deleteWallet(wallet)

            walletLD.postValue(db.RekAppDao().selectAllWallet(jeniswallet))
        }
    }

    fun deleteTransaction(wallet:Int){
        launch {
            val db = buildDb(getApplication())
            db.RekAppDao().deleteTransaction(wallet)
        }
    }
}