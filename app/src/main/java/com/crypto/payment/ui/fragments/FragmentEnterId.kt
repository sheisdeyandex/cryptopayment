package com.crypto.payment.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crypto.payment.Constants
import com.crypto.payment.MainActivity
import com.crypto.payment.R
import com.crypto.payment.databinding.FragmentEnterIdBinding


class FragmentEnterId : Fragment() {
    lateinit var binding:FragmentEnterIdBinding
    lateinit var sharedPrefs:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEnterIdBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefs = requireActivity().getPreferences(MODE_PRIVATE)
        binding.btnSetId.setOnClickListener {
            if (TextUtils.isEmpty(binding.tiedId.text)){
                binding.tiedId.error = "set Id"
            }
            else{
                sharedPrefs.edit().putString(Constants.sharedId,binding.tiedId.text.toString()).apply()
                (requireActivity() as MainActivity).replaceFragment(MainFragment())
            }

        }
    }
}