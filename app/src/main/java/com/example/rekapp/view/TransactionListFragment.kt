package com.example.rekapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rekapp.R
import com.example.rekapp.adapter.TransactionListAdapter
import com.example.rekapp.model.Transaction
import com.example.rekapp.viewmodel.TransactionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TransactionListFragment : Fragment() {

    private lateinit var viewmodel:TransactionViewModel
    private var transactionlistadapter = TransactionListAdapter(arrayListOf(), 0)
    private var wallet = 0

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

        observeViewModel(wallet)
    }

    private fun observeViewModel(wallet1:Int) {
        viewmodel.transactionLD.observe(viewLifecycleOwner, Observer {
            transactionlistadapter.updateTransactionList(it as ArrayList<Transaction>, wallet1)
        })
    }
}