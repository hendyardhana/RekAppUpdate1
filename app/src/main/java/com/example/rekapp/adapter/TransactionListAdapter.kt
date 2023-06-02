package com.example.rekapp.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rekapp.Global
import com.example.rekapp.R
import com.example.rekapp.model.Transaction
import com.example.rekapp.model.Wallet
import com.example.rekapp.util.buildDb
import com.example.rekapp.view.TransactionListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TransactionListAdapter(private val transactionList:ArrayList<Transaction>, var wallet1:Int):RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>() {
    class TransactionListViewHolder(var view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.transaction_list_layout, parent, false)

        return TransactionListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        val txtTanggal = holder.view.findViewById<TextView>(R.id.txtTanggalTransaksi)
        val txtDariatauKe = holder.view.findViewById<TextView>(R.id.txtTransaksiDariKe)
        val txtNominal = holder.view.findViewById<TextView>(R.id.txtNominal)
        txtTanggal.text = transactionList[position].tanggaltransaksi

        var jeniswallets = ""
        if(transactionList[position].wallet1 == wallet1){
            val wallet = transactionList[position].wallet2
            val globalwallets = Global.wallet
            var globalwallet = Wallet("", "", 0)

            for (i in 0 until globalwallets.size){
                if(globalwallets[i].idwallet == wallet){
                    globalwallet = globalwallets[i]
                    //Log.d("datawallet1", globalwallet.toString())
                }
            }

            if(globalwallet.namawallet != "" && globalwallet.jeniswallet != ""){
                when(globalwallet.jeniswallet){
                    "bank" -> jeniswallets = "BANK"
                    "spay" -> jeniswallets = "SHOPEE PAY"
                    "gopay" -> jeniswallets = "GOPAY"
                    "dana" -> jeniswallets = "DANA"
                    "ovo" -> jeniswallets = "OVO"
                }
                txtDariatauKe.text = "Transaksi Ke " + globalwallet.namawallet + " - " + jeniswallets
                txtNominal.text = "- "+transactionList[position].nominal.toString()
                txtNominal.setTextColor(Color.RED)
            }
        }
        else{
            val wallet = transactionList[position].wallet1
            val globalwallets = Global.wallet
            var globalwallet = Wallet("", "", 0)
            for (i in 0 until globalwallets.size){
                if(globalwallets[i].idwallet == wallet){
                    globalwallet = globalwallets[i]
                    //Log.d("datawallet2", globalwallet.toString())
                }
            }
            if(globalwallet.namawallet != "" && globalwallet.jeniswallet != ""){
                when(globalwallet.jeniswallet){
                    "bank" -> jeniswallets = "BANK"
                    "spay" -> jeniswallets = "SHOPEE PAY"
                    "gopay" -> jeniswallets = "GOPAY"
                    "dana" -> jeniswallets = "DANA"
                    "ovo" -> jeniswallets = "OVO"
                }
                txtDariatauKe.text = "Transaksi Dari " + globalwallet.namawallet + " - " + jeniswallets
                txtNominal.text = "+ " + transactionList[position].nominal.toString()
                txtNominal.setTextColor(Color.GREEN)
            }
        }
    }

    fun updateTransactionList(newTransaction: ArrayList<Transaction>, newWallet1: Int) {
        transactionList.clear()
        transactionList.addAll(newTransaction)
        wallet1 = newWallet1
        notifyDataSetChanged()
    }
}