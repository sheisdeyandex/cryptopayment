package com.crypto.payment.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.crypto.payment.App
import com.crypto.payment.MainActivity
import com.crypto.payment.R
import com.crypto.payment.databinding.FragmentCalculateBinding
import com.crypto.payment.viewmodels.CalculateViewModel

class CalculateFragment : Fragment() {
    private val viewModel: CalculateViewModel by viewModels()
    lateinit var binding:FragmentCalculateBinding
    var commissionValue = 0.02
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCalculateBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnExchangeText.iconPadding = -(binding.btnExchangeText.icon?.intrinsicWidth ?: 0)
        binding.tvSumRate.text = App.exchangeRate.toString()
        binding.tvRate.text =" "+ App.cryptoModel.value+"/USDT"
        binding.tvValue.text = " "+App.cryptoModel.value
        binding.btnExchangeText.text = " "+App.cryptoModel.value + " - Dirham"
        binding.tvCommission.text = getString(R.string.commission)+" "+ (commissionValue*100).toString()+"%"
        clicks()
        setCommission()
    }
    private fun setCommission(){
        binding.btnZeroCommission.setOnClickListener {
            commissionValue = 0.02
            calculate()
            binding.tvCommission.text = getString(R.string.commission)+" "+ (commissionValue*100).toString()+"%"

        }
        binding.btnFiveCommission.setOnClickListener {
            commissionValue = 0.05
            calculate()
            binding.tvCommission.text = getString(R.string.commission)+" "+ (commissionValue*100).toString()+"%"

        }
        binding.btnTenCommission.setOnClickListener {
            commissionValue = 0.1
            calculate()
            binding.tvCommission.text = getString(R.string.commission)+" "+ (commissionValue*100).toString()+"%"

        }
        binding.btnBack.setOnClickListener {
            (requireActivity() as MainActivity).replaceFragment(MainFragment())
        }
        binding.btnFifTeenCommission.setOnClickListener {
            commissionValue = 0.15
            calculate()
            binding.tvCommission.text = getString(R.string.commission)+" "+ (commissionValue*100).toString()+"%"
        }
    }
    private fun Double.format(digits:Int) = String.Companion.format(
        java.util.Locale.GERMAN,
        "%#,.${digits}f",
        this
    )
    @SuppressLint("SetTextI18n")
    fun calculate(){
        val sum = binding.tvSumValue.text.toString().toDouble()/binding.tvSumRate.text.toString().toDouble()
        val commission = sum*commissionValue
        val totalSum  = sum+commission
        binding.tvSumResult.text = totalSum.format(2)
    }
    @SuppressLint("SetTextI18n")
    private fun setSumText(clickText:String){
        if (binding.tvSumValue.text.toString()=="0.00"){
            binding.tvSumValue.text =clickText
        }
        else{
            binding.tvSumValue.text =binding.tvSumValue.text.toString()+clickText
        }
        calculate()
    }
    private fun clicks(){
        binding.btnBackSpace.setOnClickListener {
            if (binding.tvSumValue.text.toString().count()>1){
                binding.tvSumValue.text= binding.tvSumValue.text.toString().dropLast(1)
                calculate()
            }
            else{
                binding.tvSumValue.text= binding.tvSumValue.text.toString().dropLast(1)
            }

        }
        binding.btnPay.setOnClickListener {
            if (binding.tvSumValue.text.toString()!="0.00"){
                App.sum = binding.tvSumValue.text.toString()+" "+ App.cryptoModel.value+" ~"+binding.tvSumResult.text.toString()
                App.resultSum = binding.tvSumResult.text.toString().replace(",",".").toDouble()
                (requireActivity() as MainActivity).replaceFragment(PaymentFragment())
            }
        }
        binding.btnOne.setOnClickListener {
           setSumText("1")
        }
        binding.btnTwo.setOnClickListener {
            setSumText("2")
        }
        binding.btnThree.setOnClickListener {
            setSumText("3")
        }
        binding.btnFour.setOnClickListener {
            setSumText("4")
        }
        binding.btnFive.setOnClickListener {
            setSumText("5")
        }
        binding.btnSix.setOnClickListener {
            setSumText("6")
        }
        binding.btnSeven.setOnClickListener {
            setSumText("7")
        }
        binding.btnEight.setOnClickListener {
            setSumText("8")
        }
        binding.btnNine.setOnClickListener {
            setSumText("9")
        }
        binding.btnDot.setOnClickListener {
            setSumText(".")
        }
        binding.btnZero.setOnClickListener {
            setSumText("0")
        }


    }
}