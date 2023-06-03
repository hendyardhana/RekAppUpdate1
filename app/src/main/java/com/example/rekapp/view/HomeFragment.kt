package com.example.rekapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.rekapp.Global
import com.example.rekapp.R
import com.example.rekapp.model.Wallet
import com.example.rekapp.viewmodel.WalletViewModel

class HomeFragment : Fragment() {

    private lateinit var viewmodel:WalletViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this).get(WalletViewModel::class.java)
        viewmodel.refresh("")

        viewmodel.allWallet.observe(viewLifecycleOwner, Observer {
            Global.wallet = it as ArrayList<Wallet>
        })

        val btnDompetVirtual = view.findViewById<Button>(R.id.btnBankHome)
        val btnDompet = view.findViewById<Button>(R.id.btnSpayHome)
        val btnPemasukan = view.findViewById<Button>(R.id.btnGopayHome)
        val btnPengeluaran = view.findViewById<Button>(R.id.btnDanaHome)

        btnDompetVirtual.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("Dompet Virtual")
            Navigation.findNavController(view).navigate(action)
        }

        btnDompet.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("Dompet")
            Navigation.findNavController(view).navigate(action)
        }

        btnPemasukan.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTransactionListFragment(0)
            Navigation.findNavController(it).navigate(action)
        }

        btnPengeluaran.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTransactionListFragment(1)
            Navigation.findNavController(it).navigate(action)
        }
    }
}