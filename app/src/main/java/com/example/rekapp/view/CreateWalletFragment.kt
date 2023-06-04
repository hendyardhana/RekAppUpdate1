package com.example.rekapp.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.rekapp.R
import com.example.rekapp.model.Wallet
import com.example.rekapp.viewmodel.CreateDetailWalletViewModel
import com.google.android.material.textfield.TextInputLayout

class CreateWalletFragment : Fragment() {

    private lateinit var viewmodel:CreateDetailWalletViewModel
    var jeniswallet = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            jeniswallet = CreateWalletFragmentArgs.fromBundle(requireArguments()).jeniswallet
        }

        viewmodel = ViewModelProvider(this).get(CreateDetailWalletViewModel::class.java)
        val txtHeader = view.findViewById<TextView>(R.id.txtCreateWallet)
        txtHeader.text = "BUAT ${jeniswallet.toUpperCase()}"
        val txtNamaWallet = view.findViewById<EditText>(R.id.txtNamaWallet)
        val txtSaldo = view.findViewById<EditText>(R.id.txtSaldo)
        val txtnamawalletlayout = view.findViewById<TextInputLayout>(R.id.textInputLayoutNamaWallet)
        txtnamawalletlayout.hint = "Nama $jeniswallet"

        val btnAdd = view.findViewById<Button>(R.id.btnCreateEditWallet)
        btnAdd.setOnClickListener {
            if(!txtNamaWallet.text.toString().isEmpty() && !txtSaldo.text.toString().isEmpty()){
                val wallet = Wallet(txtNamaWallet.text.toString(), jeniswallet, txtSaldo.text.toString().toInt())
                viewmodel.addWallet(wallet)
                dialog(it, "berhasil", txtNamaWallet.text.toString(), jeniswallet)
            }
            else{
                dialog(it, "gagal", "", "")
            }
        }
    }

    private fun dialog(view:View, status:String, namaDompet:String, jenisDompet:String) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(
            context
        )

        // set title dialog
        if(status == "berhasil"){
            alertDialogBuilder.setTitle("Berhasil!")
            alertDialogBuilder.setMessage("$jenisDompet Baru :\n${namaDompet.toUpperCase()}")
            alertDialogBuilder.setIcon(R.drawable.baseline_check_circle_24)
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK")
            { dialog, id ->
                Navigation.findNavController(view).popBackStack()
            }
        }
        else if (status == "gagal"){
            alertDialogBuilder.setTitle("Kesalahan")
            alertDialogBuilder.setMessage("Ada kolom yang belum diisi!")
            alertDialogBuilder.setIcon(R.drawable.baseline_error_24)
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK")
            { dialog, id ->
                dialog.cancel()
            }
        }

        // set pesan dari dialog

        // membuat alert dialog dari builder
        val alertDialog: AlertDialog = alertDialogBuilder.create()

        // menampilkan alert dialog
        alertDialog.show()
    }
}