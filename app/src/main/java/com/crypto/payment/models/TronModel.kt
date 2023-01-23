package com.crypto.payment.models

data class TronModel(
    val amount:Int,
    val transferFromAddress:String,
    val decimals:Int,
    val tokenName:String,
    val transferToAddress:String,
    val block:Int,
    val confirmed:Boolean,
    val transactionHash:String,
    val timestamp:Long
    )