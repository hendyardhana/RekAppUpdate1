package com.example.rekapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rekapp.Global
import com.example.rekapp.R
import com.example.rekapp.adapter.WalletListAdapter
import com.example.rekapp.model.Wallet
import com.example.rekapp.viewmodel.WalletViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WalletListFragment : Fragment() {

    private lateinit var viewModel: WalletViewModel
    private val walletListAdapter = WalletListAdapter(arrayListOf(), {item -> showDialog(item, item.jeniswallet, item.namawallet)})
    private var wallet = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            wallet = WalletListFragmentArgs.fromBundle(requireArguments()).wallet
        }

        val recViewWalletList = view.findViewById<RecyclerView>(R.id.recViewWallet)
        recViewWalletList.layoutManager = LinearLayoutManager(context)
        recViewWalletList.adapter = walletListAdapter

        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        viewModel.refresh(wallet)

        val txtJenisWalletHeader = view.findViewById<TextView>(R.id.txtJenisWalletHeader)
        txtJenisWalletHeader.text = wallet.toUpperCase()

        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddWallet)
        fab.setOnClickListener {
            val action = WalletListFragmentDirections.actionWalletListFragmentToCreateWalletFragment(wallet, "create", 0)
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.walletLD.observe(viewLifecycleOwner, Observer {
            walletListAdapter.updateWalletList(it as ArrayList<Wallet>)
            val txtError = view?.findViewById<TextView>(R.id.txtErrorLoadWallet)
            if(it.isEmpty()){
                txtError?.visibility = View.VISIBLE
            }
            else{
                txtError?.visibility = View.GONE
            }
        })

        viewModel.allWallet.observe(viewLifecycleOwner, Observer {
            Global.wallet = it as ArrayList<Wallet>
            //Log.d("datawallet", Global.wallet[0].namawallet)
        })
    }

    private fun showDialog(item:Wallet, jenisDompet:String, namaDompet:String) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
            context
        )

        // set title dialog
        alertDialogBuilder.setTitle("Hapus $jenisDompet").setIcon(R.drawable.baseline_warning_24)

        // set pesan dari dialog
        alertDialogBuilder
            .setMessage("Yakin menghapus ${namaDompet.toUpperCase()} ?")
            .setCancelable(false)
            .setPositiveButton(
                "Ya"
            ) { dialog, id ->
                viewModel.deleteTransaction(item.idwallet)
                viewModel.deleteWallet(item)
            }
            .setNegativeButton(
                "Tidak"
            ) { dialog, id -> dialog.cancel()
            }

        // membuat alert dialog dari builder
        val alertDialog: AlertDialog = alertDialogBuilder.create()

        // menampilkan alert dialog
        alertDialog.show()
    }
}