package com.crypto.payment.models

data class HistoryModel (val cryptoCurrency:String="",val rate:String="",val sum:String="",val payedTo:String="",val date:String="",val confirmed:Boolean=false)