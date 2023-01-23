package com.crypto.payment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crypto.payment.App
import com.crypto.payment.MainActivity
import com.crypto.payment.databinding.FragmentMainBinding
import com.crypto.payment.models.CryptoModel
import com.crypto.payment.viewmodels.MainViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding:FragmentMainBinding
    lateinit var trc20:CryptoModel
    lateinit var eth:CryptoModel
    lateinit var erc20:CryptoModel
    var trc20ExchangeRate:Double=0.0
    var erc20ExchangeRate:Double=0.0
    var ethExchangeRate:Double=0.0
    var enableClick = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getUsdt(App.id).collect{
                if (it?.show == true){
                    binding.usdtTrc20.isVisible= true
                    binding.tvText.text = it.name
                    trc20 = it
                }
                else{
                    binding.usdtTrc20.isVisible= false
                }
                enableClick++
            }
            viewModel.getEth(App.id).collect{
                if (it?.show == true){
                    binding.eth.isVisible= true
                    binding.tvEth.text = it.name
                    eth = it
                }
                else{
                    binding.eth.isVisible= false
                }
                enableClick++
            }
            viewModel.getUsdtErc20(App.id).collect{
                if (it?.show == true){
                    binding.usdtErc20.isVisible= true
                    binding.tvErcText.text = it.name
                    erc20=it
                }
                else{
                    binding.usdtErc20.isVisible= false
                }
                enableClick++
            }
            viewModel.getUsdtErc20ExchangeRate().collect{
                if (it != null) {
                    erc20ExchangeRate = it
                }
                enableClick++
            }
            viewModel.getEthExchangeRate().collect{
                if (it != null) {
                    ethExchangeRate = it
                }
                enableClick++
            }
            viewModel.getUsdtTrc20ExchangeRate().onCompletion {
            }.collect{
                if (it != null) {
                    trc20ExchangeRate = it
                }
                enableClick++
            }
        }
        binding.usdtTrc20.setOnClickListener {
            if ( enableClick==6){
                App.cryptoModel = trc20
                App.exchangeRate = trc20ExchangeRate
                App.trc20 = true
                (requireActivity() as MainActivity).replaceFragment(CalculateFragment())
            }
        }
        binding.eth.setOnClickListener {
            if ( enableClick==6){
                App.cryptoModel = eth
                App.exchangeRate = ethExchangeRate
                App.eth = true
                (requireActivity() as MainActivity).replaceFragment(CalculateFragment())
            }
        }
        binding.usdtErc20.setOnClickListener {
            if ( enableClick==6){
                App.cryptoModel = erc20
                App.exchangeRate = erc20ExchangeRate
                App.erc20 = true
                (requireActivity() as MainActivity).replaceFragment(CalculateFragment())
            }

        }
    }
}