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
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.rekapp.Global
import com.example.rekapp.R
import com.example.rekapp.model.Transaction
import com.example.rekapp.model.Wallet
import com.example.rekapp.viewmodel.CreateDetailWalletViewModel
import com.example.rekapp.viewmodel.CreateTransactionViewModel
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
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
        val layoutTxtDari = view.findViewById<TextInputLayout>(R.id.textInputLayoutDari)
        val txtKirimKe = view.findViewById<TextView>(R.id.textView2)
        val txtJenisTransaksi = view.findViewById<TextView>(R.id.textView)

        when (wallet1) {
            0 -> {
                txtJenisTransaksi.text = "BUAT PEMASUKAN"
                txtDari.isEnabled = true
                layoutTxtDari.hint = "Pemasukan"
                txtKirimKe.text = "Masuk Ke: "
                txtDeskripsi.visibility = View.GONE
            }
            1 -> {
                txtJenisTransaksi.text = "BUAT PENGELUARAN"
                txtDari.isEnabled = true
                layoutTxtDari.hint = "Pengeluaran"
                txtKirimKe.text = "Gunakan Saldo: "
                txtDeskripsi.visibility = View.GONE
            }
            else -> {
                txtJenisTransaksi.text = "TRANSFER"
                layoutTxtDari.isEnabled = false
                for (i in 0 until Global.wallet.size){
                    if(Global.wallet[i].idwallet == wallet1){
                        txtDari.setText("${Global.wallet[i].namawallet} (Rp. ${Global.wallet[i].sisasaldo})")
                    }
                }
            }
        }

        var arrwallet = emptyArray<String>()

        for (i in 0 until Global.wallet.size){
            if(Global.wallet[i].idwallet != wallet1){
                arrwallet += "${Global.wallet[i].idwallet} - ${Global.wallet[i].namawallet} (Rp. ${Global.wallet[i].sisasaldo})"
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
            val sal = str.split( " (Rp. ")
            val saldoss = sal[1].split(")")
            if(txtDari.text.toString() != "" && txtNominal.text.toString() != ""){
                val check = saldoss[0].toInt()-txtNominal.text.toString().toInt()
                if(wallet1 == 0){
                    val transaction = Transaction(wallet1, tujuan[0].toInt(), txtNominal.text.toString().toInt(), "", txtDari.text.toString(), LocalDate.now().toString())
                    viewmodel.addTransaction(transaction)
                    viewmodel.updateWallet(txtNominal.text.toString().toInt(), tujuan[0].toInt())
                    Toast.makeText(view.context, "Berhasil Tambah Transaksi", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(it).popBackStack()
                }
                else if(wallet1 == 1){
                    if(check >= 0){
                        val transaction = Transaction(wallet1, tujuan[0].toInt(), txtNominal.text.toString().toInt(), "", txtDari.text.toString(), LocalDate.now().toString())
                        viewmodel.addTransaction(transaction)
                        viewmodel.updateWallet(txtNominal.text.toString().toInt()*-1, tujuan[0].toInt())
                        Toast.makeText(view.context, "Berhasil Tambah Transaksi", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(it).popBackStack()
                    }
                    else{
                        Toast.makeText(context, "Saldo tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    if(check >= 0){
                        val transaction = Transaction(wallet1, tujuan[0].toInt(), txtNominal.text.toString().toInt(), "", txtDeskripsi.text.toString(), LocalDate.now().toString())
                        viewmodel.addTransaction(transaction)
                        viewmodel.updateWallet(txtNominal.text.toString().toInt()*-1, wallet1)
                        viewmodel.updateWallet(txtNominal.text.toString().toInt(), tujuan[0].toInt())
                        Toast.makeText(view.context, "Berhasil Tambah Transaksi", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(it).popBackStack()
                    }
                    else{
                        Toast.makeText(context, "Saldo tidak cukup", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(context, "Diisi dulu semuanya", Toast.LENGTH_SHORT).show()
            }
        }
    }
}