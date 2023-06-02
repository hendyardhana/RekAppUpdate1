package com.example.rekapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.example.rekapp.viewmodel.TransactionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        viewmodel.refresh(wallet)

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
        for (i in 0 until Global.wallet.size){
            if(Global.wallet[i].idwallet == wallet){
                txtheader.text = "Transaksi " + Global.wallet[i].namawallet.toUpperCase() + " - " + Global.wallet[i].jeniswallet.toUpperCase()
                txtsaldos.text = "Rp. ${Global.wallet[i].sisasaldo}"
                if(Global.wallet[i].jeniswallet == "jajan"){
                    cardincome.visibility = View.GONE
                    cardoutcome.visibility = View.GONE
                    txtsaldos.visibility = View.GONE
                    fab.visibility = View.GONE
                    txtsaldogakanggo.visibility = View.GONE
                }
                else{
                    cardincome.visibility = View.VISIBLE
                    cardoutcome.visibility = View.VISIBLE
                    txtsaldos.visibility = View.VISIBLE
                    fab.visibility = View.VISIBLE
                    txtsaldogakanggo.visibility = View.VISIBLE
                }
            }
        }
        observeViewModel(wallet)
    }

    private fun observeViewModel(wallet1:Int) {
        viewmodel.transactionLD.observe(viewLifecycleOwner, Observer {
            transactionlistadapter.updateTransactionList(it as ArrayList<Transaction>, wallet1)
            val txtError = view?.findViewById<TextView>(R.id.txtErrorLoadTransaction)

            if(it.isEmpty()){
                txtError?.visibility = View.VISIBLE
            }
            else{
                txtError?.visibility = View.GONE
            }

            //Log.d("datatransaksi", it.toString())
            for (i in 0 until it.size){
                if(it[i].wallet1 == wallet1){
                    outcome += it[i].nominal
                }
                else if(it[i].wallet2 == wallet1){
                    income += it[i].nominal
                }
            }

            val txtincome = view?.findViewById<TextView>(R.id.txtIncome)
            val txtoutcome = view?.findViewById<TextView>(R.id.txtOutcome)
            txtincome?.text = income.toString()
            txtoutcome?.text = outcome.toString()
        })
    }
}