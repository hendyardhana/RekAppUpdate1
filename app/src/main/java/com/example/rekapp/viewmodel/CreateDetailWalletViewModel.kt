package com.example.rekapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.rekapp.model.Wallet
import com.example.rekapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CreateDetailWalletViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val job = Job()

    var listWallet = MutableLiveData<List<Wallet>>()

    fun addWallet(wallet: Wallet){
        launch {
            val db = buildDb(getApplication())
            db.RekAppDao().insertWallet(wallet)
        }
    }

    fun deleteWallet(wallet: Wallet){
        launch {
            val db = buildDb(getApplication())
            db.RekAppDao().deleteWallet(wallet)
        }
    }

    fun getWallet(){
        launch {
            val db = buildDb(getApplication())
            listWallet.value = db.RekAppDao().selectAllOfWallet()
        }
    }
}