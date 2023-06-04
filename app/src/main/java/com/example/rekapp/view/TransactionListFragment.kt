package com.example.rekapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rekapp.Global
import com.example.rekapp.R
import com.example.rekapp.adapter.TransactionListAdapter
import com.example.rekapp.model.Transaction
import com.example.rekapp.util.formatUang
import com.example.rekapp.viewmodel.TransactionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

class TransactionListFragment : Fragment() {

    private lateinit var viewmodel:TransactionViewModel
    private var transactionlistadapter = TransactionListAdapter(arrayListOf(), 0)
    private var wallet = 0
    private var income = 0
    private var outcome = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            wallet = TransactionListFragmentArgs.fromBundle(requireArguments()).idwallet
        }

        viewmodel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        val b = LocalDate.now()
        val bulan = b.toString().split("-")
        val tahunbulan = "${LocalDate.now().year}-${bulan[1]}-%"
        viewmodel.refresh(wallet, tahunbulan)

        val recViewTransactionList = view.findViewById<RecyclerView>(R.id.recViewTransaction)
        recViewTransactionList.layoutManager = LinearLayoutManager(context)
        recViewTransactionList.adapter = transactionlistadapter

        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddTransaction)
        fab.setOnClickListener {
            val action = TransactionListFragmentDirections.actionTransactionListFragmentToCreateTransactionFragment(wallet)
            Navigation.findNavController(it).navigate(action)
        }

        val txtheader = view.findViewById<TextView>(R.id.txtTransactionHeader)
        val txtsaldos = view.findViewById<TextView>(R.id.txtSaldos)
        val cardincome = view.findViewById<CardView>(R.id.cardView)
        val cardoutcome = view.findViewById<CardView>(R.id.cardView2)
        val txtsaldogakanggo = view.findViewById<TextView>(R.id.txtSaldoGakanggo)
        if(wallet == 0 || wallet == 1){
            cardincome.visibility = View.GONE
            cardoutcome.visibility = View.GONE
            txtsaldos.visibility = View.GONE
            txtsaldogakanggo.visibility = View.GONE
//            if(wallet == 0){
//                txtheader.text = "PEMASUKAN BULAN INI"
//            }
//            else if(wallet == 1){
//                txtheader.text = "PENGELUARAN BULAN INI"
//            }
        }
        for (i in 0 until Global.wallet.size){
            if(Global.wallet[i].idwallet == wallet){
                txtheader.text = "Transaksi " + Global.wallet[i].namawallet.toUpperCase() + " - " + Global.wallet[i].jeniswallet.toUpperCase()
                txtsaldos.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Global.wallet[i].sisasaldo.toDouble()).toString()
            }
        }
        observeViewModel(wallet)
    }

    private fun observeViewModel(wallet1:Int) {
        val txtheader = view?.findViewById<TextView>(R.id.txtTransactionHeader)
        viewmodel.transactionLD.observe(viewLifecycleOwner, Observer {
            transactionlistadapter.updateTransactionList(it as ArrayList<Transaction>, wallet1)
            val txtError = view?.findViewById<TextView>(R.id.txtErrorLoadTransaction)

            if(it.isEmpty()){
                txtError?.visibility = View.VISIBLE
            }
            else{
                txtError?.visibility = View.GONE
            }

            if(wallet1 == 0){
                income = 0
                for (i in 0 until it.size){
                    if(it[i].wallet1 == 0){
                        income += it[i].nominal
                    }
                }
                txtheader?.text = "PEMASUKAN BULAN INI = ${formatUang(income.toDouble())}"
            }
            else if(wallet1 == 1){
                outcome = 0
                for (i in 0 until it.size){
                    if(it[i].wallet1 == 1){
                        outcome += it[i].nominal
                    }
                }
                txtheader?.text = "PENGELUARAN BULAN INI = ${formatUang(outcome.toDouble())}"
            }
            else{
                income = 0
                outcome = 0
                for (i in 0 until it.size){
                    Log.d("datasasa", it[i].toString())
                    if(it[i].wallet1 == 0){
                        income += it[i].nominal
                    }
                    else if(it[i].wallet1 == 1){
                        outcome += it[i].nominal
                    }
                    else{
                        if(it[i].wallet1 == wallet1){
                            outcome += it[i].nominal
                        }
                        else{
                            income += it[i].nominal
                        }
                    }
                }

                val txtincome = view?.findViewById<TextView>(R.id.txtIncome)
                val txtoutcome = view?.findViewById<TextView>(R.id.txtOutcome)
                txtincome?.text = formatUang(income.toDouble())
                txtoutcome?.text = formatUang(outcome.toDouble())
            }
        })
    }
}