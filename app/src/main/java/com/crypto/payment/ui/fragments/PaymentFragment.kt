package com.crypto.payment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.crypto.payment.App
import com.crypto.payment.MainActivity
import com.crypto.payment.databinding.FragmentPaymentBinding
import com.crypto.payment.models.HistoryModel
import com.crypto.payment.viewmodels.PaymentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class PaymentFragment : Fragment() {
    private val viewModel: PaymentViewModel by viewModels()
    private lateinit var binding: FragmentPaymentBinding
    var startTimeStamp = System.currentTimeMillis()
    var confirmed = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(App.cryptoModel.qr).into(binding.ivQr)
        binding.tvSum.text = App.sum.toString()
        binding.tvAddress.text = App.cryptoModel.address
        binding.btnCancel.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragment(CalculateFragment())
        }
        insertHistory()
    }
    private suspend fun checkTrc20Transaction(){
        viewModel.checkTrc20Transaction(startTimeStamp).collect{
            it?.let {
                if (it.isNotEmpty()){
                    it.forEach {
                        val realDecimal = 1
                        for (i in 0..it.decimals){
                            realDecimal*10
                        }
                        val realAmount = it.amount/1000000
                        if (it.confirmed&&realAmount>=App.resultSum){
                            confirmed=true
                            viewModel.database.child(App.id).child("history").child(viewModel.historyChildId).child("confirmed").setValue(confirmed)
                            (requireActivity() as MainActivity).replaceFragment(SuccessFragment())
                        }

                    }
                }
            }

        }
    }
    private suspend fun checkEthTransaction(){
        viewModel.checkEthTransaction().collect{
            it?.let {
                if (it.isNotEmpty()){
                    it.forEach {
                        val realAmount = it.value/100000000000000000
                        if (it.confirmations>20&&realAmount>=App.resultSum&&!viewModel.getDate(startTimeStamp).after(viewModel.getDate(it.timeStamp*1000L))){
                            confirmed=true
                            viewModel.database.child(App.id).child("history").child(viewModel.historyChildId).child("confirmed").setValue(confirmed)
                            (requireActivity() as MainActivity).replaceFragment(SuccessFragment())
                        }

                    }
                }
            }

        }
    }
    private suspend fun checkErc20Transaction(){
        viewModel.checkErc20Transaction().collect{
            it?.let {
                if (it.isNotEmpty()){
                    it.forEach {
                        val realDecimal = 1
                        for (i in 0..it.tokenDecimal){
                            realDecimal*10
                        }
                        val realAmount = it.value/1000000
                        if (it.confirmations>20&&realAmount>=App.resultSum&&!viewModel.getDate(startTimeStamp).after(viewModel.getDate(it.timeStamp*1000L))){
                            confirmed=true
                            viewModel.database.child(App.id).child("history").child(viewModel.historyChildId).child("confirmed").setValue(confirmed)
                            (requireActivity() as MainActivity).replaceFragment(SuccessFragment())
                        }

                    }
                }
            }

        }
    }
    private fun insertHistory(){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                viewModel.insertHistory(App.id, HistoryModel(App.cryptoModel.name,App.exchangeRate.toString(),App.sum,App.cryptoModel.address,Calendar.getInstance().time.toString(),false))
                while (!confirmed){
                    delay(1000*30)
                    if (App.trc20){
                        checkTrc20Transaction()
                    }
                    if (App.erc20){
                        checkErc20Transaction()
                    }
                }
                }

        }
   }
}