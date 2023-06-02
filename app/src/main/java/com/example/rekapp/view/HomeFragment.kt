package com.example.rekapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.rekapp.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnBank = view.findViewById<Button>(R.id.btnBankHome)
        val btnSpay = view.findViewById<Button>(R.id.btnSpayHome)
        val btnGopay = view.findViewById<Button>(R.id.btnGopayHome)
        val btnDana = view.findViewById<Button>(R.id.btnDanaHome)
        val btnOvo = view.findViewById<Button>(R.id.btnOvoHome)
        val btnJajan = view.findViewById<Button>(R.id.btnJajanHome)
        val btnDompet = view.findViewById<Button>(R.id.btnDompetHome)

        btnBank.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("bank")
            Navigation.findNavController(view).navigate(action)
        }

        btnSpay.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("spay")
            Navigation.findNavController(view).navigate(action)
        }

        btnGopay.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("gopay")
            Navigation.findNavController(view).navigate(action)
        }

        btnDana.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("dana")
            Navigation.findNavController(view).navigate(action)
        }

        btnOvo.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("ovo")
            Navigation.findNavController(view).navigate(action)
        }

        btnJajan.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("jajan")
            Navigation.findNavController(view).navigate(action)
        }

        btnDompet.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWalletListFragment("dompet")
            Navigation.findNavController(view).navigate(action)
        }
    }
}