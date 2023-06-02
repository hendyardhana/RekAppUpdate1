package com.example.rekapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.rekapp.Global
import com.example.rekapp.R
import com.example.rekapp.model.Transaction
import com.example.rekapp.model.Wallet
import com.example.rekapp.viewmodel.CreateDetailWalletViewModel
import com.example.rekapp.viewmodel.CreateTransactionViewModel
import java.util.Calendar

class CreateTransactionFragment : Fragment() {

    private lateinit var viewmodel:CreateTransactionViewModel
    private var wallet1 = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            wallet1 = CreateTransactionFragmentArgs.fromBundle(requireArguments()).idwallet
        }

        viewmodel = ViewModelProvider(this).get(CreateTransactionViewModel::class.java)
        val txtNominal = view.findViewById<EditText>(R.id.txtNominal)
        val txtDeskripsi = view.findViewById<EditText>(R.id.txtDeskripsi)
        val txtDari = view.findViewById<EditText>(R.id.txtDari)
        for (i in 0 until Global.wallet.size){
            if(Global.wallet[i].idwallet == wallet1){
                txtDari.setText("${Global.wallet[i].namawallet} - ${Global.wallet[i].jeniswallet.toUpperCase()}")
            }
        }

        var arrwallet = emptyArray<String>()

        for (i in 0 until Global.wallet.size){
            if(Global.wallet[i].idwallet != wallet1){
                arrwallet += "${Global.wallet[i].idwallet} - ${Global.wallet[i].namawallet} - ${Global.wallet[i].jeniswallet.toUpperCase()}"
            }
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrwallet)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = view.findViewById<Spinner>(R.id.txtTujuan)
        spinner.adapter = adapter

        val btnCreate = view.findViewById<Button>(R.id.btnCreateTransaksi)
        btnCreate.setOnClickListener {
            val str = spinner.selectedItem.toString()
            val tujuan = str.split(" - ")
            if(spinner.selectedItem.toString() != "" && txtNominal.text.toString() != "" && txtDeskripsi.text.toString() != ""){
                val transaction = Transaction(wallet1, tujuan[0].toInt(), txtNominal.text.toString().toInt(), txtDeskripsi.text.toString(), Calendar.getInstance().time.toString())
                viewmodel.addTransaction(transaction)
                viewmodel.updateWallet(txtNominal.text.toString().toInt()*-1, wallet1)
                viewmodel.updateWallet(txtNominal.text.toString().toInt(), tujuan[0].toInt())
                Toast.makeText(view.context, "Berhasil Tambah Transaksi", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(it).popBackStack()
            }
            else{
                Toast.makeText(context, "Diisi dulu semuanya", Toast.LENGTH_SHORT).show()
            }
        }
    }
}