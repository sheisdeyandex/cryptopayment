package com.crypto.payment

import android.app.Application
import com.crypto.payment.models.CryptoModel

class App:Application() {
    override fun onCreate() {
        super.onCreate()

    }
    companion object{
        var sum=""
        var resultSum=0.0
        lateinit var cryptoModel:CryptoModel
        var erc20:Boolean = false
        var eth:Boolean = false
        var trc20:Boolean = false
        var exchangeRate:Double = 0.0
        var id:String = "1"
    }
}