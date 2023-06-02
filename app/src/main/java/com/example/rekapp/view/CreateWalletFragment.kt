package com.example.rekapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.rekapp.R
import com.example.rekapp.model.Wallet
import com.example.rekapp.viewmodel.CreateDetailWalletViewModel

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
        val txtNamaWallet = view.findViewById<EditText>(R.id.txtNamaWallet)
        val txtJenisWallet = view.findViewById<EditText>(R.id.txtJenisWallet)
        var jeniswallettext = ""
        when(jeniswallet){
            "bank" -> jeniswallettext = "BANK"
            "spay" -> jeniswallettext = "SHOPEE PAY"
            "gopay" -> jeniswallettext = "GOPAY"
            "dana" -> jeniswallettext = "DANA"
            "ovo" -> jeniswallettext = "OVO"
        }

        txtJenisWallet.setText(jeniswallettext)
        val txtSaldo = view.findViewById<EditText>(R.id.txtSaldo)

        val btnAdd = view.findViewById<Button>(R.id.btnCreateEditWallet)
        btnAdd.setOnClickListener {
            if(!txtNamaWallet.text.toString().isEmpty() && !txtSaldo.text.toString().isEmpty()){
                val wallet = Wallet(txtNamaWallet.text.toString(), jeniswallet, txtSaldo.text.toString().toInt())
                viewmodel.addWallet(wallet)
                Toast.makeText(view.context, "Add Wallet Success", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(it).popBackStack()
            }
            else{
                Toast.makeText(context, "Diisi dulu cuy, maen klik klik bae lu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}