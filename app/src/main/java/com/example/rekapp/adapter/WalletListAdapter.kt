package com.example.rekapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rekapp.R
import com.example.rekapp.model.Wallet
import com.example.rekapp.util.formatUang
import com.example.rekapp.view.WalletListFragmentDirections
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class WalletListAdapter(private val walletList:ArrayList<Wallet>, val adapterOnClick:(Wallet) -> Unit) : RecyclerView.Adapter<WalletListAdapter.WalletListViewHolder>() {
    class WalletListViewHolder(var view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.wallet_list_layout, parent, false)

        return WalletListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return walletList.size
    }

    override fun onBindViewHolder(holder: WalletListViewHolder, position: Int) {
        val btnWallet = holder.view.findViewById<Button>(R.id.btnWalletList)
        val imgDelete = holder.view.findViewById<ImageView>(R.id.imgDeleteWallet)
        btnWallet.text = "${walletList[position].namawallet} (${formatUang(walletList[position].sisasaldo.toDouble())})"

        btnWallet.setOnClickListener {
            val action = WalletListFragmentDirections.actionWalletListFragmentToTransactionListFragment(walletList[position].idwallet)
            Navigation.findNavController(it).navigate(action)
        }

        imgDelete.setOnClickListener {
            adapterOnClick(walletList[position])
        }
    }

    fun updateWalletList(newWallet: ArrayList<Wallet>) {
        walletList.clear()
        walletList.addAll(newWallet)
        notifyDataSetChanged()
    }
}