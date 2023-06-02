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
        val txtTujuan = view.findViewById<EditText>(R.id.txtTujuan)
        val txtNominal = view.findViewById<EditText>(R.id.txtNominal)
        val txtDeskripsi = view.findViewById<EditText>(R.id.txtDeskripsi)

        val btnCreate = view.findViewById<Button>(R.id.btnCreateTransaksi)
        btnCreate.setOnClickListener {
            val transaction = Transaction(wallet1, txtTujuan.text.toString().toInt(), txtNominal.text.toString().toInt(), txtDeskripsi.text.toString(), Calendar.getInstance().time.toString())
            viewmodel.addTransaction(transaction)
            Toast.makeText(view.context, "Add Transaction Success", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }
}